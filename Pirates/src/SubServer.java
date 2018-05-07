import java.io.*;
import java.net.Socket;

public class SubServer extends Thread {

    protected Socket socket;
    private CentralServer centralServer;
    private int UID;

    private ObjectInputStream inObject;
    private ObjectOutputStream outObject;

    public SubServer(Socket clientSocket, CentralServer centralServer, int UID) throws IOException {

        this.socket = clientSocket;
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
        while (true) {
            try {
                outObject.writeObject(centralServer.getBoats());
                centralServer.setBoats((Boat)inObject.readObject(), UID);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
