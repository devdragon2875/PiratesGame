import java.awt.Dimension;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;

import processing.awt.PSurfaceAWT;
import processing.core.PApplet;

/**
 * This class acts as a "hub" through which all other servers connect to each
 * other
 * 
 * @author Anantajit
 */
public class CentralServer {
	public static final int MAX_PLAYERS = 50;
	private int port;
	
	
	private ArrayList<SubServer> users;
	public volatile Boat[] boats;
	private int userID;
	private String[][] blocks;

	public CentralServer(int port) throws IOException {
		this.setPort(port);
		users = new ArrayList<>();
		boats = new Boat[MAX_PLAYERS];

		// MAP GENERATOR(creates a new random map and puts into a text file)
		MapGenerator mg = new MapGenerator();
		mg.GenerateMap(new DrawingSurface());

		// READS BLOCK FROM TEXTFILE AND ADJUSTS SIZE OF BLOCKS
		TextReader reader = new TextReader("output.txt");
		blocks = reader.get2DArray();
	}
	
	public void start() throws IOException {
		
	}

	public static void main(String[] args) throws IOException {
		CentralServer server = new CentralServer(4444);
		System.out.println("SERVER INIT COMPLETE");
		
		new ServerManager(server).start();
		System.out.println("SERVER START COMPLETE");
		
		ServerUI drawing = new ServerUI(server);
		PApplet.runSketch(new String[] { "Pirates - Server" }, drawing);
		PSurfaceAWT surf = (PSurfaceAWT) drawing.getSurface();
		PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
		JFrame window = (JFrame) canvas.getFrame();

		window.setSize(1200, 830);
		window.setMinimumSize(new Dimension(400, 400));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		
		window.setVisible(true);
		canvas.requestFocus();
		System.out.println("SERVER UI RUNNING");
	}

	public synchronized Boat[] getBoats() {
		return boats;
	}

	public synchronized void setBoat(int index, Boat boat) {
		boats[index] = boat;
	}

	public synchronized void removeUser(SubServer user) {
		users.remove(user);
		boats[user.getUID()] = null;
	}

	public String[][] getBlocks() {
		return blocks;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	public ArrayList<SubServer> getUsers(){
		return users;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}

class ServerManager extends Thread{
	private CentralServer head;
	public ServerManager(CentralServer head) {
		this.head = head;
	}
	
	public void run() {
		Socket socket = null;

		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(head.getPort());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while (true) {
			if (head.getUserID() < CentralServer.MAX_PLAYERS) {
				try {
					socket = serverSocket.accept();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				SubServer temp = null;
				try {
					temp = new SubServer(socket, head, head.getUserID());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				head.setUserID(head.getUserID()+1);
				head.getUsers().add(temp);
				temp.start();
			}
		}
	}
}
