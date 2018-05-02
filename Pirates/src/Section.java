import processing.core.PApplet;

public class Section {
	
	
	protected float x;
	protected float y;
	protected float width;
	protected float height;
	protected int level;
	private PApplet drawer;
	public Section(PApplet drawer, float x, float y, float width, float height) {
		level = 1;
		this.x = x;
		this.drawer = drawer;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void upgrade() {
		level++;
	}
	public void highlight() {
		float mouseX = DrawingSurface.xCoord;
		float mouseY = DrawingSurface.yCoord;
		if( mouseX >= x && mouseX <= x+width && mouseY >= y && mouseY <= y+height) {
			drawer.fill(0);
			drawer.rect(x, y, width, height);
			
		} 
	}
	
}
