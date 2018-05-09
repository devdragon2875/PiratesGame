import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

/**
 * This class is used to manage and draw all of the objects on the screen.
 * @author Blake and Devansh
 *
 */
public class DrawingSurface extends PApplet {

	//KEYS
	private boolean[] keys; // 0 - up, 1 - down, 2 - left, 3 - right
	
	//PLAYER
	private Player player;
	
	
	//ARRAYLISTS FOR BLOCKS
	private ArrayList<Block> walls;
	private ArrayList<Block> waterBlocks;
	//private Block[] walls;
	
	//ARRAYLISTS FOR OTHERS
	private ArrayList<Bullet> playerBullets;
	private ArrayList<Particle> particles;
	private ArrayList<Enemy> enemies;
	
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
	
	//Sets window to 1200, 800 and makes smooth animation
	public void settings() {
		size(1200, 800);
		smooth(0);
	}

	public void setup() {
		//SETTING NO STROKE, FRAMERATE, AND FONT TYPE
		noStroke();
		frameRate(60);
		PFont font = createFont("PressStart2P.ttf",20);
		textFont(font);
		
		//KEYS
		keys = new boolean[4];// 0 - up, 1 - down, 2 - left, 3 - right
		
		//MOUSE COORD(adjusted for weird stuff)
		xCoord = this.mouseX;
		yCoord = this.mouseY;
		
		//IF SCREEN ZOOMED IN, BY HOW MUCH, AND ROTATION
		zoom = true;
		scaleFactor = 7;
		angle = (float) (Math.PI/2.0);
		angleVel = 0;
		
		//ARRAYLISTS FOR STUFF
		playerBullets = new ArrayList<Bullet>();
		particles = new ArrayList<Particle>();
		enemies = new ArrayList<Enemy>();
		
		//ARRAYLISTS FOR BLOCKS
		walls = new ArrayList<Block>();
		waterBlocks = new ArrayList<Block>();
		
		//SCREEN VAR
		screen = GAME;
		menuScreen = new Menu(this);
		ts = new TradeScreen(this);
		
		//MAP GENERATOR(creates a new random map and puts into a text file)
		MapGenerator mg = new MapGenerator();
		mg.GenerateMap(this);
		
		//READS BLOCK FROM TEXTFILE AND ADJUSTS SIZE OF BLOCKS
		TextReader reader = new TextReader("output.txt");
		String[][] blocks = reader.get2DArray();
		int blockSize = width/blocks.length;
		
		//LOADING TEXTURES
		
		//water
		Animation water = new Animation("Water_2/water",4,this,10);
		
		//sand(variations)
		PImage sand = loadImage("sand12.png");
		PImage sand2 = loadImage("sand22.png");
		PImage sand3 = loadImage("sand32.png");
		
		//grass
		PImage grass = loadImage("Grass.png");
		
		//SETS UP MAP ON DISPLAY
		for(int i = 0; i < blocks.length; i++) {
			for(int j = 0; j < blocks[i].length; j++) {
				
				//DRAWS A LAND
				if(blocks[j][i].equals("l")) {
					noStroke();
					walls.add(new Block(this,i*blockSize,j*blockSize,blockSize, blockSize,grass));
					walls.get(walls.size()-1).setColor(40, 130, 20);
				}
				
				//DRAWS A SAND(randomly picks between textures)
				else if(blocks[j][i].equals("s")) {
					
					noStroke();
					double x = Math.random();
					if(x < 0.33) {
						walls.add(new Block(this,i*blockSize,j*blockSize,blockSize, blockSize,sand));
					} else if(x < 0.66){
						walls.add(new Block(this,i*blockSize,j*blockSize,blockSize, blockSize,sand2));
					} else {
						walls.add(new Block(this,i*blockSize,j*blockSize,blockSize, blockSize,sand3));
					}
					
					walls.get(walls.size()-1).setColor(219, 209, 0);
				}
				
				//DRAWS A DOCK
				else if(blocks[j][i].equals("d")) {
					noStroke();
					walls.add(new Block(this,i*blockSize,j*blockSize,blockSize, blockSize));
					walls.get(walls.size()-1).setColor(219, 0, 209);
				}
				
				//DRAWS A PLAYER AT FIRST WATER TILE
				else if(blocks[j][i].equals("p")) {
					player = new Player(this,i*blockSize/2,j*blockSize/2,10,20,20);
					player.setColor(255, 100, 10);
				}
				
				//DRAWS AN ENEMY(no enemies currently)
				else if(blocks[j][i].equals("e")) {
					enemies.add(new Enemy(this,i*blockSize,j*blockSize,blockSize,blockSize,15));
					enemies.get(enemies.size()-1).setColor(200, 100, 255);
				}
				
				//DRAWS A WATER TILE
				else if(blocks[j][i].equals("w")) {
					waterBlocks.add(new Block(this,i*blockSize,j*blockSize,blockSize, blockSize,water));
					waterBlocks.get(waterBlocks.size()-1).setColor(100, 150, 230);
				}
			}
		}
		
		
		
		//player.setGun(new Gun(100,10,15,10));
	}

