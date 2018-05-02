import java.awt.Color;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class Player extends Block{
	
	protected float xV,yV,maxXV, maxYV, friction;
	//private PImage image;
	public static final float DEFAULT_MAX_VELOCITY = 0.3f;
	public static final float DEFAULT_FRICTION = 0.1f;
	private int health, maxHealth;
	private ArrayList<WeaponSection> weapons = new ArrayList<WeaponSection>();
	private SteerSection steer;
	private LookoutSection lookout;
	private float weaponWidth;
	private float weaponHeight;
	private float angle;
	Animation splash;
	public Player(PApplet parent, float x, float y, float width, float height, int maxHealth) {
		super(parent, x, y, width, height);
		xV = 0;
		yV = 0;
		weaponWidth = width/2;
		weaponHeight = height/4;
		maxXV = DEFAULT_MAX_VELOCITY;
		maxYV = DEFAULT_MAX_VELOCITY;
		friction = DEFAULT_FRICTION;
		//x-=width/2;
		//y-=height/2;
		//splash = new Animation("Splash/splash_",4,parent,5);
		angle = (float) (Math.PI/2);
		this.maxHealth = maxHealth;
		health = maxHealth;
		weapons.add(new WeaponSection(x, y+height/4, weaponWidth, weaponHeight, true, parent));
		weapons.add(new WeaponSection(x+weaponWidth, y+height/4, weaponWidth, weaponHeight, false, parent));
		weapons.add(new WeaponSection(x+weaponWidth, y+height/4+weaponHeight, weaponWidth, weaponHeight, false, parent));
		weapons.add(new WeaponSection(x, y+height/4+weaponHeight, weaponWidth, weaponHeight, true, parent));
		steer = new SteerSection(parent, x+width/2 -width/6, y+height/4+height, width/3, width/3);
		lookout = new LookoutSection(parent, x+width/6, y, width/3, width/3);
		//image = parent.loadImage("playerStill.png");
		//movingImage = parent.loadImage("playerMoving.gif"); // doesnt work with gifs
//		for(int i = 0; i < weapons.size(); i++) {
//			weapons.get(i).upgrade();
//		}
//		for(int i = 0; i < weapons.size(); i++) {
//			weapons.get(i).upgrade();
//		}
//		steer.upgrade();
//		steer.upgrade();
	}
	
	public void show() {
		
		
		this.draw();
		for(int i = 0; i < weapons.size(); i++) {
			weapons.get(i).draw();
		}
		steer.draw();
		lookout.draw();
		//splash.update();
		//super.show();
		//parent.image(image, x, y,size,size);
		
	}
	
	public void update(Block[] b) {
		
//		x+= maxXV * Math.cos(angle);
//		y+= maxYV * Math.sin(angle);
		if(xV > maxXV) {
			xV = maxXV;
		}else if(xV < -1*maxXV) {
			xV = -1*maxXV;
		}
		if(yV > maxYV) {
			yV = maxYV;
		}else if(yV < -1*maxYV) {
			yV = -1*maxYV;
		}
		
		//moving x
		x += xV;
		
		for(int i = 0; i < b.length; i++) {
			if(this.isTouching(b[i])) { 
				if(xV > 0)
					x = b[i].getX()-super.getWidth();
				else
					x = b[i].getX()+b[i].getWidth();
				xV = 0;
				break;
			}
				
		}
		
		
		//moving y
		y += yV;
		
		for(int i = 0; i < b.length; i++) {
			if(this.isTouching(b[i])) { 
				if(yV > 0)
					y = b[i].getY()-super.getHeight();
				else
					y = b[i].getY()+b[i].getHeight();
				yV = 0;
				break;
			}
				
		}
		
		//friction
		xV *= 1-friction;
		
		yV *= 1-friction;
		
		for(int i = 0; i < weapons.size(); i++) {
			weapons.get(i).update();
		}
		weapons.get(0).setX(x);
		weapons.get(1).setX(x+weaponWidth);
		weapons.get(2).setX(x+weaponWidth);
		weapons.get(3).setX(x);
		weapons.get(0).setY(y+height/4);
		weapons.get(1).setY(y+height/4);
		weapons.get(2).setY(y+height/4+weaponHeight);
		weapons.get(3).setY(y+height/4+weaponHeight);
		steer.setX(x+width/2 - width/6);
		steer.setY( y+height/4+height/2);
		lookout.setX(x+width/3);
		lookout.setY(y);
	}
	public void draw() {
		
		
		parent.stroke(0);
		parent.strokeWeight((float) 0.5);
		parent.fill(new Color(139,69,19).getRGB());
		parent.rect(x, y, width, height, 2);
		
		parent.triangle(x+width/40, y+height/25, x+width/2, y-height/3, x+width-width/40, y+height/25);
		parent.noStroke();
		parent.rect(x+width/16, y, width-width/8, height/10, 2);
		
	}
	
	public void update(ArrayList<Block> blocks) {
		Block[] blockArray = new Block[blocks.size()];
		for(int i = 0; i < blocks.size(); i++)
			blockArray[i] = blocks.get(i);
		update(blockArray);
	}
	
	public boolean isOnGround(Block[] b) {
		if(y >= parent.height - height)
			return true;
		else {
			for(int i = 0; i < b.length; i++) {
				if(this.isTouching(b[i]))
					return true;
			}
			return false;
		}
	}
	
	public void changeXV(float in) {
		xV += in;
	}
	public void changeYV(float in) {
		yV += in;
	}
	
	public void moveOnScreen() {
		if(x < 0) {
			x = 0;
			xV = 0;
		}
		if(y < 0)
			y = 0;
		if(x + width > parent.width) {
			x = parent.width - width;
			xV = 0;
		}
		if(y + height > parent.height)
			y = parent.height - height;
	}
	
	public float getYV() {
		return yV;
	}
	
	public float getXV() {
		return xV;
	}
	
	public boolean shouldBeDead() {
		return health < 1;
	}
	
	
	public void changeHealth(int change) {
		health += change;
		if(health > maxHealth)
			health = maxHealth;
	}

	public void setAngle(float angle) {
		// TODO Auto-generated method stub
		this.angle = angle;
	}
	
	
	
}
