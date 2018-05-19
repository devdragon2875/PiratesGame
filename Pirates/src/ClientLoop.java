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
        	if(Dock.pull && parent.getCurrentDock() != null) {
        		//Ask for a dock
        		client.writeObject(new Request(NetworkedDock.class, parent.getCurrentDock().getNet().getID()));
        		System.out.println("REQUESTING DOCK");
        	} else if(Dock.push && parent.getCurrentDock() != null) {
        		//Send dock
        		client.writeObject(parent.getCurrentDock().getNet());
        		Dock.push = false;
        		System.out.println("SENDING DOCK");
        	}
        	else{
        		Boat b = player.getBoat();
        		client.writeObject(b);
        	}
            
            Object input = client.readObject();
            if(input instanceof Request) {
            	//TODO: Add code here
            	
            } else if(input instanceof Boat[])
            	parent.setBoats((Boat[]) input);
            //If we get a networked dock, we update it no matter what
            else if(input instanceof NetworkedDock) {
            	parent.getCurrentDock().setNet((NetworkedDock) input);
            	System.out.println("RECEIVED DOCK");
            	Dock.pull = false;
            }
        }
    }
}
