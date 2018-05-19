/**
 * A looping thread that updates data in the DrawingSurface class
 * @author Anantajit
 */
public class ClientLoop extends Thread {
    private DrawingSurface parent;
    private Client client;
    private Player player;
    
    private Request ongoing;

    public ClientLoop(DrawingSurface parent, Client client, Player player) {
        this.parent = parent;
        this.client = client;
        this.player = player;
    }

    public void run() {
        while (true) {
        	if(ongoing == null) {
        		Boat b = player.getBoat();
        		client.writeObject(b);
        	} else {
        		if(ongoing.getClass().equals(NetworkedDock.class)) {
            		client.writeObject(parent.getCurrentDock().getNet());
        		}
        		ongoing = null;
        	}
            
            Object input = client.readObject();
            if(input instanceof Boat[])
            	parent.setBoats((Boat[]) input);
            //If we get a networked dock, we update it no matter what
            else if(input instanceof NetworkedDock) {
            	parent.getCurrentDock().setNet((NetworkedDock) input);
            }
        }
    }
}
