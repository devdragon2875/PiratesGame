import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
            System.out.println("SOCKET CONNECTED");
            outObject = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("OUTPUT STREAM CONNECTED");
            inObject = new ObjectInputStream(socket.getInputStream());
            System.out.println("INPUT STREAM CONNECTED");
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }

        System.out.println("CONNECTED TO SERVER");
        return 0;

    }

    public void disconnect() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Boat[] readAllBoats() {
        if (inObject == null)
            return null;
        try {
            return (Boat[]) inObject.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void writeBoat(Boat boat) {
        if (outObject != null) {
            try {
                outObject.writeObject(boat);
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

}
  