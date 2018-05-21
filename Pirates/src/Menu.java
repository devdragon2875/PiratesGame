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
	private boolean tipB;
	private Button tips;
	private Button exitButton;
	private PImage dockImage;
	
	public Menu(PApplet drawer) {
		this.drawer = drawer;
		a = new Animation("menuanim/", 11, drawer, 6, ".gif");
		wasd = new Animation("wasdstuff/", 5, drawer, 15, ".gif");
		switchScreen = false;
		normal = true;
		rules = true;
		play = new Button(drawer, drawer.width / 2 - 150, 400, 300, 75, "Play");
		play.setTextSize(32);
		play.setClickColor(new Color(255, 255, 255));
		play.setDefaultColor(new Color(0, 120, 230, 120));
		play.setHoverColor(new Color(0, 180, 255));
		play.setTextColor(new Color(255, 255, 255));

		howToPlay = new Button(drawer, drawer.width / 2 - 113, 525, 230, 55, "Controls");
		howToPlay.setClickColor(new Color(255, 255, 255));
		howToPlay.setDefaultColor(new Color(255, 183, 51));
		howToPlay.setHoverColor(new Color(255, 220, 150));
		howToPlay.setTextColor(new Color(255, 255, 255));
		
		drawer.textSize(25);
		tips = new Button(drawer, drawer.width / 2 - 90, 625, 180, 45, "Tips");
		tips.setClickColor(new Color(255, 255, 255));
		tips.setDefaultColor(new Color(50, 183, 51));
		tips.setHoverColor(new Color(50, 220, 150));
		tips.setTextColor(new Color(255, 255, 255));

		exitButton = new Button(drawer, drawer.width - 45, 0, 40, 40, "X");
		exitButton.setHoverColor(new Color(255, 100, 100));
		exitButton.setDefaultColor(new Color(200, 0, 0));
		
		dockImage = drawer.loadImage("Dock.png");
	}

	public void update() {
		if (normal) {
			a.update();
			if (howToPlay.update()) {
				normal = false;
				rules = true;
				tipB = false;
			}

			if (play.update()) {

				switchScreen = true;
			}
			
			if(tips.update()) {
				normal = false;
				rules = false;
				tipB = true;
			}
			
		} else if (rules) {
			wasd.update();
			if (exitButton.update()) {
				
				normal = true;
				rules = false;
				tipB = false;
			}
			
		} else if(tipB) {
			if (exitButton.update()) {
				
				normal = true;
				rules = false;
				tipB = false;
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
			
			drawer.fill(255, 183, 51);
			
			drawer.text("PIRATES", 625,200);
			play.showRounded(50);
			howToPlay.showRounded(50);
			tips.showRounded(50);
		} else if (rules) {
			drawer.background(0, 0, 0);
			wasd.display(200, 120, 400, 300);
			drawer.textSize(50);
			drawer.fill(255);
			drawer.text("Controls", 500, 100);
			drawer.textSize(30);
			drawer.textAlign(drawer.LEFT);
			drawer.text("Press W to move forward", 150, 450);
			drawer.text("Press S to move backward", 150, 500);
			drawer.text("Press A to rotate left", 150, 550);
			drawer.text("Press D to rotate right", 150, 600);
			drawer.text("Press M or SHIFT to open the map", 150, 650);
			drawer.text("Click on the green sections of\nyour ship to fire the cannons", 150, 700);
			drawer.textAlign(drawer.CENTER);
			
			exitButton.show();
		} else if (tipB) { 
			drawer.textSize(50);
			drawer.background(0, 0, 0);
			drawer.fill(255);
			drawer.text("Tips", 500, 100);
			drawer.image(dockImage, 100, 200,100,100);
			
			drawer.textSize(30);
			drawer.textAlign(drawer.LEFT);
			
			drawer.text("Visit docks to trade items\nand upgrade your ship.", 250, 250);
			
			drawer.text("Try to buy materials when they are cheap <11G and sell them when they are expensive >13G to make a profit.\n\nOnly upgrade your ship when you have enough money left over to continue trading materials.", 100, 350,1000,400);
			
			drawer.textAlign(drawer.CENTER);

			exitButton.show();
		}
	}

	public boolean getSwitchScreen() {
		// TODO Auto-generated method stub
		return switchScreen;
	}
}
