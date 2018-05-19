import processing.core.PApplet;

/**
 * This class is used to store statistics about a gun and is used to generate bullets.
 * @author Blake
 *
 */
public class Gun {

	public static final int DEFAULT_DELAY = 10;
	public static final int DEFAULT_DAMAGE = 1;
	public static final float DEFAULT_SPEED = 10;
	public static final float DEFAULT_INACCURACY = 50;
	//public static final int DEFAULT_BULLETS = 3;
	private int firingDelay, currentDelay, damage;
	private float speed, inaccuracy;
	private float x;
	private float y;
	private int width;
	private int height;
	private boolean left;
	private boolean fired;
	
//	public Gun() {
//		this(DEFAULT_DELAY,DEFAULT_DAMAGE,DEFAULT_SPEED,DEFAULT_INACCURACY);
//	}
	
	public Gun(float x, float y, int width, int height, boolean left, int firingDelay, int damage, float speed, float inaccuracy) {
		this.firingDelay = firingDelay;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.left = left;
		currentDelay = firingDelay;
		this.damage = damage;
		this.speed = speed;
		this.inaccuracy = inaccuracy;
		fired = false;
	}
	
	public void update() {
		if(fired && currentDelay > 0)
			currentDelay--;
		
		if(fired && currentDelay <= 0)
			fired = false;
	}
	
	public boolean canFire() {
		return !fired;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public Bullet generateBullet(PApplet parent, WeaponSection player, float targetX, float targetY) {
		//change inaccuracy, more/less accurate with where the target is
		fired = true;
		currentDelay = firingDelay;
		float xDist = Math.abs(player.getX()-targetX);
		float yDist = Math.abs(player.getY()-targetY);
		return new Bullet(parent,player,targetX + xDist*(float)(Math.random()*inaccuracy*2-inaccuracy)/100,targetY + yDist*(float)(Math.random()*inaccuracy*2-inaccuracy)/100,speed,damage);
	}
	public void draw(PApplet drawer) {
		drawer.fill(0);
		if(left) {
			drawer.rect(x, y, -width, height);
		} else {
			drawer.rect(x, y, width, height);
		}
		
	}

	public void setX(float f) {
		// TODO Auto-generated method stub
		this.x = f;
	}

	public void setY(float f) {
		// TODO Auto-generated method stub
		this.y = f;
	}

	public float getX() {
		// TODO Auto-generated method stub
		return x;
	}

	public float getY() {
		// TODO Auto-generated method stub
		return y;
	}
}
