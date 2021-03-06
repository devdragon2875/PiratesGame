import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

/**
 * The Client that lives in the main thread (everything but updating data)
 * 
 * @author Anantajit
 */
public class Client {
	private int port;
	private String ip;

	private ObjectInputStream inObject;
	private ObjectOutputStream outObject;

	private Socket socket;

	public Client(String ip, int port) {
		this.port = port;
		this.ip = ip;
	}

	public int connect() {
		String serverAddress = ip;
		socket = null;
		try {
			socket = new Socket(serverAddress, port);
			outObject = new ObjectOutputStream(socket.getOutputStream());
			inObject = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}

		return 0;

	}

	public void disconnect() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Object readObject() {
		if (inObject == null)
			return null;
		try {
			return inObject.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void writeObject(Serializable object) {
		if (outObject != null) {
			try {
				outObject.writeObject(object);
				outObject.reset();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String[][] readArray() {
		if (inObject == null)
			return null;
		try {
			return (String[][]) inObject.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
