import java.awt.Color;

import processing.core.PApplet;

public class MapDrawer {
	public void drawNoiseMap(double[][] noiseMap, PApplet drawer) {
		int width = noiseMap.length;
		int height = noiseMap[0].length;
		Color[][] colorMap = new Color[width][height];
		for(int y = 0; y < width; y++) {
			for(int x = 0; x < width; x++) {
				Color color = new Color(drawer.lerpColor(Color.BLACK.getRGB(), Color.WHITE.getRGB(), (float)noiseMap[x][y]));
				colorMap[x][y] = color;
			}
		}
		
		for(int i = 0; i < colorMap.length; i++) {
			for(int j = 0; j < colorMap[i].length; j++) {
				int r = colorMap[i][j].getRed();
				int g = colorMap[i][j].getGreen();
				int b = colorMap[i][j].getBlue();
				
				drawer.fill(r, g , b);
				drawer.rect(i*drawer.width/colorMap.length, j*drawer.height/colorMap[i].length, drawer.width/colorMap.length, drawer.height/colorMap[i].length);
			}
		}
	}
	public void drawColorMap(Color[][] colorMap, PApplet drawer) {
		
		for(int i = 0; i < colorMap.length; i++) {
			for(int j = 0; j < colorMap[i].length; j++) {
				int r = colorMap[i][j].getRed();
				int g = colorMap[i][j].getGreen();
				int b = colorMap[i][j].getBlue();
				
				drawer.fill(r, g , b);
				drawer.rect(i*drawer.width/colorMap.length, j*drawer.height/colorMap[i].length, drawer.width/colorMap.length, drawer.height/colorMap[i].length);
			}
		}
	}
}
