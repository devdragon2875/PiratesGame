import java.awt.Color;

import processing.core.PApplet;

/**
 * This class is a part of the ship which is used to display the map.
 * 
 * @author Devansh
 *
 */
public class LookoutSection extends Section {
	private PApplet drawer;
	private int renderDistance;

	public LookoutSection(PApplet drawer, float x, float y, float width, float height) {
		super(drawer, x, y, width, height);
		this.drawer = drawer;
		renderDistance = 100;

	}

	public void upgrade() {
		super.upgrade();
		if (super.level == 1) {
			renderDistance = 100;
		} else if (super.level == 2) {
			renderDistance = 200;
		} else if (super.level == 3) {
			renderDistance = 300;
		}
	}

	public void draw() {
		drawer.ellipseMode(drawer.LEFT);
		drawer.stroke(0);
		drawer.strokeWeight((float) 0.5);
		if (super.level == 1) {
			drawer.fill(new Color(10, 80, 190).getRGB());
			drawer.ellipse(x, y, width, height);
		} else if (super.level == 2) {
			drawer.fill(new Color(10, 180, 190).getRGB());
			drawer.ellipse(x, y, width, height);
		} else if (super.level == 3) {
			drawer.fill(new Color(10, 250, 100).getRGB());
			drawer.ellipse(x, y, width, height);
		}
		drawer.noStroke();
		super.highlight();
	}

	public void setX(float f) {

		this.x = f;
	}

	public void setY(float f) {

		this.y = f;
	}

	public int getRenderDistance() {

		return renderDistance;

	}

	public int getStat(int level) {
		switch (level) {
		case 1:
			return 8;
		case 2:
			return 16;
		case 3:
			return 24;
		}
		return -1;
	}
}
