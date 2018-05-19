import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

import java.util.ArrayList;

/**
 * This class is used to manage and draw all of the objects on the screen.
 *
 * @author Anantajit, Blake and Devansh
 */
public class DrawingSurface extends PApplet {
    private volatile Boat[] boats;
    
    private int mapRadius;
    //KEYS
    private boolean[] keys; // 0 - up, 1 - down, 2 - left, 3 - right

    //PLAYER
    private Player player;
    private Client client;
    private ClientLoop clientThread;


    //ARRAYLISTS FOR BLOCKS
    private ArrayList<Block> walls;
    private ArrayList<Block> waterBlocks;

    //private Block[] walls;

    //ARRAYLISTS FOR OTHERS
    private ArrayList<Bullet> playerBullets;
    private ArrayList<Particle> particles;

    //SCREEN ADJUSTMENTS
    private float angle; // rotates screen(not functional yet)
    private float angleVel; // angle velocity, how fast the angle is changing
    private boolean zoom; // whether screen is zoomed or not(by scalefactor)
    public float scaleFactor; // scales screen

    //MOUSE VAR(adjusted for the translations and scale)
    public static float xCoord;
    public static float yCoord;

    //SCREEN VAR
    private TradeScreen ts;
    private Menu menuScreen;
    private int screen;
    private static final int MENU = 0;
    private static final int GAME = 1;
    private static final int TRADE = 2;
    private static final int DEAD = 3;

    //DOCKS AND TRADING
    private ArrayList<Dock> docks;
    private Dock currentDock;
    private int dockTimer; //timer so player doesn't go back into the dock they just exited
    
    private int damageOutsideMapTimer;

    //Sets window to 1200, 800 and makes smooth animation
    public void settings() {
        size(1200, 800);
        smooth(0);
    }

