import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * A "mini-server" that handles communication on a separate thread from the
 * CentralServer. Each SubServer communicates with a single client via Peer to
 * Peer
 * 
 * @author Anantajit
 */
public class SubServer extends Thread {
	public static final boolean showSelfOnNetwork = true;

	private CentralServer centralServer;
	private int UID;

	private ObjectInputStream inObject;
	private ObjectOutputStream outObject;

	public SubServer(Socket clientSocket, CentralServer centralServer, int UID) throws IOException {

		Socket socket = clientSocket;
		this.centralServer = centralServer;
		this.UID = UID;

		try {
			inObject = new ObjectInputStream(socket.getInputStream());
			outObject = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	public void run() {
		try {
			outObject.writeObject(centralServer.getBlocks());
			outObject.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (true) {
			try {
				Boat b = (Boat) inObject.readObject();
				centralServer.setBoat(UID, b);
				Boat[] boats = centralServer.getBoats().clone();
				if (!showSelfOnNetwork)
					boats[UID] = null;
				outObject.writeObject(boats);
				outObject.reset();
			} catch (IOException | ClassNotFoundException e) {
				centralServer.removeUser(this);
				break;
			}
		}
	}

	public int getUID() {
		return UID;
	}

	public void setUID(int newUID) {
		UID = newUID;
	}
}
