package sanjit.spacefighter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by sanjit on 12/2/17.
 */

public class Enemy {
    private Bitmap bitmap;

    private int x;
    private int y;
    private int speed = 1;

    private int maxX;
    private int maxY;
    private int minX;
    private int minY;

    private Rect detectCollision;

    public Enemy(Context context, int screenX, int screenY) {
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy);

        maxX = screenX;
        maxY = screenY;
        minX = 0;
        minY = 0;

        Random generator = new Random();
        speed = generator.nextInt(6) + 10;
        x = generator.nextInt(maxX) - bitmap.getHeight();
        y = minY;

        detectCollision = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }

    public void update(int playerSpeed) {
        y += playerSpeed;
        y += speed;

        if (y > maxY + bitmap.getHeight()) {
            Random generator = new Random();
            speed = generator.nextInt(6) + 10;
            x = generator.nextInt(maxX) - bitmap.getHeight();
            if (x < minX)
                x = minX;
            y = minY;
        }

        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();
    }

    public Rect getDetectCollision() {
        return detectCollision;
    }

    public Bitmap getBitmap() {
        return bitmap;
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

    public int getSpeed() {
        return speed;
    }
}
