import processing.core.PApplet;

/**
 * This class is made to represent a dock on the map which the player can interact and trade at.
 * @author Blake
 *
 */
public class Dock extends Block{

	private TradeScreen tradeScreen;
	
	public Dock(PApplet parent, float x, float y, float width,float height) {
		super(parent, x, y, width, height);
		tradeScreen = new TradeScreen(parent);
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

}
