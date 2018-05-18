import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import processing.core.PApplet;

public class ServerUI extends PApplet{
	private CentralServer server;
	private InetAddress serverStats;
	private String ip;
	
	public ServerUI(CentralServer server) {
		this.server = server;
		try {
			server.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			serverStats = InetAddress.getLocalHost();
			ip = serverStats.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public void settings() {
		size(1200, 800);
		smooth(0);
	}
	
	public void setup() {
		this.frameRate(10);
	}
	
	public void draw() {
		clear();
		background(135,206,250);
		stroke(0);
		textSize(100);	
		float boxWidth = this.textWidth(ip);
		fill(255);
		rect(width/2 - boxWidth/2 - 10, height/2 - 60, boxWidth + 20, 120, 20);
		fill(100);
		text(ip, width/2 - boxWidth/2, height/2 + 50);
	}
}
