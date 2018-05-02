import java.awt.Color;

import processing.core.PApplet;

public class Button{

	private String text;
	private float x,y,width,height;
	private PApplet parent;
	private Color fillColor, borderColor, textColor;
	
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
	}

	public boolean isPointInside(float x, float y){
		return x > this.x && x < this.x + width && y > this.y && y < this.y + height;
	}
	
	public boolean isMouseHovering(){
		return isPointInside(parent.mouseX,parent.mouseY);
	}
	
	public boolean isClicked(){
		return isMouseHovering() && parent.mousePressed;
	}
	
	public void show(){
		parent.pushMatrix();
		parent.pushStyle();
		
		parent.fill(fillColor.getRed(),fillColor.getGreen(),fillColor.getBlue());
		parent.stroke(borderColor.getRed(),borderColor.getGreen(),borderColor.getBlue());
		
		parent.rect(x, y, width, height);
		parent.fill(textColor.getRed(),textColor.getGreen(),textColor.getBlue());
		parent.textAlign(parent.CENTER);
		parent.textSize(20);
		parent.text(text, x+width/8, y+height/4,width*3/4,height);
		
		parent.popStyle();
		parent.popMatrix();
	}
	
	public void update(){
		if(isClicked()){
			fillColor = new Color(100,255,100);
		} else if(isMouseHovering())
			fillColor = new Color(255,255,100);
		else
			fillColor = new Color(255,235,200);
		
	}
}
