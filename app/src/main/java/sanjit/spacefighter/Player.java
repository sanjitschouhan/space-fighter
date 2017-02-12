package sanjit.spacefighter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

/**
 * Created by sanjit on 12/2/17.
 */

public class Player {
    private final int GRAVITY = -10;
    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;
    private Bitmap bitmap;
    private int x;
    private int y;
    private int speed = 0;
    private int xSpeed = 0;
    private boolean boosting;
    private int maxY;
    private int minY;
    private int maxX;
    private int minX;

    private Rect detectCollision;

    public Player(Context context, int screenX, int screenY) {
        x = screenX / 2;
        y = screenY;
        speed = 1;
        xSpeed = 0;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);
        maxY = screenY - bitmap.getHeight();
        maxX = screenX - bitmap.getWidth();
        minY = 0;
        minX = 0;
        boosting = false;
        detectCollision = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }

    public Rect getDetectCollision() {
        return detectCollision;
    }

    public void setBoosting() {
        boosting = true;
    }

    public void stopBoosting() {
        boosting = false;
    }

    public void update() {

        if (boosting) {
            speed += 2;
        } else {
            speed -= 5;
        }

        if (speed < MIN_SPEED) {
            speed = MIN_SPEED;
        }
        if (speed > MAX_SPEED) {
            speed = MAX_SPEED;
        }

        y -= speed + GRAVITY;

        if (y < minY) {
            y = minY;
        }

        if (y > maxY) {
            y = maxY;
        }

        x -= xSpeed;

        if (x < minX) {
            x = minX;
        }
        if (x > maxX) {
            x = maxX;
        }

        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public void setXSpeed(int roll) {
        xSpeed = roll;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }
}
