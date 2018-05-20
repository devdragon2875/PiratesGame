
public class Treasure {
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
		x = player.getX()+player.getWidth()/2.0f;
		y = player.getY()+player.getHeight()/2.0f;
	}

	
	
	
}
