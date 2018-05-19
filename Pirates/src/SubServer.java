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
			boolean sent = false;
			try {
				Object input = inObject.readObject();
				if (input instanceof Boat)
					centralServer.setBoat(UID, (Boat) input);
				else if (input instanceof Request) {
					if (((Request) input).getType().equals(NetworkedDock.class)) {
						outObject.writeObject(centralServer.getDocks()[((Request) input).getID()]);
						sent = true;
						System.out.println("SENT CLIENT DOCK...");
					}
				}
				else if(input instanceof NetworkedDock) {
					centralServer.getDocks()[((NetworkedDock) input).getID()] = (NetworkedDock) input;
					System.out.println("GOT DOCK");
				}
				
				if (!sent) {
					Boat[] boats = centralServer.getBoats().clone();
					if (!showSelfOnNetwork)
						boats[UID] = null;
					outObject.writeObject(boats);
					sent = true;
				}
				outObject.reset();
			} catch (IOException | ClassNotFoundException e) {
				centralServer.removeUser(this);
				centralServer.setBoat(UID, null);
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
