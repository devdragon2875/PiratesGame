import processing.core.PApplet;

/**
 * This class is used to represent an enemy a player can fight.
 * @author Blake
 *
 */
public class Enemy extends Player{
	
	public Enemy(PApplet parent, float x, float y, float width,float height, int health) {
		super(parent, x, y, width, height, health);
		maxXV = 5;
		maxYV = 5;
	}
	
	public void act(Player p) {
		if(getX() < p.getX()) {
			changeXV(1);
		} else if(getX() > p.getX()){
			changeXV(-1);
		}
		
		if(getY() < p.getY()) {
			changeYV(1);
		} else if(getY() > p.getY()){
			changeYV(-1);
		}
	}

	
}
