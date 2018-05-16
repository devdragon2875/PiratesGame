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
	
	public UpgradePart(PApplet parent, float x, float y, float width, float height, int sectionType) {
		this.sectionType = sectionType;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.parent = parent;
		
		upgrade = new Button(parent,x+width-height,y+height*3/8,height,height/2,"Upgrade");
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
		parent.text("Stats:x -> Stats:y",x+width/3,y+height*3/4);
		
		parent.text("Price:x", x+width-height/2, y+height/4);
		upgrade.show();
		
		
		parent.popMatrix();
		parent.popStyle();
	}
	
	public boolean updateButton() {
		return upgrade.update();
	}
}
