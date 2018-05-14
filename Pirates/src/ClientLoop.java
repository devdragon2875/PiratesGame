/**
 * A looping thread that updates data in the DrawingSurface class
 * @author Anantajit
 */
public class ClientLoop extends Thread {
    private DrawingSurface parent;
    private Client client;
    private Player player;

    public ClientLoop(DrawingSurface parent, Client client, Player player) {
        this.parent = parent;
        this.client = client;
        this.player = player;
    }

    public void run() {
        while (true) {
            Boat b = player.getBoat();
            client.writeBoat(b);
            parent.setBoats(client.readAllBoats());
        }
    }
}
