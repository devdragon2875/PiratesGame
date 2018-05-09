import java.util.ArrayList;

import processing.core.PApplet;

/**
 * This class is used to represent a bullet, as it can move through the screen and collide with players or enemies.
 * @author Blake
 *
 */
public class Bullet extends Block{
	
	private float xV, yV;
	public static final int DEFAULT_BULLET_SIZE = 4;
	private int damage;
	
	public Bullet(PApplet parent, float x, float y, float width,float height, float xV, float yV, int damage) {
		super(parent, x, y, width, height);
		this.xV = xV;
		this.yV = yV;
		this.damage = damage;
	}

	public Bullet(PApplet parent, float x, float y, float width,float height, int angle, float speed, int damage) {
		super(parent, x, y, width, height);
		xV = (float)(speed * Math.cos(Math.toRadians(angle)));
		yV = (float)(speed * Math.sin(Math.toRadians(angle)));
		this.damage = damage;
	}
	
	public Bullet(PApplet parent, float x, float y, float width,float height,float startX, float startY, float targetX, float targetY, float speed, int damage) {
		super(parent, x, y, width, height);
		float distance = (float)Math.sqrt((startX-targetX)*(startX-targetX) + (startY-targetY)*(startY-targetY));
		xV = speed * (targetX - startX)/distance;
		yV = speed * (targetY - startY)/distance;
		this.damage = damage;
	}
	
	public Bullet(PApplet parent, WeaponSection player, float targetX, float targetY, float speed, int damage) {
		this(parent, player.getX() + player.getWidth()/2f - DEFAULT_BULLET_SIZE/2f, player.getY() + player.getHeight()/2f - DEFAULT_BULLET_SIZE/2f, DEFAULT_BULLET_SIZE, DEFAULT_BULLET_SIZE, player.getX() + player.getWidth()/2f, player.getY() + player.getHeight()/2f, targetX,targetY,speed,damage);
	}
	
	public void updateMovement() {
		super.x+=xV;
		super.y+=yV;
	}
	
	public boolean shouldBeDead(Block[] walls) {
		if(super.x>parent.width || super.x+super.width < 0 || super.y>parent.height || super.y+super.height < 0)
			return true;
		
		for(int i = 0; i < walls.length; i++) {
			if(super.isTouching(walls[i]))
				return true;
		}
		
		return false;
	}
	
	public boolean shouldBeDead(ArrayList<Block> blocks) {
		Block[] blockArray = new Block[blocks.size()];
		for(int i = 0; i < blocks.size(); i++)
			blockArray[i] = blocks.get(i);
		return shouldBeDead(blockArray);
	}

	public float getXV() {
		return xV;
	}

	public void setXV(float xV) {
		this.xV = xV;
	}

	public float getYV() {
		return yV;
	}

	public void setYV(float yV) {
		this.yV = yV;
	}
	
	public int getDamage() {
		return damage;
	}
}