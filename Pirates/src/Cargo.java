
/**
 * This class is used to store statistics about what the player is currently carrying and the player's gold.
 * @author Blake
 *
 */
public class Cargo {

	private int gold;
	private int maxSpace;
	private int numCloth, numSpices, numJewelry, numBeards;
	public static final int CLOTH = 1;
	public static final int SPICES = 2;
	public static final int JEWELRY = 3;
	public static final int BEARDS = 4;

	
	public Cargo(int maxSpace) {
		this.maxSpace = maxSpace;
		gold = 0;
		numCloth = 0;
		numSpices = 0;
		numJewelry = 0;
		numBeards = 0;
	}
	
	public void setMaterial(int type, int newAmount) {
		switch(type) {
		case CLOTH:
			numCloth = newAmount;
			break;
		case SPICES:
			numSpices = newAmount;
			break;
		case JEWELRY:
			numJewelry = newAmount;
			break;
		case BEARDS:
			numBeards = newAmount;
			break;
		}
	}
	
	public int getMaterial(int type) {
		switch(type) {
		case CLOTH:
			return numCloth;
		case SPICES:
			return numSpices;
		case JEWELRY:
			return numJewelry;
		case BEARDS:
			return numBeards;
		default:
			return -1;
		}
	}
	
	public void changeMaterial(int type, int changeBy) {
		switch(type) {
		case CLOTH:
			numCloth += changeBy;
			break;
		case SPICES:
			numSpices += changeBy;
			break;
		case JEWELRY:
			numJewelry += changeBy;
			break;
		case BEARDS:
			numBeards += changeBy;
			break;
		}
	}
	
	public int getEmptySpace() {
		return maxSpace - getSpaceUsed();
	}
	
	public int getSpaceUsed() {
		return numCloth + numSpices + numJewelry + numBeards;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public int getMaxSpace() {
		return maxSpace;
	}

	public void setMaxSpace(int maxSpace) {
		this.maxSpace = maxSpace;
	}

	public int getNumCloth() {
		return numCloth;
	}

	public void setNumCloth(int numCloth) {
		this.numCloth = numCloth;
	}

	public int getNumSpices() {
		return numSpices;
	}

	public void setNumSpices(int numSpices) {
		this.numSpices = numSpices;
	}

	public int getNumJewelry() {
		return numJewelry;
	}

	public void setNumJewelry(int numJewelry) {
		this.numJewelry = numJewelry;
	}

	public int getNumBeards() {
		return numBeards;
	}

	public void setNumBeards(int numBeards) {
		this.numBeards = numBeards;
	}
	
	
	
}
