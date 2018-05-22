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
	public static final int MAX_PLAYERS = 5;
	private int port;

	private ArrayList<SubServer> users;
	public volatile Boat[] boats;
	private int userID;
	private String[][] blocks;
	private volatile NetworkedDock[] docks;

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

		int dockCount = 0;
		// STATS
		for (String[] row : blocks) {
			for (String block : row) {
				dockCount++;
			}
		}
		docks = new NetworkedDock[dockCount];
		for (int i = 0; i < docks.length; i++) {
			docks[i] = new NetworkedDock(i);
			for (int i2 = 0; i2 < 4; i2++) {
				docks[i].getPrices()[i2] = -1;
			}
		}
	}

	public static void main(String[] args) throws IOException {
		CentralServer server = new CentralServer(4444);

		new ServerManager(server).start();

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

	public void setUserID() {
		boolean updated = false;

		for (int i = 0; i < boats.length; i++) {
			if (this.boats[i] == null) {
				this.userID = i;
				updated = true;
			}
		}

		if (!updated) {
			this.userID = CentralServer.MAX_PLAYERS + 1;
		}
	}

	public ArrayList<SubServer> getUsers() {
		return users;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public NetworkedDock[] getDocks() {
		return docks;
	}

	public void setDocks(NetworkedDock[] docks) {
		this.docks = docks;
	}

	public synchronized ArrayList<BulletNet> otherBullets(int UID) {
		ArrayList<BulletNet> out = new ArrayList<>();
		for (int i = 0; i < users.size(); i++) {
			if (i != UID && users.get(i) != null && users.get(i).getBullets() != null) {
				out.addAll(users.get(i).getBullets());
			}
		}
		return out;
	}

	public synchronized Integer getDamage(int UID) {
		int total = 0;
		for (int i = 0; i < users.size(); i++) {
			if (i != UID && users.get(i) != null && users.get(i).getDamagedEnemies() != null) {
				total += users.get(i).getDamagedEnemies()[UID];
				users.get(i).getDamagedEnemies()[UID] = 0;
			}
		}
		return total;
	}
}

class ServerManager extends Thread {
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
			e.printStackTrace();
		}

		while (true) {
			if (head.getUserID() < CentralServer.MAX_PLAYERS) {
				try {
					socket = serverSocket.accept();
				} catch (IOException e) {
					e.printStackTrace();
				}
				SubServer temp = null;
				try {
					temp = new SubServer(socket, head, head.getUserID());
				} catch (IOException e) {
					e.printStackTrace();
				}

				head.getUsers().add(temp);
				temp.start();
			} else {
				head.setUserID();
			}
		}
	}

}
