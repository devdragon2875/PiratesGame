import java.io.Serializable;

/**
 * A way for client to request objects from server
 * 
 * @author Anantajit
 *
 */
public class Request implements Serializable {
	private Class type;
	private int ID;

	public Request(Class type, int ID) {
		this.type = type;
		this.ID = ID;
	}

	public Class getType() {
		return type;
	}

	public int getID() {
		return ID;
	}
}
