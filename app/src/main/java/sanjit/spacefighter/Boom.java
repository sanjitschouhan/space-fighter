package sanjit.spacefighter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by sanjit on 12/2/17.
 */

public class Boom {
    private Bitmap bitmap;

    private int x;
    private int y;
    private int speed;

    public Boom(Context context) {
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.boom);
        x = -300;
        y = -300;
        speed = 0;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
