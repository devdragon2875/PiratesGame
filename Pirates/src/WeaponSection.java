import java.awt.Color;

import processing.core.PApplet;

/**
 * This class is used to represent the cannons the player has on the ship.
 * 
 * @author Devansh
 *
 */
public class WeaponSection extends Section {
	Gun cannon;
	boolean left;
	private PApplet drawer;
	private float centerX, centerY;

	public WeaponSection(float x, float y, float width, float height, boolean left, PApplet drawer) {
		super(drawer, x, y, width, height);
		this.drawer = drawer;
		this.left = left;
		if (left) {

			cannon = new Gun(x, y + height / 2 - 1, 2, 2, left, 300, 5, 2, (float) 0.1);

		} else {

			cannon = new Gun(x + width, y + height / 2 - 1, 2, 2, left, 300, 5, 2, (float) 0.1);

		}
	}

	public void upgrade() {
		super.upgrade();
		if (super.level == 2) {
			if (left) {

				cannon = new Gun(x, y + height / 2 - 1, 4, 2, left, 200, 7, 2, (float) 0.1);

			} else {

				cannon = new Gun(x + width, y + height / 2 - 1, 4, 2, left, 200, 7, 2, (float) 0.1);

			}
		}
		if (super.level == 3) {
			if (left) {

				cannon = new Gun(x, y + height / 2 - 1.5f, 6, 3, left, 100, 10, 2, (float) 0.1);

			} else {

				cannon = new Gun(x + width, y + height / 2 - 1.5f, 6, 3, left, 100, 10, 2, (float) 0.1);

			}
		}

	}

	public Bullet generateBullet(int targetX, int targetY) {
		if (cannon.canFire()) {
			return cannon.generateBullet(drawer, this, targetX, targetY);
		} else {
			return null;
		}

	}

	public boolean canFire() {
		return cannon.canFire();
	}

	public void draw() {

		cannon.draw(drawer);
		drawer.stroke(0);
		drawer.strokeWeight((float) 0.5);
		if (this.canFire()) {
			drawer.fill(new Color(23, 206, 43).getRGB());
		} else {
			drawer.fill(new Color(206, 23, 43).getRGB());
		}
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

		return width;
	}

	public float getHeight() {

		return height;
	}

	public void update() {
		cannon.update();

	}

	public void setX(float f) {
		this.x = f;
		if (left) {
			cannon.setX(f);
		} else {
			cannon.setX(x + width);

		}
		cannon.setY(y + height / 2 - 1);

	}

	public void setY(float f) {
		this.y = f;

	}

	public boolean isLeft() {

		return left;
	}

	public Gun getCannon() {

		return cannon;
	}

	public void setCenter(float centerX, float centerY) {
		this.centerX = centerX;
		this.centerY = centerY;
	}

	public float getCenterX() {
		return centerX;
	}

	public float getCenterY() {
		return centerY;
	}

	public int getStat(int level) {
		switch (level) {
		case 1:
			return 5;
		case 2:
			return 7;
		case 3:
			return 10;
		}
		return -1;
	}

}
