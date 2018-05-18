import processing.core.PApplet;
import processing.core.PImage;

/**
 * This class is used to represent one section's part of the upgrade screen.
 * @author Blake
 *
 */
public class UpgradePart {

	private Button upgrade;
	private int sectionType;
	private float x, y, width, height;
	private PApplet parent;
	//private int upgradeCost;
	//private int currentLevel;
	
	public UpgradePart(PApplet parent, float x, float y, float width, float height, int sectionType) {
		this.sectionType = sectionType;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.parent = parent;
		//currentLevel = 1;
		
		upgrade = new Button(parent,x+width-height,y+height*3/8,height,height/2,"Upgrade");
		//upgradeCost = 150;
	}
	
	public void show(Player p) {
		parent.pushMatrix();
		parent.pushStyle();
		
		parent.stroke(0);
		parent.strokeWeight(2);
		parent.fill(200,150,50);

		parent.rect(x, y, width, height);
		String thing = "";
		switch(sectionType) {
		case UpgradeScreen.WEAPON:
			thing = "Weapons";
			break;
		case UpgradeScreen.LOOKOUT:
			thing = "Lookout";
			break;
		case UpgradeScreen.STEERING:
			thing = "Steering";
			break;
		}
		parent.fill(0);
		parent.textSize(35);
		parent.text(thing, x+width/6, y+height/4);
		
		parent.textSize(30);
		
		
		
		if(p.getSection(sectionType).getLevel() < 3) {
			int fLevel = p.getSection(sectionType).getStat(p.getSection(sectionType).getLevel());
			int nLevel = p.getSection(sectionType).getStat(p.getSection(sectionType).getLevel()+1);
			switch(sectionType) {
			case UpgradeScreen.WEAPON:
				parent.text("Cannon Damage:\n"+fLevel+" -> "+nLevel,x+width/3,y+height*1/2);
				break;
			case UpgradeScreen.LOOKOUT:
				parent.text("Map View Distance:\n"+fLevel+" -> "+nLevel,x+width/3,y+height*1/2);
				break;
			case UpgradeScreen.STEERING:
				parent.text("Movement Speed:\n"+fLevel+" -> "+nLevel,x+width/3,y+height*1/2);
				break;
			}
			parent.text("Price: " + getCost(p) + "G", x+width-height/2-height/4, y+height/4);
		} else {
			int fLevel = p.getSection(sectionType).getStat(p.getSection(sectionType).getLevel());
			switch(sectionType) {
			case UpgradeScreen.WEAPON:
				parent.text("Cannon Damage:"+fLevel,x+width/3,y+height*1/2);
				break;
			case UpgradeScreen.LOOKOUT:
				parent.text("Map View Distance:"+fLevel,x+width/3,y+height*1/2);
				break;
			case UpgradeScreen.STEERING:
				parent.text("Movement Speed:"+fLevel,x+width/3,y+height*1/2);
				break;
			}
			upgrade.setText("Fully Upgraded");
		}
		upgrade.show();
		
		parent.popMatrix();
		parent.popStyle();
	}
	
	public boolean updateButton(Player p) {
		if(p.getSection(sectionType).getLevel() < 3)
			return upgrade.update();
		return false;
	}
	
	public int getCost(Player p) {
		return p.getSection(sectionType).getLevel()*100+50;
	}
}
