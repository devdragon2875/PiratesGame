import java.io.Serializable;

/**
 * Represents Bullet with minimum information
 * @author Anantajit
 *
 */
public class BulletNet implements Serializable{
	private float x, y;
	private float xV, yV;
	private int damage;
	
	public BulletNet(int damage) {
		this.damage = damage;
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public float getxV() {
		return xV;
	}
	public void setxV(float xV) {
		this.xV = xV;
	}
	public float getyV() {
		return yV;
	}
	public void setyV(float yV) {
		this.yV = yV;
	}
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
}
