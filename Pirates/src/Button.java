import java.awt.Color;

import processing.core.PApplet;

/**
 * This class is used to detect if the user is clicking on a rectangular area.
 * @author Blake
 *
 */
public class Button{

	private String text;
	private float x,y,width,height;
	private PApplet parent;
	private Color fillColor, borderColor, textColor, clickColor, hoverColor, defaultColor;
	private boolean clicked; // boolean so that it will only register clicking once when holding down the mouse
	
	private int textSize;
	
	public Button(PApplet parent, float x, float y, float width, float height, String text) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.parent = parent;
		this.text = text;
		fillColor = new Color(255,255,255);
		borderColor = new Color(0,0,0);
		textColor = new Color(0,0,0);
		
		clickColor = new Color(100,255,100);
		hoverColor = new Color(255,255,100);
		defaultColor = new Color(255,235,200);
		
		clicked = true;
		
		textSize = 25;
	}

	public boolean isPointInside(float x, float y){
		return x > this.x && x < this.x + width && y > this.y && y < this.y + height;
	}
	
	public boolean isMouseHovering(){
		return isPointInside(parent.mouseX,parent.mouseY);
	}
	
	public boolean isClicked(){
		if(isMouseHovering() && parent.mousePressed) {
			if(!clicked) {
				clicked = true;
				return true;
			}
		} else {
			clicked = false;
		}
		return false;
	}
	public void showRounded(int roundFactor) {
		parent.pushMatrix();
		parent.pushStyle();
		
		parent.fill(fillColor.getRed(),fillColor.getGreen(),fillColor.getBlue());
		parent.stroke(borderColor.getRed(),borderColor.getGreen(),borderColor.getBlue());
		
		parent.rect(x, y, width, height, roundFactor);
		parent.fill(textColor.getRed(),textColor.getGreen(),textColor.getBlue());
		parent.textAlign(parent.CENTER);
		parent.textSize(textSize);
		
		parent.text(text, x, y+height/4,width,height);
		parent.popStyle();
		parent.popMatrix();
	}
	public void show(){
		parent.pushMatrix();
		parent.pushStyle();
		
		parent.fill(fillColor.getRed(),fillColor.getGreen(),fillColor.getBlue());
		parent.stroke(borderColor.getRed(),borderColor.getGreen(),borderColor.getBlue());
		
		parent.rect(x, y, width, height);
		parent.fill(textColor.getRed(),textColor.getGreen(),textColor.getBlue());
		parent.textAlign(parent.CENTER);
		parent.textSize(textSize);
		
		parent.text(text, x, y+height/4,width,height);
		parent.popStyle();
		parent.popMatrix();
	}
	
	public boolean update(){ // returns true if it is clicked
		if(isClicked()){
			fillColor = new Color(clickColor.getRGB());
			return true;
		} else if(isMouseHovering()) {
			fillColor = new Color(hoverColor.getRGB());
		} else {
			fillColor = new Color(defaultColor.getRGB());
		}
		return false;
	}
	
	public void setTextSize(int in) {
		textSize = in;
	}
	
	public void setClickColor(Color c) {
		this.clickColor = c;
	}
	
	public void setHoverColor(Color c) {
		this.hoverColor = c;
	}
	
	public void setDefaultColor(Color c) {
		this.defaultColor = c;
	}
	public void setBorderColor(Color c) {
		this.borderColor = c;
	}
	public void setTextColor(Color c) {
		this.textColor = c;
	}
	public void setText(String text) {
		this.text = text;
	}
}
