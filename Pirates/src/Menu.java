import java.awt.Color;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * This class is the start menu and is used to inform the user about how to play
 * the game.
 * 
 * @author Devansh
 *
 */
public class Menu {
	private PImage back;
	private PApplet drawer;
	private Animation a;
	private Animation wasd;
	private Button play;
	private Button howToPlay;
	private boolean switchScreen;
	private boolean normal;
	private boolean rules;
	private Button exitButton;

	public Menu(PApplet drawer) {
		this.drawer = drawer;
		a = new Animation("menuanim/", 11, drawer, 6, ".gif");
		wasd = new Animation("wasdstuff/", 5, drawer, 15, ".gif");
		switchScreen = false;
		normal = true;
		rules = true;
		play = new Button(drawer, drawer.width / 2 - 150, 400, 300, 75, "Play");
		play.setTextSize(35);
		play.setClickColor(new Color(255, 255, 255));
		play.setDefaultColor(new Color(0, 120, 230, 120));
		play.setHoverColor(new Color(0, 180, 255));
		play.setTextColor(new Color(255, 255, 255));

		howToPlay = new Button(drawer, drawer.width / 2 - 100, 525, 200, 50, "Rules");
		howToPlay.setTextSize(25);
		howToPlay.setClickColor(new Color(255, 255, 255));
		howToPlay.setDefaultColor(new Color(255, 183, 51));
		howToPlay.setHoverColor(new Color(255, 220, 150));
		howToPlay.setTextColor(new Color(255, 255, 255));

		exitButton = new Button(drawer, drawer.width - 45, 0, 40, 40, "X");
		exitButton.setHoverColor(new Color(255, 100, 100));
		exitButton.setDefaultColor(new Color(200, 0, 0));
	}

	public void update() {
		if (normal) {
			a.update();
			if (howToPlay.update()) {
				normal = false;
				rules = true;
			}

			if (play.update()) {

				switchScreen = true;
			}
		} else if (rules) {
			wasd.update();
			if (exitButton.update()) {
				
				normal = true;
				rules = false;
			}
			
		}
	}

	public void draw() {

		// a.display(0, 0, drawer.width, drawer.height);
		if (normal) {
			drawer.textAlign(drawer.CENTER);
			a.display(0, 0, drawer.width, drawer.height);
			drawer.textSize(120);
			drawer.fill(0);
			for(int x = -2; x < 3; x++){
			    drawer.text("PIRATES", 625+x,200);
			    drawer.text("PIRATES", 625,200+x);
			}
			
			drawer.fill(220);
			
			drawer.text("PIRATES", 625,200);
			play.showRounded(50);
			howToPlay.showRounded(50);
		} else if (rules) {
			drawer.background(0, 0, 0);
			wasd.display(200, 200, 400, 300);
			exitButton.show();
		}
	}

	public boolean getSwitchScreen() {
		// TODO Auto-generated method stub
		return switchScreen;
	}
}
