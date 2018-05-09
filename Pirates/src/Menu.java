import processing.core.PApplet;
import processing.core.PImage;

/**
 * This class is the start menu and is used to inform the user about how to play the game.
 * @author Devansh
 *
 */
public class Menu {
	private PImage back;
	private PApplet drawer;
	Animation a;
	public Menu(PApplet drawer) {
		this.drawer = drawer;
		a = new Animation("menu/", 20, drawer,1);
		
	}
	public void draw() {
		
		//a.display(0, 0, drawer.width, drawer.height);
		a.displayReverse(0, 0, drawer.width, drawer.height);
		drawer.textSize(50);
		drawer.text("Play", 100, 200);
	}
}
