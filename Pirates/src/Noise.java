import java.awt.Point;
import java.util.Random;
import java.util.Vector;

import processing.core.PApplet;

/**
 * This class is used to help randomly generate the map.
 * @author Devansh
 *
 */
public class Noise {
	public static double[][] generateMap(int mapWidth, int mapHeight, int seed, double scale, int octaves, double persistance, double lacunarity, Vector<Double> offset, PApplet drawer){
		double[][] noiseMap = new double[mapWidth][mapHeight];
		Random r= new Random(seed);
		Vector<Double>[] octaveOffsets = new Vector[octaves];
		for(int i = 0; i < octaves; i++) {
			double offsetX = (r.nextDouble()*200000)-100000;
			double offsetY = (r.nextDouble()*200000)-100000;
			Vector<Double> v = new Vector<Double>();
			v.add(offsetX+offset.get(0));
			v.add(offsetY+offset.get(1));
			octaveOffsets[i] = v;
		}
		
		if(scale  <= 0) {
			scale =  0.001;
		}
		double halfWidth = mapWidth/2.0;
		double halfHeight = mapHeight/2.0;
		double maxNoiseHeight =  -3.402823E38;
		double minNoiseHeight =  3.402823E38;
		for(int y = 0; y < mapHeight; y++) {
			for(int x = 0; x < mapWidth; x++) {
				
				double amplitude = 1;
				double frequency = 1;
				double noiseHeight = 0;
				for(int i = 0 ; i < octaves; i++) {
					double sampleX= (x - halfWidth) / scale * frequency + octaveOffsets[i].get(0);
					double sampleY= (y - halfHeight) / scale * frequency + octaveOffsets[i].get(1);
					double perlinNoise = drawer.noise((float)sampleX, (float)sampleY)*2-1;
					noiseHeight += perlinNoise*amplitude;
					amplitude *= persistance;
					frequency *= lacunarity;
				}
				if(noiseHeight > maxNoiseHeight) {
					maxNoiseHeight = noiseHeight;
				} else if(noiseHeight  < minNoiseHeight) {
					minNoiseHeight  = noiseHeight;
				}
				noiseMap[x][y] = noiseHeight;
			}
		}
		for(int y = 0; y < mapHeight; y++) {
			for(int x = 0; x < mapWidth; x++) {
				noiseMap[x][y] = inverseLerp(minNoiseHeight, maxNoiseHeight, noiseMap[x][y]);
				
			}
		}
		return noiseMap;
	}
	
	private static double inverseLerp(double x, double y, double z) {
		double den = y-x;
		double num = z-x;
		return num/den;
	}
		
	
}
