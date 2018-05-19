import java.io.Serializable;
/**
 * Serializable version of the dock, with only essential information on board
 * @author Anantajit
 *
 */
public class NetworkedDock implements Serializable{
	int ID;
	private float[] prices;
	
	public NetworkedDock(int ID) {
		this.ID = ID;
		prices = new float[4];
	}

	public float[] getPrices() {
		return prices;
	}

	public void setPrices(float[] prices) {
		this.prices = prices;
	}
	
	public int getID() {
		return ID;
	}
}
