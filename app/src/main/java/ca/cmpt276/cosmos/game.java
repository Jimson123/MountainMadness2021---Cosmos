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
    private final Handler handler = new Handler();
    private double newX;
    private double newY;

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
        setSpaceshipLocation(spaceship.getX(), spaceship.getY());
        setGoal(maxX/2, 200);
        setGameClick();
    }

    private void setGameClick() {
        ConstraintLayout gameLayout = findViewById(R.id.game);
        gameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newX = spaceship.getVelocity() * sin(spaceship.getAngle());
                newY = spaceship.getVelocity() * cos(spaceship.getAngle());
                handler.post(thrust);
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


}