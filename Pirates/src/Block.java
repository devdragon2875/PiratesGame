import processing.core.PApplet;
import processing.core.PImage;

/**
 * This class is used to store the location and size of rectangular objects.
 * @author Blake
 *
 */
public class Block {
	
	protected float x, y, width, height;
	protected PApplet parent;
	protected int r,g,b,alpha;
	protected PImage image;
	protected Animation gif;
	
	public Block(PApplet parent, float x, float y, float width, float height, Animation gif) {
		this.parent = parent;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		alpha = 255;
		this.gif = gif;
		image = null;
	}
	
	public Block(PApplet parent, float x, float y, float width, float height, PImage image) {
		this.parent = parent;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		alpha = 255;
		this.image = image;
		gif = null;
		
	}
	public Block(PApplet parent, float x, float y, float width, float height) {
		this(parent,x,y,width,height,(PImage)null);
	}
	
	public void show() {
		
		parent.fill(r,g,b,alpha);
		//System.out.println(x + " " + y);
		
//		PImage img;
//		img = parent.loadImage("sand.jpg");
//		parent.image(img, x, y, size, size);
		if(this.image != null)
			parent.image(image, x, y,width,height);
		else if(this.gif != null)
			gif.display(x, y, width, height);
		else
			parent.rect(x, y, width, height);
		
	}
	
	public void showNoImage() {
		parent.fill(r,g,b,alpha);
		parent.rect(x, y, width, height);
	}
	
	public void updateGif() {
		if(gif != null)
			gif.update();
	}
	
	public void setColor(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public boolean isTouching(Block other) {
		return x < other.x + other.width && x + width > other.x && y < other.y + other.height && y + height > other.y;
	}
	
	public float getX() {
		return x;
	}
	
	public void setX(float in) {
		x = in;
	}
	
	public float getY() {
		return y;
	}
	
	public void setY(float in) {
		y = in;
	}

	public float getWidth() {
		return width;
	}
	public float getHeight() {
		return height;
	}
	
	public void setWidth(float in) {
		width = in;
	}
	public void setHeight(float in) {
		height = in;
	}
	
	public int getR() {
		return r;
	}
	public int getG() {
		return g;
	}
	public int getB() {
		return b;
	}
	
	public boolean isTouching(float x1, float y1, float width1, float height1) {
		return x < x1 + width1 && x + width > x1 && y < y1 + height1 && y + height > y1;
	}
}
