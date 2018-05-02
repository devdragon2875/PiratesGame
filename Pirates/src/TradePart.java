import processing.core.PApplet;
import processing.core.PImage;

public class TradePart {

	private float price;
	private Button buy1, buy10, sell1, sellAll;
	private float x, y, width, height;
	private PApplet parent;
	private PImage image;
	private String name;
	
	public TradePart(PApplet parent, float price, float x, float y, float width, float height, PImage image, String name){
		this.parent = parent;
		this.price = price;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.image = image;
		this.name = name;
		
		float xBorder = width/30;
		float yBorder = height/300;
		buy1 = new Button(parent,x+xBorder,y + height/2+yBorder, width/2-xBorder*2, height/8-yBorder*2, "Buy 1");
		buy10 = new Button(parent,x+width/2+xBorder,y + height/2+yBorder, width/2-xBorder*2, height/8-yBorder*2, "Buy 10");
		sell1 = new Button(parent,x+xBorder,y + height/2 + height/4+yBorder, width/2-xBorder*2, height/8-yBorder*2, "Sell 1");
		sellAll = new Button(parent,x + width/2+xBorder,y + height/2 + height/4+yBorder, width/2-xBorder*2, height/8-yBorder*2, "Sell All");
	}
	
	public void show(){
		parent.pushMatrix();
		parent.pushStyle();
		
		parent.stroke(0);
		parent.strokeWeight(2);
		parent.fill(200,150,50);
		parent.rect(x, y, width, height);
		
		parent.fill(0);
		parent.textAlign(parent.CENTER);
		parent.textSize(40);
		parent.text(name, x+width/2, y+height/15);
		
		buy1.show();
		buy10.show();
		sell1.show();
		sellAll.show();
		
		
		float xBorder = width/10;
		float yBorder = height/10;
		parent.fill(255,235,200);
		parent.rect(x+xBorder, y+yBorder, width-xBorder*2, width-xBorder*2);
		
		parent.image(image, x+xBorder, y+yBorder,width-xBorder*2,width-xBorder*2);
		
		parent.popMatrix();
		parent.popStyle();
	}
	
	public void update(){
		buy1.update();
		buy10.update();
		sell1.update();
		sellAll.update();
	}
}
