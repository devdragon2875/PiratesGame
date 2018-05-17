import processing.core.PApplet;

/**
 * This class is made to represent a dock on the map which the player can interact and trade at.
 * @author Blake
 *
 */
public class Dock extends Block{

	private TradeScreen tradeScreen;
	private UpgradeScreen upgradeScreen;
	private int currentScreen;
	public static final int TRADE = 1;
	public static final int UPGRADE = 2;

	
	public Dock(PApplet parent, float x, float y, float width,float height) {
		super(parent, x, y, width, height);
		tradeScreen = new TradeScreen(parent);
		upgradeScreen = new UpgradeScreen(parent);
		currentScreen = TRADE;
		
	}
	
	public void showTradeScreen(Player p) {
		tradeScreen.show(p);
	}
	
	public void updateTradeScreen(Player p) {
		tradeScreen.update(p);
	}
	
	public boolean checkTradeExitButton() {
		return tradeScreen.checkExitButton();
	}
	
	public boolean checkUpgradeExitButton() {
		return upgradeScreen.checkExitButton();
	}
	
	public void showUpgradeScreen(Player p) {
		upgradeScreen.show(p);
	}
	
	public void updateUpgradeScreen(Player p) {
		upgradeScreen.update(p);
	}
	
	public void showCurrentScreen(Player p) {
		if(currentScreen == TRADE) {
			showTradeScreen(p);
		} else if(currentScreen == UPGRADE) {
			showUpgradeScreen(p);
		}
	}
	
	public void updateCurrentScreen(Player p) {
		if(currentScreen == TRADE) {
			updateTradeScreen(p);
		} else if(currentScreen == UPGRADE) {
			updateUpgradeScreen(p);
		}
	}
	
	public boolean checkCurrentExitButton() {
		if(currentScreen == TRADE) {
			return checkTradeExitButton();
		} else if(currentScreen == UPGRADE) {
			if(checkUpgradeExitButton()) {
				currentScreen = TRADE;
				return true;
			}
		}
		
		return false;
	}
	
	public void checkCurrentSwitchButton() {
		if(currentScreen == TRADE) {
			if(tradeScreen.checkUpgradeButton()) {
				currentScreen = UPGRADE;
			}
		} else if(currentScreen == UPGRADE) {
			if(upgradeScreen.checkTradeButton()) {
				
				currentScreen = TRADE;
			}
		}
	}

}
