import java.io.Serializable;

/**
 * Represents a treasure that is located at a random position in the ocean
 * 
 * @author Anantajit and Devansh
 *
 */
public class Treasure implements Serializable {
	private int cloth;
	private int spices;
	private int jewelry;
	private int beards;
	private int gold;
	private float x;
	private float y;

	public Treasure(Player player) {
		cloth = player.getCargo().getMaterial(1);
		spices = player.getCargo().getMaterial(2);
		jewelry = player.getCargo().getMaterial(3);
		beards = player.getCargo().getMaterial(4);
		gold = player.getCargo().getGold();
		x = player.getX() + player.getWidth() / 2.0f;
		y = player.getY() + player.getHeight() / 2.0f;

	}

	public int getCloth() {
		return cloth;
	}

	public int getSpices() {
		return spices;
	}

	public int getJewelry() {
		return jewelry;
	}

	public int getBeards() {
		return beards;
	}

	public int getGold() {
		return gold;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

}
