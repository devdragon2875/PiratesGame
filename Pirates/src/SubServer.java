import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SubServer extends Thread {

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
                outObject.writeObject(centralServer.getBoats());
                outObject.reset();
            } catch (IOException e) {
                break;
            } catch (ClassNotFoundException e) {
                break;
            }
        }
    }
}
