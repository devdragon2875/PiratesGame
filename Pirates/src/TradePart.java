import processing.core.PApplet;
import processing.core.PImage;

/**
 * This class is used to represent one material's part of the trade screen.
 * 
 * @author Blake and Anantajit
 *
 */
public class TradePart {

	private float price;

	// private Button buy1, buy10, sell1, sellAll;
	private Button buy1, sell1;
	private float x, y, width, height;
	private PApplet parent;
	private PImage image;
	private String name;
	private int materialType;

	public TradePart(PApplet parent, float price, float x, float y, float width, float height, PImage image,
			String name, int materialType) {
		this.parent = parent;
		this.price = price;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.image = image;
		this.name = name;
		this.materialType = materialType;

		float xBorder = width / 30;
		float yBorder = height / 300;
		/*
		 * buy1 = new Button(parent,x+xBorder,y + height/2+yBorder, width/2-xBorder*2,
		 * height/8-yBorder*2, "Buy 1"); buy10 = new Button(parent,x+width/2+xBorder,y +
		 * height/2+yBorder, width/2-xBorder*2, height/8-yBorder*2, "Buy 10"); sell1 =
		 * new Button(parent,x+xBorder,y + height/2 + height/4+yBorder,
		 * width/2-xBorder*2, height/8-yBorder*2, "Sell 1"); sellAll = new
		 * Button(parent,x + width/2+xBorder,y + height/2 + height/4+yBorder,
		 * width/2-xBorder*2, height/8-yBorder*2, "Sell All");
		 */
		buy1 = new Button(parent, x + xBorder, y + height / 2 + yBorder, width - xBorder * 2, height / 8 - yBorder * 2,
				"Buy 1");
		sell1 = new Button(parent, x + xBorder, y + height / 2 + height / 4 + yBorder, width - xBorder * 2,
				height / 8 - yBorder * 2, "Sell 1");
	}

	public void show() {
		parent.pushMatrix();
		parent.pushStyle();

		parent.stroke(0);
		parent.strokeWeight(2);
		parent.fill(200, 150, 50);
		parent.rect(x, y, width, height);

		parent.fill(0);
		parent.textAlign(parent.CENTER);
		parent.textSize(35);
		parent.text(name, x + width / 2, y + height / 15);
		parent.text("Price:\n" + (int) price + "G", x + width / 2, y + height * 7 / 16);
		buy1.show();
		// buy10.show();
		sell1.show();
		// sellAll.show();

		float xBorder = width / 10;
		float yBorder = height / 10;
		parent.fill(255, 235, 200);
		parent.rect(x + xBorder, y + yBorder, width - xBorder * 2, width - xBorder * 2);

		parent.image(image, x + xBorder, y + yBorder, width - xBorder * 2, width - xBorder * 2);

		parent.popMatrix();
		parent.popStyle();
	}

	/*
	 * public void update(){ buy1.update(); buy10.update(); sell1.update();
	 * sellAll.update(); }
	 */
	public void updateTrade(Cargo cargo) {
		if (buy1.update()) {
			System.out.println(name + " buy1 was clicked.");
			if (cargo.getGold() >= price && cargo.getEmptySpace() >= 1) {
				cargo.setGold(cargo.getGold() - (int) price);
				cargo.changeMaterial(materialType, 1);
				updatePrice(1);
			}
		} /*
			 * else if(buy10.update()) { System.out.println(name + " buy10 was clicked.");
			 * if(cargo.getGold() >= price*10 && cargo.getEmptySpace() >= 10) {
			 * cargo.setGold(cargo.getGold() - (int)(price)*10);
			 * cargo.changeMaterial(materialType, 10); updatePrice(10); } }
			 */else if (sell1.update()) {
			System.out.println(name + " sell1 was clicked.");
			if (cargo.getMaterial(materialType) >= 1) {
				cargo.setGold(cargo.getGold() + (int) price);
				cargo.changeMaterial(materialType, -1);
				updatePrice(-1);
			}
		} /*
			 * else if(sellAll.update()) { System.out.println(name +
			 * " sellAll was clicked."); if(cargo.getMaterial(materialType) > 0) { int
			 * numMaterial = cargo.getMaterial(materialType); cargo.setGold(cargo.getGold()
			 * + (int)price*numMaterial); cargo.setMaterial(materialType, 0);
			 * updatePrice(-numMaterial); } }
			 */
	}

	private void updatePrice(int n) { // updates the price of the good assuming n items were bought (n can be
										// negative, which will assume n items were sold)
		if (n == 0)
			return;
		// double factor = 1.01; // should be tweaked
		double change = 0.1;
		if (n < 0) {
			n *= -1;
			// factor = 0.99; // should be tweaked
			change = -0.1;
		}

		for (int i = 0; i < n; i++) {
			// price *= factor;
			price += change;
		}
		Dock.push = true;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
}
