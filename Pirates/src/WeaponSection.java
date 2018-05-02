import java.awt.Color;

import processing.core.PApplet;

public class WeaponSection extends Section{
	Gun cannon;
	boolean left;
	private PApplet drawer;
	public WeaponSection(float x, float y, float width, float height, boolean left, PApplet drawer) {
		super(drawer, x, y, width, height);
		this.drawer = drawer;
		this.left = left;
		if(left) {
			cannon = new Gun(x, y + height/2 - 1, 2, 2, left,  10, 1, 5, (float) 0.1);
		} else {
			cannon = new Gun(x+width, y + height/2 - 1, 2, 2, left,  10, 1, 5, (float) 0.1);
		}
	}
	public void upgrade() {
		super.upgrade();
		if(super.level == 2) {
			if(left) {
				cannon = new Gun(x, y + height/2 - 1, 4, 2, left,  10, 1, 5, (float) 0.1);
			} else {
				cannon = new Gun(x+width, y + height/2 - 1, 4, 2, left,  10, 1, 5, (float) 0.1);
			}
		}
		if(super.level == 3) {
			if(left) {
				cannon = new Gun(x, y + height/2 - 1.5f, 6, 3, left,  10, 1, 5, (float) 0.1);
			} else {
				cannon = new Gun(x+width, y + height/2 - 1.5f, 6, 3, left,  10, 1, 5, (float) 0.1);
			}
		}
		
	}
	public Bullet generateBullet(int targetX, int targetY) {
		return cannon.generateBullet(drawer, this, targetX, targetY);
	}


	public void draw() {
		
		cannon.draw(drawer);
		drawer.stroke(0);
		drawer.strokeWeight((float) 0.5);
		drawer.fill(new Color(206, 23, 43).getRGB());
		drawer.rect(x, y, width, height);
		drawer.noStroke(); 
		super.highlight();
		
	}
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	public float getWidth() {
		// TODO Auto-generated method stub
		return width;
	}
	public float getHeight() {
		// TODO Auto-generated method stub
		return height;
	}
	public void update() {
		cannon.update();
		
	}
	public void setX(float f) {
		this.x = f;
		if(left) {
			cannon.setX(f);
		} else {
			cannon.setX(x+width);
			
		}
		cannon.setY(y + height/2 - 1);
		
	}
	public void setY(float f) {
		this.y = f;
		
	}
}
