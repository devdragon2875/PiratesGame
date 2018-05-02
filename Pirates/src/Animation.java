import processing.core.PApplet;
import processing.core.PImage;

public class Animation {
  PImage[] images;
  int imageCount;
  int frame;
  private PApplet drawer;
  private boolean reverse;
  private int delay;
  private int timer;
  
  public Animation(String imagePrefix, int count, PApplet drawer, int delay) {
    imageCount = count;
    this.drawer = drawer;
    images = new PImage[imageCount];
    reverse = false;
    for (int i = 0; i < imageCount; i++) {
      // Use nf() to number format 'i' into four digits
      String filename = imagePrefix + i + ".png";
      images[i] = drawer.loadImage(filename);
    }
    this.delay = delay;
    timer = 0;
  }

  public void display(float xpos, float ypos, float width, float height) {
    drawer.image(images[frame], xpos, ypos, width, height);
  }
  
  public void update() {
	  if(timer >= delay) {
			frame = (frame+1) % imageCount;
			timer = 0;
		} else
			timer++;
  }
  
  public void displayReverse(float xpos, float ypos, float width, float height) {
	  	if(reverse) {
	  		frame = (frame-1) % imageCount;
	  		drawer.image(images[frame], xpos, ypos, width, height);
	  		if(frame <= 0) {
	  			reverse = false;
	  		}
	  	} else {
	  		this.display(xpos, ypos, width, height);
	  		if(frame >= imageCount -1) {
	  			reverse = true;
	  		}
	  	}
  }
  public void displayRandom(float xpos, float ypos, float width, float height) {
	  if(timer >= delay) {
		  	drawer.image(images[(int)(Math.random()*imageCount)], xpos, ypos, width, height);
			timer = 0;
		} else
			timer++;
	  	
	  		
}
  
  public int getWidth() {
    return images[0].width;
  }
  
}