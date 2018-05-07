import java.io.Serializable;

public class Boat implements Serializable {
    private float x;
    private float y;
    private float angle;

    public Boat(float x, float y, float angle) {

        this.x = x;
        this.y = y;
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
}