	public void draw() {
		//UPDATING MOUSE LOCATION
		xCoord = mouseX;
		yCoord = mouseY;
		
		//If GAME SCREEN
		if (screen == GAME) {
			//SETS BACKGROUND TO A BLUE COLOR
			background(100, 150, 230);
			
			//CHECK FOR INPUT AND CHANGES PLAYER VALUES 
			if (keys[0]) {
				player.changeYV((float)-0.05);
			} else {
				//player.setYV(0);
			}
			if (keys[1]) {
				player.changeYV((float)0.05);
			} else {
				//player.setYV(0);
			}
			if (keys[2]) {
				//player.changeXV(-1);
				angleVel -= Math.PI/1000;
			}
			if (keys[3]) {
				//player.changeXV(1);
				angleVel += Math.PI/1000;
			} 
			
			//ZOOMS WORLD TO PLAYER BY ZOOM FACTOR
			if (zoom) {
				//ADJUST MOUSE COORD(translate for scale)
				xCoord -= this.width/2 + player.getWidth()/2;
				yCoord -= this.height / 2 + player.getHeight()/2;
				
				angle += angleVel;
				angleVel *= 0.9;
				
				player.setAngle(angle);
				//SCALES MAP BASED ON PLAYER LOCATION
				translate(this.width/2 + player.getWidth()/2, this.height / 2 + player.getHeight()/2);
				rotate((float)(-angle + Math.PI/2.0));
				scale((float) scaleFactor);
				
				//ADJUSTS MOUSE VALUES BY SCALING
				xCoord/= scaleFactor;
				yCoord/=scaleFactor;
				
				//UNDOS TRANSLATE NEEDED FOR SCALE
				translate(-this.width/2 - player.getWidth()/2, -this.height / 2 - player.getHeight()/2);
				
				//ADJUST MOUSE COORD BACK
				xCoord += this.width/2 + player.getWidth()/2;
				yCoord += this.height / 2 + player.getHeight()/2;
			} else {
				//MIGHT NEED SOMETHING HERE(just in case)
			}
			
			//CENTERS ON PLAYER
			translate(this.width / 2 - player.getX(), this.height / 2 - player.getY());
			
			//ADJUSTS MOUSE COORD
			xCoord -= this.width/2 - player.getX();
			yCoord -= this.height / 2 - player.getY();
			
			//UPDTAES THE LOCATION OF THE WALLS
			player.update(walls);
			
			//UPDATES BULLETS(not needed rn)
			for (int i = 0; i < playerBullets.size(); i++) {
				playerBullets.get(i).updateMovement();
				for (Enemy e : enemies) {
					if (playerBullets.get(i).isTouching(e)) {
						e.changeHealth(-1 * playerBullets.get(i).getDamage());
						particles.add(new Particle(this, playerBullets.get(i), 20)); // add a "smoke" particle
						playerBullets.remove(i);
						if (i > 0)
							i--;
						break;
					}
				}
				if (i != 0 && playerBullets.get(i).shouldBeDead(walls)) {
					particles.add(new Particle(this, playerBullets.get(i), 20)); // add a "smoke" particle
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
			
			//UPDATES ENEMIES(not needed rn)
			for (int i = 0; i < enemies.size(); i++) {
				enemies.get(i).act(player);
				enemies.get(i).update(walls);
				if (enemies.get(i).shouldBeDead()) {
					enemies.remove(i);
					if (i > 0)
						i--;
				} else if (enemies.get(i).isTouching(player)) {
					player.changeHealth(-1);
				}
			}
			
			//DISPLAYS THE BLOCKS
			for (Block w : walls) {
				if(zoom/* && w.isTouching(player.getX()-700/scaleFactor, player.getY()-500/scaleFactor, 1500/scaleFactor, 1050/scaleFactor)*/) {
					w.show();
				} else if(!zoom) {
					w.showNoImage();
				}
				
			}
			
			//DISPLAYS THE WATER TILES
			for(Block w : waterBlocks) {
				if(zoom/* && w.isTouching(player.getX()-(this.width+200)/(2*scaleFactor), player.getY()-(this.height+200)/(2*scaleFactor), (this.width+200)/scaleFactor, (this.height+200)/scaleFactor)*/) {
					w.show();
				} else if(!zoom) {
					w.showNoImage();
				}
				
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
			
			//DISPLAYS ENEMIES(not needed rn)
			for (Enemy e : enemies)
				e.show();
			
			//DISPLAYS PLAYER
			player.show();
			
		} 
		
		//IF TRADE SCREEN
		else if(screen == TRADE) {
			ts.update(player);
			ts.show();
		} 
		
		//IF MENU SCREEN
		else if(screen == MENU) {
			//SLOWS DOWN FRAMERATE FOR COOL GIF(can be changed)
			frameRate(10);
			menuScreen.draw();
		}
		
		
		
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
}
