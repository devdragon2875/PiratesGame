import java.awt.Color;

import processing.core.PApplet;

/**
 * This class is used to manage how the ship steers and how fast it moves.
 * 
 * @author Devansh
 *
 */
public class SteerSection extends Section {

	private PApplet drawer;

	public SteerSection(PApplet drawer, float x, float y, float width, float height) {
		super(drawer, x, y, width, height);
		this.drawer = drawer;

	}

	public void upgrade() {
		super.upgrade();
	}

	public void draw() {
		drawer.stroke(0);
		drawer.strokeWeight((float) 0.5);
		if (super.level == 1) {
			drawer.fill(new Color(139, 69, 19).getRGB());
			drawer.rect(x, y, width, height);
		} else if (super.level == 2) {
			drawer.fill(new Color(205, 133, 63).getRGB());
			drawer.rect(x, y, width, height);
		} else if (super.level == 3) {
			drawer.fill(new Color(255, 222, 173).getRGB());
			drawer.rect(x, y, width, height);
		}
		drawer.noStroke();
		super.highlight();
	}

	public void setX(float f) {
		// TODO Auto-generated method stub
		this.x = f;
	}

	public void setY(float f) {
		// TODO Auto-generated method stub
		this.y = f;
	}

	public int getStat(int level) {
		switch (level) {
		case 1:
			return 10;
		case 2:
			return 12;
		case 3:
			return 15;
		}
		return -1;
	}
}
