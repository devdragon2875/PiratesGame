import java.awt.Color;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * This class is used to represent the trade screen the player will see when at a dock.
 * @author Blake
 *
 */
public class TradeScreen {

	private TradePart[] parts;
	private PApplet parent;
	private PImage clothImg, spicesImg, jewelryImg, beardsImg;
	private Button exitButton;
	//private Player player;
	
	public TradeScreen(PApplet parent){
		this(parent,(float)(Math.random()*5 + 10),(float)(Math.random()*5 + 10),(float)(Math.random()*5 + 10),(float)(Math.random()*5 + 10));
	}
	
	public TradeScreen(PApplet parent, float aPrice, float bPrice, float cPrice, float dPrice){
		this.parent = parent;
		parts = new TradePart[4];
		clothImg = parent.loadImage("Cloth.png");
		spicesImg = parent.loadImage("Spices.png");
		jewelryImg = parent.loadImage("Jewelry.png");
		beardsImg = parent.loadImage("Beards.png");
		parts[0] = new TradePart(parent,aPrice,0,0,parent.width/5,parent.height,clothImg,"Cloth",Cargo.CLOTH);
		parts[1] = new TradePart(parent,bPrice,parent.width/5,0,parent.width/5,parent.height,spicesImg,"Spices",Cargo.SPICES);
		parts[2] = new TradePart(parent,cPrice,parent.width*2/5,0,parent.width/5,parent.height,jewelryImg,"Jewelry",Cargo.JEWELRY);
		parts[3] = new TradePart(parent,dPrice,parent.width*3/5,0,parent.width/5,parent.height,beardsImg,"Beards",Cargo.BEARDS);
		exitButton = new Button(parent,parent.width-45,0,40,40,"X");
		exitButton.setHoverColor(new Color(255,100,100));
		exitButton.setDefaultColor(new Color(200,0,0));
	}
	
	public void show(Player player){
		parent.fill(255);
		parent.strokeWeight(1);
		for(int i = 0; i < parts.length; i++){
			parts[i].show();
		}
		parent.fill(255,235,200);
		parent.rect(parent.width*4/5, 0, parent.width/5, parent.height);
		
		float offset = parent.width*4/5;
		//parent.rect(offset+parent.width/100, parent.height*3/8, parent.width/15, parent.width/15);
		parent.image(clothImg, offset+parent.width/100, parent.height*3/8,parent.width/15,parent.width/15);
		parent.image(spicesImg, offset+parent.width/100, parent.height/2,parent.width/15,parent.width/15);
		parent.image(jewelryImg, offset+parent.width/100, parent.height*5/8,parent.width/15,parent.width/15);
		parent.image(beardsImg, offset+parent.width/100, parent.height*6/8,parent.width/15,parent.width/15);
		
		parent.fill(0);
		parent.textSize(40);
		parent.textAlign(parent.CENTER);
		parent.text(player.getCargo().getNumCloth(), offset+parent.width/100+parent.width/8, parent.height*3/8+parent.width/20);
		parent.text(player.getCargo().getNumSpices(), offset+parent.width/100+parent.width/8, parent.height/2+parent.width/20);
		parent.text(player.getCargo().getNumJewelry(), offset+parent.width/100+parent.width/8, parent.height*5/8+parent.width/20);
		parent.text(player.getCargo().getNumBeards(), offset+parent.width/100+parent.width/8, parent.height*6/8+parent.width/20);
		
		parent.textSize(30);
		
		parent.text(player.getCargo().getGold()+"G", offset+parent.width/10, parent.height/4);
		parent.text("Player:", offset+parent.width/10, 130);
		
		exitButton.show();
	}
	
	public void update(Player player){
		
		
		for(TradePart p : parts) {
			//p.update();
			p.updateTrade(player.getCargo());
		}
		String s = "";
		for(int i = 1; i <= 4; i++)
			s += player.getCargo().getMaterial(i) + "|";
		s += ", Gold : " + player.getCargo().getGold();
		System.out.println(s);
	}
	
	public boolean checkExitButton() {
		return exitButton.update();
	}
}
