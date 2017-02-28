package sanjit.spacefighter;

import android.graphics.Rect;

/**
 * Created by sanjit on 12/2/17.
 */

public class ShootFire {
    private int x;
    private int y;
    private int speed;
    private Rect detectCollision;

    public ShootFire(int x, int y) {
        this.x = x;
        this.y = y;
        speed = 10;
        detectCollision = new Rect(x - 1, y, x + 2, y + 10);
    }

    public void update(int playerSpeed) {
        if (y > -100) {
            y -= playerSpeed;
            y -= speed;
            detectCollision = new Rect(x - 1, y, x + 2, y + 10);
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Rect getDetectCollision() {
        return detectCollision;
    }
}
