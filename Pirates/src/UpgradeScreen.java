import java.awt.Color;
import java.util.ArrayList;

import processing.core.PApplet;

/**
 * Screen in the dock in which the player can upgrade their ship.
 * 
 * @author Blake
 *
 */
public class UpgradeScreen {

	private PApplet parent;
	private Button exitButton;
	private Button tradeButton;

	public static final int WEAPON = 1;
	public static final int LOOKOUT = 2;
	public static final int STEERING = 3;

	private UpgradePart weapons;
	private UpgradePart lookout;
	private UpgradePart steering;

	public UpgradeScreen(PApplet parent) {
		this.parent = parent;

		exitButton = new Button(parent, parent.width - 45, 0, 40, 40, "X");
		exitButton.setHoverColor(new Color(255, 100, 100));
		exitButton.setDefaultColor(new Color(200, 0, 0));

		tradeButton = new Button(parent, parent.width * 4 / 5, 0, parent.width / 5 - 45, 40, "Trade");

		weapons = new UpgradePart(parent, 0, 0, parent.width * 4 / 5, parent.height / 3, WEAPON);
		lookout = new UpgradePart(parent, 0, parent.height / 3, parent.width * 4 / 5, parent.height / 3, LOOKOUT);
		steering = new UpgradePart(parent, 0, parent.height * 2 / 3, parent.width * 4 / 5, parent.height / 3, STEERING);

	}

	public void show(Player p) {

		p.showNoRotation(parent.width - 125, parent.height * 3 / 4, 10);

		weapons.show(p);
		lookout.show(p);
		steering.show(p);

		parent.textSize(30);

		float offset = parent.width * 4 / 5;
		parent.fill(0);
		parent.text(p.getCargo().getGold() + "G", offset + parent.width / 10, parent.height / 4);
		parent.text("Player:", offset + parent.width / 10, 130);

		exitButton.show();
		tradeButton.show();
	}

	public void update(Player p) {
		if (weapons.updateButton(p) && p.getCargo().getGold() > weapons.getCost(p)) {
			p.getCargo().setGold(p.getCargo().getGold() - weapons.getCost(p));
			ArrayList<WeaponSection> weapons = p.getWeapons();
			for (WeaponSection w : weapons) {
				w.upgrade();

			}
		}
		if (lookout.updateButton(p) && p.getCargo().getGold() > lookout.getCost(p)) {
			p.getCargo().setGold(p.getCargo().getGold() - lookout.getCost(p));
			p.getLookout().upgrade();
		}
		if (steering.updateButton(p) && p.getCargo().getGold() > steering.getCost(p)) {
			p.getCargo().setGold(p.getCargo().getGold() - steering.getCost(p));
			p.getSteer().upgrade();
		}
	}

	public boolean checkExitButton() {
		return exitButton.update();
	}

	public boolean checkTradeButton() {
		return tradeButton.update();
	}
}
