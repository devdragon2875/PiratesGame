
public class Cargo {

	private int gold;
	private int maxSpace;
	private int numCloth, numSpices, numJewelry, numBeards;
	
	public Cargo(int maxSpace) {
		this.maxSpace = maxSpace;
		gold = 0;
		numCloth = 0;
		numSpices = 0;
		numJewelry = 0;
		numBeards = 0;
	}
	
	//public int getSpaceUsed() {
	//	return 
	//}

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
