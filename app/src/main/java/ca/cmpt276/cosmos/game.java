package ca.cmpt276.cosmos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Space;

import ca.cmpt276.cosmos.models.Spaceship;

public class game extends AppCompatActivity {

    public static Intent launchIntent(Context context) {
        Intent intent = new Intent(context, game.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);

        int maxX = (int) display.widthPixels;
        int maxY = (int) display.heightPixels;
        ImageView spaceshipIcon = findViewById(R.id.spaceship);
        Log.i("x", ""+maxX);
        Log.i("Y", ""+ maxY);
        int spaceshipX = maxX/2 - 50;
        int spaceshipY = maxY - 300;
        Log.i("spaceship x", ""+ spaceshipX);
        Log.i("spaceship Y", ""+ spaceshipY);
        double spaceshipForwardVel = 0;
        double spaceshipVelX = 0;
        double spaceshipVelY = 0;
        Spaceship spaceship = new Spaceship(spaceshipX,spaceshipY);
        setSpaceshipLocation(spaceship.getX(), spaceship.getY());
    }

    private void setSpaceshipLocation(int x, int y) {
        ImageView spaceshipIcon = findViewById(R.id.spaceship);
        spaceshipIcon.setX(x);
        spaceshipIcon.setY(y);
    }

    private void setSpaceshipRotation(int r) {
        ImageView spaceshipIcon = findViewById(R.id.spaceship);
        spaceshipIcon.setRotation(r);
    }

    // TODO: consider whether it would be better to use polar coordinates (angle from planet, radius) instead of cartesian coordinates
    // as you can see, this is absolutely broken
    private void step(double stepTime) {
        ImageView spaceshipIcon = findViewById(R.id.spaceship);
        double x = spaceshipIcon.getX();
        double y = spaceshipIcon.getY();
        double r = spaceshipIcon.getRotation();
        spaceshipVelX = spaceshipForwardVel * Math.sin(Math.toRadians(r));
        spaceshipVelY = spaceshipForwardVel * Math.cos(Math.toRadians(r));
        double newX = x + (spaceshipVelX * stepTime);
        double newY = y + (spaceshipVelY * stepTime);
        setSpaceshipLocation((int) newX, (int) newY);
        double newR = Math.atan2(newY, newX) + 1.570796327;
        setSpaceshipRotation((int) newR);
        // TODO: adjust rotation based on where the planet actually is. the rocket currently only orbits (0, 0)
    }




}