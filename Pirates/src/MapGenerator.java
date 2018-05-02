import java.awt.Color;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

import processing.core.PApplet;

public class MapGenerator {
	public int mapWidth = 100;
	public int mapHeight = 100;
	public double noiseScale = 20;
	public int octaves = 4;
	public double persistance =0.3;
	public double lacunarity = 2;
	public int seed = 21;
	public boolean spawn = false;
	Vector<Double> v = new Vector<Double>();
	private String str = System.getProperty("line.separator");
	char lineSep = str.charAt(0);
	
	
	public void GenerateMap(PApplet drawer) {
		v.add(13.4);
		v.add(6.26);
		BufferedOutputStream writer = null;
		try {
			writer = new BufferedOutputStream(new FileOutputStream("output.txt"));
			Color[][] colorMap = new Color[mapWidth][mapHeight];
			double[][] noiseMap = Noise.generateMap(mapWidth, mapHeight, seed, noiseScale, octaves, persistance, lacunarity, v, drawer);
			for(int y = 0; y < mapHeight; y++) {
				for(int x = 0; x < mapWidth; x++) {
					double currH = noiseMap[x][y];
					if(currH > 0.75) {
						Color color = new Color(40, 130, 20);
						colorMap[x][y] = color;
						writer.write('l');
					} else if(currH > 0.7) {
						Color color = new Color(219, 209, 0);
						colorMap[x][y] = color;
						writer.write('s');
					} else if(currH > 0.699) {
						Color color = new Color(219, 0, 209);
						colorMap[x][y] = color;
						writer.write('d');
					}else {
						Color color = new Color(0, 50, 200);
						colorMap[x][y] = color;
						
						if(!spawn) {
							writer.write('p');
							spawn = true;
						} else {
							writer.write('w');
						}
						
					}
				}
				if(y != mapHeight-1)
					writer.write(lineSep);
			}
			//MapDrawer display = new MapDrawer();
			//display.drawNoiseMap(noiseMap, drawer);
			//display.drawColorMap(colorMap, drawer);
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if(writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
