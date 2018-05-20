import java.awt.Polygon;
import java.io.Serializable;

/**
 * A simplified version of the player class, with the things that NEED to be sent over the network
 * @author Anantajit
 */
public class Boat implements Serializable {
    private float x;
    private float y;
    private float v;
    private float angle;
    private Polygon hitbox;

    public Boat(float x, float y, float v, float angle) {
        this.x = x;
        this.y = y;
        this.v = v;
        this.angle = angle;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getAngle() {
        return angle;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    @Override
    public String toString() {
        return "X: " + x + ", + Y: " + y + "; ANGLE:" + angle;
    }

    public float getV() {
        return v;
    }

    public void setV(float v) {
        this.v = v;
    }

	public Polygon getHitbox() {
		return hitbox;
	}

	public void setHitbox(Polygon hitbox) {
		this.hitbox = hitbox;
	}
}
