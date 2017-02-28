package sanjit.spacefighter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by sanjit on 12/2/17.
 */

public class GameView extends SurfaceView implements Runnable, SensorEventListener {

    volatile boolean playing;
    int nextFire;
    private Thread gameThread = null;
    private Player player;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private Enemy[] enemies;
    private int enemyCount = 3;
    private ArrayList<Star> stars = new ArrayList<>();
    private ArrayList<ShootFire> fires = new ArrayList<>();
    private Boom boom;

    private SensorManager sensorManager;
    private Sensor sensor;

    private int screenY;
    private int score = 0;

    public GameView(Context context, int screenX, int screenY, Object SystenSensorManager) {
        super(context);
        this.screenY = screenY;

        this.sensorManager = (SensorManager) SystenSensorManager;
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        player = new Player(context, screenX, screenY);

        surfaceHolder = getHolder();
        paint = new Paint();

        int starNums = 100;
        for (int i = 0; i < starNums; i++) {
            stars.add(new Star(screenX, screenY));
        }

        nextFire = 0;
        int fireNums = 100;
        for (int i = 0; i < fireNums; i++) {
            fires.add(new ShootFire(-500, -500));
        }

        enemies = new Enemy[enemyCount];
        for (int i = 0; i < enemyCount; i++) {
            enemies[i] = new Enemy(context, screenX, screenY);
        }

        boom = new Boom(context);

    }

    @Override
    public void run() {
        while (playing) {
            update();
            draw();
            control();
        }

    }

    private void update() {
        player.update();
        for (Star s : stars) {
            s.update(player.getSpeed());
        }

        for (int i = 0; i < enemyCount; i++) {
            enemies[i].update(player.getSpeed());

            for (ShootFire sf : fires) {
                sf.update(player.getSpeed());
                if (Rect.intersects(sf.getDetectCollision(), enemies[i].getDetectCollision())) {
                    boom.setX(enemies[i].getX());
                    boom.setY(enemies[i].getY());
                    boom.setSpeed(enemies[i].getSpeed());
                    enemies[i].setX(-enemies[i].getBitmap().getWidth() - 200);
                    score += player.getSpeed();
                }
            }
        }

        if (boom.getY() < screenY) {
            boom.setY(boom.getY() + boom.getSpeed() + player.getSpeed());
        }
    }

    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.BLACK);

            paint.setColor(Color.WHITE);
            for (Star s : stars) {
                paint.setStrokeWidth(s.getStarWidth());
                canvas.drawPoint(s.getX(), s.getY(), paint);

            }
            paint.setColor(Color.YELLOW);
            for (ShootFire sf : fires) {
                paint.setStrokeWidth(2);
                canvas.drawLine(sf.getX(), sf.getY(), sf.getX(), sf.getY() + 10, paint);
            }

            canvas.drawBitmap(
                    player.getBitmap(),
                    player.getX(),
                    player.getY(),
                    paint
            );

            for (int i = 0; i < enemyCount; i++) {
                canvas.drawBitmap(
                        enemies[i].getBitmap(),
                        enemies[i].getX(),
                        enemies[i].getY(),
                        paint
                );
            }

            canvas.drawBitmap(
                    boom.getBitmap(),
                    boom.getX(),
                    boom.getY(),
                    paint
            );

            paint.setTextSize(50);
            canvas.drawText("Score: " + score, 10, 60, paint);

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control() {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sensorManager.unregisterListener(this);
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                player.stopBoosting();
                break;
            case MotionEvent.ACTION_DOWN:
                player.setBoosting();
                break;
        }
        return true;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int roll = (int) event.values[2];
        player.setXSpeed(roll);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void shoot() {
        ShootFire shootFire = fires.get(nextFire);
        nextFire = (nextFire + 1) % 100;
        shootFire.setX(player.getX() + player.getBitmap().getWidth() / 4);
        shootFire.setY(player.getY());

        shootFire = fires.get(nextFire);
        nextFire = (nextFire + 1) % 100;
        shootFire.setX(player.getX() + player.getBitmap().getWidth() / 2);
        shootFire.setY(player.getY());

        shootFire = fires.get(nextFire);
        nextFire = (nextFire + 1) % 100;
        shootFire.setX(player.getX() + 3 * player.getBitmap().getWidth() / 4);
        shootFire.setY(player.getY());
    }
}
