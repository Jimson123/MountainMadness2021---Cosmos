package ca.cmpt276.cosmos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import ca.cmpt276.cosmos.models.Spaceship;

public class game extends AppCompatActivity {

    private Spaceship spaceship;
    private ConstraintLayout gameLayout;
    private final Handler handler = new Handler();
    private final Handler obstacleHandler = new Handler();
    private int r = 1;
    private long currentTime;
    private double newX;
    private double newY;
    private boolean touching;

    public static Intent launchIntent(Context context) {
        Intent intent = new Intent(context, game.class);
        return intent;
    }

    private final Runnable thrust = new Runnable() {
        @Override
        public void run() {
            //Log.i("thrust", "moving");
            if (gameLayout.isPressed()) {
                //Log.i("is pressed:", "true");
                //long timepassed = System.currentTimeMillis() - currentTime;
                step(10);
                //Log.i("time: ", "" + timepassed);
                spaceship.incrementForwardVelocity(1);
                handler.postDelayed(thrust, 10);
            }
            else if (spaceship.getSpaceshipForwardVel() > 0){
                spaceship.incrementForwardVelocity(-1);
                handler.postDelayed(thrust, 10);
            }

        }
    };

    private final Runnable rotate = new Runnable() {
        @Override
        public void run() {
            if (!gameLayout.isPressed()){
                setSpaceshipRotation(r);
                handler.postDelayed(rotate,100);
            }
        }
    };

    private final Runnable handleObstacles = new Runnable() {
        @Override
        public void run() {
            handleObstaclePositions();
            obstacleHandler.postDelayed(handleObstacles,30);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);

        int maxX = (int) display.widthPixels;
        int maxY = (int) display.heightPixels;
        ImageView spaceshipIcon = findViewById(R.id.spaceship);
        Log.i("x", "" + maxX);
        Log.i("Y", "" + maxY);
        double spaceshipX = maxX/2 - 50;
        double spaceshipY = maxY - 300;
        Log.i("spaceship x", ""+ spaceshipX);
        Log.i("spaceship Y", ""+ spaceshipY);
        spaceship = new Spaceship(spaceshipX,spaceshipY);

        Spaceship spaceship = new Spaceship(spaceshipX,spaceshipY);
        setSpaceshipLocation(spaceship.getX(), spaceship.getY());
        gameLayout = findViewById(R.id.game);
        setGoal(maxX/2, 200);
        handler.postDelayed(rotate,100);
        obstacleHandler.post(handleObstacles);
        setGameClick();
    }

    private void setGameClick() {

        gameLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                currentTime = System.currentTimeMillis();
                handler.post(thrust);

                return false;
            }
        });
    }

    private void setSpaceshipLocation(double x, double y) {
        ImageView spaceshipIcon = findViewById(R.id.spaceship);
        spaceshipIcon.setX((float) x);
        spaceshipIcon.setY((float) y);
        spaceship.setX(x);
        spaceship.setY(y);
    }

    private void setGoal(double x, double y){
        ImageView goal = findViewById(R.id.goal);
        goal.setX((float) x);
        goal.setY((float) y);
    }

    private void setSpaceshipRotation(int r) {
        ImageView spaceshipIcon = findViewById(R.id.spaceship);
        spaceship.updateAngle(r);
        spaceshipIcon.setRotation((float) spaceship.getAngle());
    }

    private void handleObstaclePositions() {
        ImageView asteroidIcon = findViewById(R.id.asteroid);
        ImageView satelliteIcon = findViewById(R.id.satellite);
        asteroidIcon.setX((float) (150 + 450 * Math.sin(Math.toRadians(1 * (System.currentTimeMillis() % 12000)))));
        asteroidIcon.setY(275);
        satelliteIcon.setX((float) (150 + 450 * Math.sin(Math.toRadians(1 * (System.currentTimeMillis() % 7000)))));
        satelliteIcon.setY(425);
    }

    private void step(long stepTime) {
        double x = spaceship.getX();
        double y = spaceship.getY();
        double r = spaceship.getAngle();
        spaceship.setSpaceshipVelX(spaceship.getSpaceshipForwardVel() * Math.sin(Math.toRadians(r)));
        spaceship.setSpaceshipVelY(spaceship.getSpaceshipForwardVel() * -1 * Math.cos(Math.toRadians(r)));
        double newX = x + (spaceship.getSpaceshipVelX() * stepTime/10);
        double newY = y + (spaceship.getSpaceshipVelY() * stepTime/10);
        setSpaceshipLocation((int) newX, (int) newY);
        //double newR = Math.atan2(newY, newX) + 1.570796327;
        //setSpaceshipRotation((int) newR);
    }

}