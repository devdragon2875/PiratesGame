import processing.core.PApplet;

public class TradeScreen {

	private TradePart[] parts;
	private PApplet parent;
	
	public TradeScreen(PApplet parent){
		this(parent,(float)(Math.random()*5 + 10),(float)(Math.random()*5 + 10),(float)(Math.random()*5 + 10),(float)(Math.random()*5 + 10));
	}
	
	public TradeScreen(PApplet parent, float aPrice, float bPrice, float cPrice, float dPrice){
		this.parent = parent;
		parts = new TradePart[4];
		parts[0] = new TradePart(parent,aPrice,0,0,parent.width/4,parent.height,parent.loadImage("Cloth.png"),"Cloth",Cargo.CLOTH);
		parts[1] = new TradePart(parent,bPrice,parent.width/4,0,parent.width/4,parent.height,parent.loadImage("Spices.png"),"Spices",Cargo.SPICES);
		parts[2] = new TradePart(parent,cPrice,parent.width/2,0,parent.width/4,parent.height,parent.loadImage("Jewelry.png"),"Jewelry",Cargo.JEWELRY);
		parts[3] = new TradePart(parent,dPrice,parent.width*3/4,0,parent.width/4,parent.height,parent.loadImage("Beards.png"),"Beards",Cargo.BEARDS);
	}
	
	public void show(){
		parent.fill(255);
		parent.strokeWeight(1);
		for(int i = 0; i < parts.length; i++){
			parts[i].show();
		}
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
}