    public void setup() {
        //SETTING NO STROKE, FRAMERATE, AND FONT TYPE
        client = new Client("127.0.0.1", 4444);
        client.connect();
        
        mapRadius = 100;
        noStroke();
        frameRate(60);
        PFont font = createFont("PressStart2P.ttf", 20);
        textFont(font);

        //KEYS
        keys = new boolean[4];// 0 - up, 1 - down, 2 - left, 3 - right

        //MOUSE COORD(adjusted for weird stuff)
        xCoord = this.mouseX;
        yCoord = this.mouseY;

        //IF SCREEN ZOOMED IN, BY HOW MUCH, AND ROTATION
        zoom = true;
        scaleFactor = 7;
        angle = (float) (Math.PI / 2.0);
        angleVel = 0;

        //ARRAYLISTS FOR STUFF
        playerBullets = new ArrayList<Bullet>();
        particles = new ArrayList<Particle>();

        //ARRAYLISTS FOR BLOCKS
        walls = new ArrayList<Block>();
        waterBlocks = new ArrayList<Block>();

        //SCREEN VAR
        screen = MENU;
        menuScreen = new Menu(this);
        ts = new TradeScreen(this);

        //DOCKS AND TRADING
        docks = new ArrayList<Dock>();
        currentDock = null;

        //READS BLOCK FROM TEXTFILE AND ADJUSTS SIZE OF BLOCKS
        TextReader reader = new TextReader("output.txt");
        String[][] blocks = client.readArray();
        int blockSize = width / blocks.length;

        //LOADING TEXTURES

        //water
        Animation water = new Animation("Water_2/water", 4, this, 10);

        //sand(variations)
        /*
        PImage sand1 = loadImage("sand12.png");
        PImage sand2 = loadImage("sand22.png");
        PImage sand3 = loadImage("sand32.png");
        */
        PImage sand = loadImage("Sand.png");
        
        PImage dock = loadImage("Dock.png");
        

        //grass
        PImage grass = loadImage("GrassNew.png"); // was "Grass.png"

        //SETS UP MAP ON DISPLAY
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {

                //DRAWS A LAND
                if (blocks[j][i].equals("l")) {
                    noStroke();
                    walls.add(new Block(this, i * blockSize, j * blockSize, blockSize, blockSize, grass));
                    walls.get(walls.size() - 1).setColor(40, 130, 20);
                }

                //DRAWS A SAND(randomly picks between textures)
                else if (blocks[j][i].equals("s")) {

                    noStroke();
                    /*
                    double x = Math.random();
                    if (x < 0.33) {
                        walls.add(new Block(this, i * blockSize, j * blockSize, blockSize, blockSize, sand1));
                    } else if (x < 0.66) {
                        walls.add(new Block(this, i * blockSize, j * blockSize, blockSize, blockSize, sand2));
                    } else {
                        walls.add(new Block(this, i * blockSize, j * blockSize, blockSize, blockSize, sand3));
                    }
                     */
                    walls.add(new Block(this, i * blockSize, j * blockSize, blockSize, blockSize, sand));
                    walls.get(walls.size() - 1).setColor(219, 209, 0);
                }

                //DRAWS A DOCK
                else if (blocks[j][i].equals("d")) {
                    noStroke();
                    //PImage thisImage;
                    /*
                    if(i-1 >= 0 && (blocks[i-1][j].equals("s") || blocks[i-1][j].equals("l"))) {
                    	//sand is up?
                    	thisImage = dockDown;
                    } else if(j-1 >= 0 && (blocks[i][j-1].equals("s") || blocks[i][j-1].equals("l"))) {
                    	//left?
                    	thisImage = dockLeft;
                    } else if(i+1 < blocks.length && (blocks[i+1][j].equals("s") || blocks[i+1][j].equals("l"))) {
                    	//down?
                    	thisImage = dockUp;
                    } else {
                    	//right?
                    	thisImage = dockRight;
                    }
                    */
                    docks.add(new Dock(this, i * blockSize, j * blockSize, blockSize, blockSize,dock));
                    docks.get(docks.size() - 1).setColor(219, 0, 209);
                    
                    waterBlocks.add(new Block(this, i * blockSize, j * blockSize, blockSize, blockSize, water));
                    waterBlocks.get(waterBlocks.size() - 1).setColor(100, 150, 230);
                }

                //DRAWS A PLAYER AT FIRST WATER TILE
                else if (blocks[j][i].equals("p")) {
                	waterBlocks.add(new Block(this, i * blockSize, j * blockSize, blockSize, blockSize, water));
                    waterBlocks.get(waterBlocks.size() - 1).setColor(100, 150, 230);
                    /*
                    player = new Player(this, i * blockSize / 2, j * blockSize / 2, 10, 20, 20);
                    player.setColor(255, 100, 10);
                    */
                }

                //DRAWS A WATER TILE
                else if (blocks[j][i].equals("w")) {
                    waterBlocks.add(new Block(this, i * blockSize, j * blockSize, blockSize, blockSize, water));
                    waterBlocks.get(waterBlocks.size() - 1).setColor(100, 150, 230);
                }
            }
        }

        //RANDOMLY GENERATED PLAYER SPAWN SO THAT 3 BY 3 AROUND IS ALL WATER
        int randomI;
        int randomJ;
        boolean playerSpawn;
        do {
        	randomI = (int)random(blocks.length);
        	randomJ = (int)random(blocks[0].length);
        	playerSpawn = true;
        	for(int i = -1; i < 2; i++) {
        		for(int j = -1; j < 2; j++) {
        			if(randomI + i < 0 || randomI + i > blocks.length-1 || randomJ + j < 0 || randomJ + j > blocks[0].length-1) {
        				playerSpawn = false;
        				//System.out.println(randomI + " " + randomJ + " wont work because bounds");
        				break;
        			} else if(!blocks[randomI+i][randomI+j].equals("w")) {
        				playerSpawn = false;
        				//System.out.println(randomI + " " + randomJ + " wont work because " + randomI+i + " " + randomJ+j + " is a " + blocks[randomI+i][randomI+j]);
        				break;
        			}
        		}
        		if(!playerSpawn)
        			break;
        	}
        } while(!playerSpawn);
        
