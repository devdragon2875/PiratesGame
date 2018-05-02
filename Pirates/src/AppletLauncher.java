import java.awt.Dimension;

import javax.swing.JApplet;
import javax.swing.JFrame;

import processing.awt.PSurfaceAWT;
import processing.core.PApplet;

public class AppletLauncher extends JApplet { 

	/*
	public static void main(String[] args) {
		DrawingSurface drawing = new DrawingSurface();
		PApplet.runSketch(new String[] { "" }, drawing);
		PSurfaceAWT surf = (PSurfaceAWT) drawing.getSurface();
		PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
		//JApplet window = (JApplet) canvas.getFrame();
		JApplet window = canvas.get
		window.setSize(800, 830);
		window.setMinimumSize(new Dimension(400, 400));
		//window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//window.setResizable(false);
		
		//window.setVisible(true);
		canvas.requestFocus();
	}
	*/
	public void init() {
		DrawingSurface drawing = new DrawingSurface();
		PApplet.runSketch(new String[] { "" }, drawing);
		PSurfaceAWT surf = (PSurfaceAWT) drawing.getSurface();
		PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
		//JApplet window = (JApplet) canvas.getFrame();
		this.add(canvas);
		this.setSize(800, 830);
		this.setMinimumSize(new Dimension(400, 400));
		//window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//window.setResizable(false);
		
		//window.setVisible(true);
		this.setVisible(true);
		canvas.requestFocus();
	}

}