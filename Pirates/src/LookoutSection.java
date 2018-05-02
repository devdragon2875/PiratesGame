import java.awt.Color;

import processing.core.PApplet;

public class LookoutSection extends Section{
	private PApplet drawer;
	public LookoutSection(PApplet drawer, float x, float y, float width, float height) {
		super(drawer, x, y, width, height);
		this.drawer = drawer;
		
	}
	public void upgrade() {
		super.upgrade();
	}
	public void draw() {
		drawer.ellipseMode(drawer.LEFT);
		drawer.stroke(0);
		drawer.strokeWeight((float) 0.5);
		if(super.level == 1) {
			drawer.fill(new Color(10, 80, 190).getRGB());
			drawer.ellipse(x, y, width, height);
		} else if(super.level == 2) {
			drawer.fill(new Color(10, 180, 190).getRGB());
			drawer.ellipse(x, y, width, height);
		}else if(super.level == 3) {
			drawer.fill(new Color(10, 250, 100).getRGB());
			drawer.ellipse(x, y, width, height);
		}
		drawer.noStroke();
		super.highlight();
	}
	public void setX(float f) {
		// TODO Auto-generated method stub
		this.x =f;
	}
	public void setY(float f) {
		// TODO Auto-generated method stub
		this.y =f;
	}
}