		//System.out.println(randomI + " " + randomJ + " should work because " + randomI + " " + randomJ + " is a " + blocks[randomI][randomI]);


        player = new Player(this, randomI * blockSize, randomJ * blockSize, 10, 20, 100);
        player.setColor(255, 100, 10);
        
        
        clientThread = new ClientLoop(this, client, player);
        clientThread.start();
        
        damageOutsideMapTimer = 0;
        //player.setGun(new Gun(100,10,15,10));
    }


    public void draw() {
        //UPDATING MOUSE LOCATION
        xCoord = mouseX;
        yCoord = mouseY;

        //If GAME SCREEN
        if (screen == GAME) {
        	pushMatrix(); // pushing matrix so it can pop to draw ui
        	
            //SETS BACKGROUND TO A BLUE COLOR
            background(0);

            //CHECK FOR INPUT AND CHANGES PLAYER VALUES
            if (keys[0]) {
                player.changeYV((float) -0.05);
            } else {
                //player.setYV(0);
            }
            if (keys[1]) {
                player.changeYV((float) 0.05);
            } else {
                //player.setYV(0);
            }
            if (keys[2]) {
                //player.changeXV(-1);
                player.setAngleVel(player.getAngleVel() - Math.PI / 1000);
                //angleVel -= Math.PI/1000;
            }
            if (keys[3]) {
                //player.changeXV(1);
                player.setAngleVel(player.getAngleVel() + Math.PI / 1000);
                //angleVel += Math.PI/1000;
            }

            //ZOOMS WORLD TO PLAYER BY ZOOM FACTOR
            if (zoom) {
                //ADJUST MOUSE COORD(translate for scale)
                xCoord -= this.width / 2 + player.getWidth() / 2;
                yCoord -= this.height / 2 + player.getHeight() / 2;


                //angle += angleVel;
                //angleVel *= 0.9;
                angle = player.getAngle();
                //SCALES MAP BASED ON PLAYER LOCATION
                translate(this.width / 2 + player.getWidth() / 2, this.height / 2 + player.getHeight() / 2);
                rotate((float) (-angle + Math.PI / 2.0));
                scale((float) scaleFactor);

                //ADJUSTS MOUSE VALUES BY SCALING
                xCoord /= scaleFactor;
                yCoord /= scaleFactor;

                //UNDOS TRANSLATE NEEDED FOR SCALE
                translate(-this.width / 2 - player.getWidth() / 2, -this.height / 2 - player.getHeight() / 2);

                //ADJUST MOUSE COORD BACK
                xCoord += this.width / 2 + player.getWidth() / 2;
                yCoord += this.height / 2 + player.getHeight() / 2;
            } else {
                //MIGHT NEED SOMETHING HERE(just in case)
            }

            //CENTERS ON PLAYER
            translate(this.width / 2 - player.getX(), this.height / 2 - player.getY());

            //ADJUSTS MOUSE COORD
            xCoord -= this.width / 2 - player.getX();
            yCoord -= this.height / 2 - player.getY();

            //UPDTAES THE LOCATION OF THE WALLS
            player.update(walls);
            
            
            
            //Updates Render Distance
            mapRadius = player.getLookout().getRenderDistance();
            
            
            //Updates Bullets
            for(int i = 0; i < player.getWeapons().size(); i++) {
    			if(player.getWeapons().get(i).isClicked()) {
    				if(player.getWeapons().get(i).isLeft()) {
    					int x = (int) player.getWeapons().get(i).getCannon().getX();
    					int y = (int) player.getWeapons().get(i).getCannon().getY();
    					System.out.println(angle);
    					Bullet bullet = player.getWeapons().get(i).generateBullet(x+(int)(90*Math.cos(angle+Math.PI/2)), 
								y+(int)(90*Math.sin(angle+Math.PI/2)));
    					if(bullet!=null) {
    						addBullet(bullet);
    					}
    				} else {
    					int x = (int) player.getWeapons().get(i).getX();
    					int y = (int) player.getWeapons().get(i).getY();
    					System.out.println(angle);
    					Bullet bullet = player.getWeapons().get(i).generateBullet(x+(int)(90*Math.cos(angle-Math.PI/2)), 
								y+(int)(90*Math.sin(angle-Math.PI/2)));
    					if(bullet!=null) {
    						addBullet(bullet);
    					}
    				}
    			}
    		}
            
            //UPDATES BULLETS(not needed rn)
            for (int i = 0; i < playerBullets.size(); i++) {
                playerBullets.get(i).updateMovement();
                if (i != 0 && playerBullets.get(i).shouldBeDead(walls, width, width)) {
                    particles.add(new Particle(this, playerBullets.get(i), 2)); // add a "smoke" particle
                    playerBullets.remove(i);
                    if (i > 0)
                        i--;
                }
            }

            if (mousePressed) {
                //CODE FOR GUN(buggy)
//				if (player.getGun().canFire()) {
//					playerBullets.add(player.generateBullet(mouseX, mouseY));
//					playerBullets.get(playerBullets.size() - 1).setColor(255, 50, 0);
//				}

                //SIMPLE FILLER FOR SHOOTING
                System.out.println("Kerchow!");
            }

            //UPDATES PARTICLES(not needed rn)
            for (int i = 0; i < particles.size(); i++) {
                particles.get(i).update();
                if (particles.get(i).shouldBeDead()) {
                    particles.remove(i);
                    if (i > 0)
                        i--;
                }
            }


            //UPDATES DOCKS
            if (dockTimer <= 0) {
                for (Dock d : docks) {
                    if (player.isTouching(d)) {
                        screen = TRADE;
                        currentDock = d;
                        break;
                    }
                }
            } else {
                dockTimer--;
            }

            //DISPLAYS THE BLOCKS
            for (Block w : walls) {
                if (zoom/* && w.isTouching(player.getX()-700/scaleFactor, player.getY()-500/scaleFactor, 1500/scaleFactor, 1050/scaleFactor)*/) {
                    w.show();
                } else if (!zoom) {
                	double distance = Math.sqrt(Math.pow(w.getX()-(player.getX()+player.getWidth()/2), 2)+Math.pow(w.getY()-(player.getY()+player.getHeight()/2), 2));
                	if(distance <= mapRadius) {
                		w.showNoImage();
                	}
                }

            }

            //DISPLAYS THE WATER TILES
            for (Block w : waterBlocks) {
                if (zoom/* && w.isTouching(player.getX()-(this.width+200)/(2*scaleFactor), player.getY()-(this.height+200)/(2*scaleFactor), (this.width+200)/scaleFactor, (this.height+200)/scaleFactor)*/) {
                    w.show();
                } else if (!zoom) {
                	double distance = Math.sqrt(Math.pow(w.getX()-(player.getX()+player.getWidth()/2), 2)+Math.pow(w.getY()-(player.getY()+player.getHeight()/2), 2));
                	if(distance <= mapRadius) {
                		w.showNoImage();
                	}
                }

            }

            //DISPLAYS THE DOCKS
            for (Dock d : docks) {
                if (zoom)
                    d.show();
                else
                    d.showNoImage();
            }

            //UPDATES ANIMATION
            waterBlocks.get(0).updateGif();

            //DISPLAYS BULLETS(not needed rn)
            for (Bullet b : playerBullets) {
                b.show();
            }

            //DISPLAYS PARTICLES(not needed rn)
            for (Particle p : particles)
                p.show();

            if (boats != null) {
                for (Boat b :
                        boats) {
                    if (b != null) {
                        b.setX((float) (b.getX() + b.getV() * Math.cos(b.getAngle())));
                        b.setY((float) (b.getY() + b.getV() * Math.sin(b.getAngle())));
                        Block bx = new Block(this, b.getX(), b.getY(), 10, 10);
                        bx.setColor(0, 0, 0);
                        bx.show();
                    }
                }
            }

            //DISPLAYS PLAYER
            player.show();
            
            popMatrix();
            
            fill(50); 
            rect(0, 0, width/3, height/20);
            if((double)player.getHealth()/player.getMaxHealth() >= 0.65) {
            	fill(50,255,50);
            } else if((double)player.getHealth()/player.getMaxHealth() >= 0.25) {
            	fill(255, 250, 50);
            } else {
            	fill(255,50,50);
            }
            rect(0,0,((float)player.getHealth() / player.getMaxHealth()) * (width/3-10),height/20-10);
            
            if(player.shouldBeDead()) {
            	screen = DEAD;
            }
            
            float playerX = player.getX();
            float playerY = player.getY();
            if(playerX > width || playerX < 0 || playerY > width || playerY < 0) { // slightly damages player if outside game
            	if(damageOutsideMapTimer <= 0) {
            		damageOutsideMapTimer = 20;
            		player.changeHealth(-1);
            	} else {
            		damageOutsideMapTimer--;
            	}
            }
        }

        //IF TRADE SCREEN
        else if (screen == TRADE) {
        	background(255);
			/*
			ts.update(player);
			ts.show(player);
			
			if(ts.checkExitButton())
				screen = GAME;
			*/

            currentDock.updateCurrentScreen(player);
            currentDock.showCurrentScreen(player);
            currentDock.checkCurrentSwitchButton();
            if (currentDock.checkCurrentExitButton()) {
                screen = GAME;
                currentDock = null;
                dockTimer = 120;
            }

        }

        //IF MENU SCREEN
        else if (screen == MENU) {
            //SLOWS DOWN FRAMERATE FOR COOL GIF(can be changed)
            
            //menu
        	menuScreen.update();
        	if(menuScreen.getSwitchScreen()) {
        		
        		screen = GAME;
        	}
            menuScreen.draw();
        } else if(screen == DEAD) {
        	background(0);
        	fill(255,50,50);
        	textAlign(CENTER);
        	textSize(50);
        	text("You have died\nPress R to restart",width/2,height/2);
        	if(keyPressed && (key == 'r' || key == 'R')) {
        		setup();
        		//screen = MENU;
        	}
        }


    }

    public float getAngleVel() {
        return angleVel;
    }

    public void setAngleVel(float f) {
        angleVel = f;
    }

    public void keyPressed() {
        if (key == 'w' || keyCode == UP || key == 'W')
            keys[0] = true;
        else if (key == 's' || keyCode == DOWN || key == 'S')
            keys[1] = true;
        else if (key == 'a' || keyCode == LEFT || key == 'A')
            keys[2] = true;
        else if (key == 'd' || keyCode == RIGHT || key == 'D')
            keys[3] = true;
        else if (key == 'm' || key == 'M')
            zoom = false;

    }

    public void keyReleased() {
        if (key == 'w' || keyCode == UP || key == 'W')
            keys[0] = false;
        else if (key == 's' || keyCode == DOWN || key == 'S')
            keys[1] = false;
        else if (key == 'a' || keyCode == LEFT || key == 'A')
            keys[2] = false;
        else if (key == 'd' || keyCode == RIGHT || key == 'D')
            keys[3] = false;
        else if (key == 'm' || key == 'M')
            zoom = true;
    }

    public Player getPlayer() {
        return player;
    }

    public void setBoats(Boat[] boats) {
        this.boats = boats;
    }
    public void addBullet(Bullet b) {
    	playerBullets.add(b);
    }
}
