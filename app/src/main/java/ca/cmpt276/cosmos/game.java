package ca.cmpt276.cosmos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Space;

import ca.cmpt276.cosmos.models.Spaceship;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class game extends AppCompatActivity {

    private Spaceship spaceship;
    private ConstraintLayout gameLayout;
    private final Handler handler = new Handler();
    private int r = 1;
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
            double x = spaceship.getX();
            double y = spaceship.getY();
            if (x < newX){
                x += 10;
                spaceship.setX(x);
            }
            if (y < newY){
                y += 10;
                spaceship.setY(y);
            }
        }
    };

    private final Runnable rotate = new Runnable() {
        @Override
        public void run() {
            if (!gameLayout.isPressed()){
                setSpaceshipRotation(r);
            }
            handler.postDelayed(rotate,100);
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

        gameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                newX = spaceship.getVelocity() * sin(spaceship.getAngle());
//                newY = spaceship.getVelocity() * cos(spaceship.getAngle());
//                handler.post(thrust);
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

    // TODO: consider whether it would be better to use polar coordinates (angle from planet, radius) instead of cartesian coordinates
    private void step(double stepTime) {
        ImageView spaceshipIcon = findViewById(R.id.spaceship);
        double x = spaceshipIcon.getX();
        double y = spaceshipIcon.getY();
        double r = spaceshipIcon.getRotation();
        spaceship.setSpaceshipVelX(spaceship.getSpaceshipForwardVel() * Math.sin(Math.toRadians(r)));
        spaceship.setSpaceshipVelY(spaceship.getSpaceshipVelY() * Math.cos(Math.toRadians(r)));
        double newX = x + (spaceship.getSpaceshipVelX() * stepTime);
        double newY = y + (spaceship.getSpaceshipVelY() * stepTime);
        setSpaceshipLocation((int) newX, (int) newY);
        double newR = Math.atan2(newY, newX) + 1.570796327;
        setSpaceshipRotation((int) newR);
        // TODO: adjust rotation based on where the planet actually is. the rocket currently only orbits (0, 0)
    }




}