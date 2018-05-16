import java.awt.Color;

import processing.core.PApplet;

/**
 * Screen in the dock in which the player can upgrade their ship.
 * @author Blake
 *
 */
public class UpgradeScreen {

	private PApplet parent;
	private Button exitButton;
	private Button tradeButton;

	public UpgradeScreen(PApplet parent) {
		this.parent = parent;
		
		exitButton = new Button(parent,parent.width-45,0,40,40,"X");
		exitButton.setHoverColor(new Color(255,100,100));
		exitButton.setDefaultColor(new Color(200,0,0));
		
		tradeButton = new Button(parent, parent.width*4/5,0, parent.width/5-45, 40, "Trade");
	}
	
	public void show(Player p) {
		p.showNoRotation(parent.width/2, parent.height/2,10);
		exitButton.show();
		tradeButton.show();
	}
	
	public void update(Player p) {
		
	}
	
	public boolean checkExitButton() {
		return exitButton.update();
	}
	
	public boolean checkTradeButton() {
		return tradeButton.update();
	}
}
