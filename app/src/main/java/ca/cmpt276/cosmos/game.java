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
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import ca.cmpt276.cosmos.models.Planet;
import ca.cmpt276.cosmos.models.Spaceship;

public class game extends AppCompatActivity {

    private Spaceship spaceship;
    private ConstraintLayout gameLayout;
    private final Handler handler = new Handler();
    private final Handler obstacleHandler = new Handler();
    private int r = 1;
    private List<Planet> planetList = new ArrayList<>();
    private int displayX = 1080;
    private int displayY = 1920;
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
                spaceship.incrementForwardVelocity(0.1);
                handler.postDelayed(thrust, 10);
            }
            else if (spaceship.getSpaceshipForwardVel() > 0){
                step(10);
                if (inGravity() == null) {
                    spaceship.incrementForwardVelocity(-0.1);
                }
                handler.postDelayed(thrust, 10);
            }
            else{
                handler.postDelayed(rotate, 10);
            }

        }
    };

    private final Runnable rotate = new Runnable() {
        @Override
        public void run() {
            if (!gameLayout.isPressed()){
                setSpaceshipRotation(r);
                handler.postDelayed(rotate,10);
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
        // I have no idea how this works
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_game);

        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);

        displayX = (int) display.widthPixels;
        displayY = (int) display.heightPixels;
        ImageView spaceshipIcon = findViewById(R.id.spaceship);
        Log.i("x", "" + displayX);
        Log.i("Y", "" + displayY);
        double spaceshipX = displayX/2 - 50;
        double spaceshipY = displayY - 300;
        Log.i("spaceship x", ""+ spaceshipX);
        Log.i("spaceship Y", ""+ spaceshipY);
        spaceship = new Spaceship(spaceshipX,spaceshipY);

        Spaceship spaceship = new Spaceship(spaceshipX,spaceshipY);
        setSpaceshipLocation(spaceship.getX(), spaceship.getY());
        gameLayout = findViewById(R.id.game);
        setGoal(displayX/2, 200);
        //setGoal(maxX/2, 200);
        handler.postDelayed(rotate,100);
        //obstacleHandler.post(handleObstacles);
        planetList.add(new Planet(400,499, 100,200));
        setupPlanet(planetList.get(0));
        setGameClick();
    }

    private void setGameClick() {

        gameLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

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

    private void setupPlanet(Planet p){
        ImageView planet = findViewById(R.id.planet);
        planet.setX((float) (p.getX() - p.getRadius()));
        planet.setY((float) (p.getY() - p.getRadius()));
        planet.getLayoutParams().height = (int) (2*p.getRadius());
        planet.getLayoutParams().width = (int) (2*p.getRadius());
    }

    private void setSpaceshipRotation(int r) {
        ImageView spaceshipIcon = findViewById(R.id.spaceship);
        spaceship.updateAngle(r);
        spaceshipIcon.setRotation((float) spaceship.getAngle());
    }

    private void handleObstaclePositions() {
        ImageView asteroidIcon = findViewById(R.id.asteroid);
        ImageView satelliteIcon = findViewById(R.id.satellite);
        double asteroidPhase = Math.toRadians(System.currentTimeMillis() / (3.6 * 6.283185307));
        asteroidIcon.setX((float) (displayX/2 - 100 + (displayX/3) * Math.sin(asteroidPhase)));
        asteroidIcon.setY((float) (displayY * 0.35));
        double satellitePhase = Math.toRadians(System.currentTimeMillis() / (2.1 * 6.283185307));
        satelliteIcon.setX((float) (displayX/2 - 100 + (displayX/3) * Math.sin(satellitePhase)));
        satelliteIcon.setY((float) (displayY * 0.6));
    }

    private void step(long stepTime) {
        double x = spaceship.getX();
        double y = spaceship.getY();
        double r = spaceship.getAngle();
        spaceship.setSpaceshipVelX(spaceship.getSpaceshipForwardVel() * Math.sin(Math.toRadians(r)));
        spaceship.setSpaceshipVelY(spaceship.getSpaceshipForwardVel() * -1 * Math.cos(Math.toRadians(r)));
        Planet p = inGravity();
        if (p != null){
            double angle = p.getAngle(spaceship.getX(), spaceship.getY());
            double gravity = p.getGravity();
            Log.i("Angle", "true " + angle);
            Log.i("Gravity Added", "true " + gravity);
            //Log.i("orig xVel:", "" + spaceship.getSpaceshipVelX());
            //Log.i("orig yVel:", "" + spaceship.getSpaceshipVelY());
            spaceship.addGravityAttraction(angle, gravity);
            if (!gameLayout.isPressed() && angle < spaceship.getAngle()){
                setSpaceshipRotation(1);
            }
            else if (!gameLayout.isPressed()){
                setSpaceshipRotation(-1);
            }
            //Log.i(" xVel:", "" + spaceship.getSpaceshipVelX());
            //Log.i(" yVel:", "" + spaceship.getSpaceshipVelY());
            Log.i(" x:", "" + spaceship.getX());
            Log.i(" y:", "" + spaceship.getY());
        }
        double newX = x + (spaceship.getSpaceshipVelX() * stepTime/10);
        double newY = y + (spaceship.getSpaceshipVelY() * stepTime/10);
        setSpaceshipLocation((int) newX, (int) newY);
        //double newR = Math.atan2(newY, newX) + 1.570796327;
        //setSpaceshipRotation((int) newR);
    }

    private Planet inGravity(){
        for (Planet p: planetList){
            double maxX = p.getX() + p.getRadius();
            double minX = p.getX() - p.getRadius();
            double maxY = p.getY() + p.getRadius();
            double minY = p.getY() - p.getRadius();
            if (minX <= spaceship.getX() && spaceship.getX() <= maxX){
                if (minY <= spaceship.getY() && spaceship.getY() <= maxY){
                    return p;
                }
            }
        }
        return null;
    }

}