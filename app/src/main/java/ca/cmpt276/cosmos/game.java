package ca.cmpt276.cosmos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import ca.cmpt276.cosmos.models.Spaceship;

public class game extends AppCompatActivity {

    private Spaceship spaceship;
    private ConstraintLayout gameLayout;
    private final Handler handler = new Handler();
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
//            double x = spaceship.getX();
//            double y = spaceship.getY();
//            if (x < newX){
//                x += 10;
//                spaceship.setX(x);
//            }
//            if (y < newY){
//                y += 10;
//                spaceship.setY(y);
//            }
//        }
            Log.i("thrust", "moving");
            if (gameLayout.isPressed()) {
                Log.i("is pressed:", "true");
                long timepassed = System.currentTimeMillis() - currentTime;
                step(timepassed);
                Log.i("time: ", "" + timepassed);
                handler.postDelayed(thrust, 100);
            }
            else{
                Log.i("is pressed:", "false");
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
        setGameClick();
    }

    private void setGameClick() {

        gameLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

//                newX = spaceship.getVelocity() * sin(spaceship.getAngle());
//                newY = spaceship.getVelocity() * cos(spaceship.getAngle());
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

    private void step(long stepTime) {
        double x = spaceship.getX();
        double y = spaceship.getY();
        double r = spaceship.getAngle();
        spaceship.setSpaceshipVelX(spaceship.getSpaceshipForwardVel() * Math.sin(Math.toRadians(r)));
        spaceship.setSpaceshipVelY(spaceship.getSpaceshipForwardVel() * -1 *Math.cos(Math.toRadians(r)));
        double newX = x + (spaceship.getSpaceshipVelX() * stepTime/10);
        double newY = y + (spaceship.getSpaceshipVelY() * stepTime/10);
        setSpaceshipLocation((int) newX, (int) newY);
        //double newR = Math.atan2(newY, newX) + 1.570796327;
        //setSpaceshipRotation((int) newR);
    }

}