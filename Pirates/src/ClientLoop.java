import java.util.ArrayList;

/**
 * A looping thread that updates data in the DrawingSurface class
 * 
 * @author Anantajit
 */
public class ClientLoop extends Thread {
	private DrawingSurface parent;
	private Client client;
	private Player player;
	private boolean alive = true;

	public ClientLoop(DrawingSurface parent, Client client, Player player) {
		this.parent = parent;
		this.client = client;
		this.player = player;
	}

	public void run() {
		boolean sendBullets = false;
		boolean sendDamage = false;
		while (alive) {
			if (Dock.pull && parent.getCurrentDock() != null) {
				// Ask for a dock
				client.writeObject(new Request(NetworkedDock.class, parent.getCurrentDock().getNet().getID()));
			} else if (Dock.push && parent.getCurrentDock() != null) {
				// Send dock
				for (int i = 0; i < 4; i++) {
					parent.getCurrentDock().getNet()
							.getPrices()[i] = parent.getCurrentDock().getTradeScreen().getParts()[i].getPrice();
				}

				client.writeObject(parent.getCurrentDock().getNet());
				Dock.push = false;
			} else {

				if (sendBullets) {
					ArrayList<BulletNet> networked = new ArrayList<>();
					for (Bullet bullet : parent.getPlayerBullets()) {
						networked.add(bullet.getNet());
					}
					client.writeObject(networked);
					sendBullets = false;
				} else if (sendDamage) {
					client.writeObject(parent.getDamagedEnemies());
					parent.resetDamagedEnemies();
					sendDamage = false;
				} else {
					Boat b = player.getBoat();
					client.writeObject(b);
					sendBullets = true;
					sendDamage = true;
				}
			}

			Object input = client.readObject();
			if (input instanceof ArrayList) {
				// TODO: Add code here
				parent.setOtherBullets((ArrayList<BulletNet>) input);
			} else if (input instanceof Boat[]) {
				parent.setBoats((Boat[]) input);
				// If we get a networked dock, we update it no matter what
			} else if (input instanceof NetworkedDock) {
				parent.getCurrentDock().setNet((NetworkedDock) input);
				Dock.pull = false;

				if (parent.getCurrentDock() != null) {
					// Send dock
					for (int i = 0; i < 4; i++) {
						Dock d = parent.getCurrentDock();
						TradeScreen t = d.getTradeScreen();
						TradePart p = t.getParts()[i];
						NetworkedDock nd = d.getNet();
						float price = nd.getPrices()[i];
						if (price > 0)
							p.setPrice(price);
					}
				}
			} else if (input instanceof Integer) {
				parent.setDamageTaken((Integer) input);
			} else {
				System.err.println(input.getClass() + " UNKNOWN ACTION");
			}
		}
		client.disconnect();
	}

	public void kill() {
		alive = false;
	}
}
