import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class CentralServer {
    public static final int MAX_PLAYERS = 8;

    private ArrayList<SubServer> users;
    private Boat[] boats;
    private int userID;

    public CentralServer(int port) throws IOException {
        users = new ArrayList<>();
        boats = new Boat[MAX_PLAYERS];
        Socket socket = null;

        ServerSocket serverSocket = new ServerSocket(port);

        while (true) {
            socket = serverSocket.accept();
            SubServer temp = new SubServer(socket, this, userID++);
            users.add(temp);
            temp.start();
        }
    }


    public static void main(String[] args) throws IOException {
        CentralServer server = new CentralServer(4444);
    }

    public Boat[] getBoats() {
        return boats;
    }

    public void setBoats(Boat boat, int index) {
        boats[index] = boat;
    }
}
