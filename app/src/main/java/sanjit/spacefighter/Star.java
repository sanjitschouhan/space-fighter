package sanjit.spacefighter;

import java.util.Random;

/**
 * Created by sanjit on 12/2/17.
 */

public class Star {
    private int x;
    private int y;
    private int speed;

    private int maxX;
    private int maxY;
    private int minX;
    private int minY;

    public Star(int screenX, int screenY) {
        maxX = screenX;
        maxY = screenY;
        minX = 0;
        minY = 0;
        Random generator = new Random();
        speed = generator.nextInt(10);

        x = generator.nextInt(maxX);
        y = generator.nextInt(maxY);
    }

    public void update(int playerSpeed) {
        y += playerSpeed;
        y += speed;
        if (y > maxY) {
            y = minY;
            Random generator = new Random();
            x = generator.nextInt(maxX);
            speed = generator.nextInt(15);
        }
    }

    public float getStarWidth() {
        float minX = 1.0f;
        float maxX = 4.0f;
        Random rand = new Random();
        return (rand.nextFloat() * (maxX - minX)) + minX;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
