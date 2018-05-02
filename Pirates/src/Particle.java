import processing.core.PApplet;

public class Particle extends Block{
	
	private int timeLeft;
	private float sizeChange, rChange, gChange, bChange, xChange, yChange,aChange;

	public Particle(PApplet parent, float x, float y, float width,float height, int timeLeft) {
		super(parent, x, y, width, height);
		this.timeLeft = timeLeft;
		sizeChange = 0;
		rChange = 0;
		gChange = 0;
		bChange = 0;
		xChange = 0;
		yChange = 0;
		aChange = 0;
	}
	
	//generates "smoke" for the bullet
	public Particle(PApplet parent, Bullet b, int timeLeft) {
		this(parent, b.getX(), b.getY(),b.getWidth(), b.getHeight(), timeLeft);
		super.setColor(b.getR(), b.getG(), b.getB());
		setSizeChange(1);
		setAlphaChange(-10);
	}

	public void update() {
		width += sizeChange;
		height += sizeChange;
		r += rChange;
		g += gChange;
		b += bChange;
		x += xChange - sizeChange/2f;
		y += yChange - sizeChange/2f;
		alpha += aChange;
		timeLeft--;
	}
	
	public boolean shouldBeDead() {
		return timeLeft < 1;
	}

	public float getSizeChange() {
		return sizeChange;
	}

	public void setSizeChange(float sizeChange) {
		this.sizeChange = sizeChange;
	}

	public float getRChange() {
		return rChange;
	}

	public void setRChange(float rChange) {
		this.rChange = rChange;
	}

	public float getGChange() {
		return gChange;
	}

	public void setGChange(float gChange) {
		this.gChange = gChange;
	}

	public float getBChange() {
		return bChange;
	}

	public void setBChange(float bChange) {
		this.bChange = bChange;
	}

	public float getXChange() {
		return xChange;
	}

	public void setXChange(float xChange) {
		this.xChange = xChange;
	}

	public float getYChange() {
		return yChange;
	}

	public void setYChange(float yChange) {
		this.yChange = yChange;
	}

	public float getAlphaChange() {
		return aChange;
	}

	public void setAlphaChange(float aChange) {
		this.aChange = aChange;
	}
	
}
