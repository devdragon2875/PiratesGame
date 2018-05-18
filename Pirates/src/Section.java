import processing.core.PApplet;

/**
 * This is a superclass used by the different sections of the player's ship.
 * @author Devansh
 *
 */
public class Section {
	
	
	protected float x;
	protected float y;
	protected float width;
	protected float height;
	protected int level;
	protected int clickCounter;
	private PApplet drawer;
	public Section(PApplet drawer, float x, float y, float width, float height) {
		level = 1;
		clickCounter = 0;
		this.x = x;
		this.drawer = drawer;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void upgrade() {
		level++;
		if(level >3) {
			level = 3;
		}
	}
	public void highlight() {
		float mouseX = DrawingSurface.xCoord;
		float mouseY = DrawingSurface.yCoord;
		if( mouseX >= x && mouseX <= x+width && mouseY >= y && mouseY <= y+height) {
			drawer.fill(255, 255, 255, 120);
			drawer.rect(x, y, width, height);
			
		} 
		/*
		if(isClicked()) {
			this.upgrade();
		}
		*/
	}
	public boolean isPointInside(float x, float y){
		return x > this.x && x < this.x + width && y > this.y && y < this.y + height;
	}
	
	public boolean isMouseHovering(){		return isPointInside(DrawingSurface.xCoord, DrawingSurface.yCoord);
	}
	
	public boolean isClicked(){
		return isMouseHovering() && drawer.mousePressed && (clickCounter <=0);
	}
	
	public int getLevel() {
		return level;
	}
	
	//OVERRIDE THIS
	public int getStat(int level) {
		return -1;
	}
	
}
