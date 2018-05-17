import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class acts as a "hub" through which all other servers connect to each
 * other
 * 
 * @author Anantajit
 */
public class CentralServer {
	public static final int MAX_PLAYERS = 50;

	private ArrayList<SubServer> users;
	public volatile Boat[] boats;
	private int userID;
	private String[][] blocks;

	public CentralServer(int port) throws IOException {
		users = new ArrayList<>();
		boats = new Boat[MAX_PLAYERS];
		Socket socket = null;

		// MAP GENERATOR(creates a new random map and puts into a text file)
		MapGenerator mg = new MapGenerator();
		mg.GenerateMap(new DrawingSurface());

		// READS BLOCK FROM TEXTFILE AND ADJUSTS SIZE OF BLOCKS
		TextReader reader = new TextReader("output.txt");
		blocks = reader.get2DArray();

		ServerSocket serverSocket = new ServerSocket(port);

		while (true) {
			if (userID < MAX_PLAYERS) {
				socket = serverSocket.accept();
				SubServer temp = new SubServer(socket, this, userID++);
				users.add(temp);
				temp.start();
			}
		}
	}

	public static void main(String[] args) throws IOException {
		CentralServer server = new CentralServer(4444);
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
}
