package ca.cmpt276.cosmos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ca.cmpt276.cosmos.models.Asteroid;
import ca.cmpt276.cosmos.models.Planet;
import ca.cmpt276.cosmos.models.Spaceship;

public class game extends AppCompatActivity {

    private Spaceship spaceship;
    private List<Asteroid> asteroidList = new ArrayList<>();
    private List<Planet> planetList = new ArrayList<>();
    private ConstraintLayout gameLayout;
    private final Handler handler = new Handler();
    private int r = 1;
    private int touchCounter = 0;
    private Planet goal;
    private final Handler asteroidHandler = new Handler();
    private final Handler explodeHandler = new Handler();
    private int displayX;
    private int displayY;
    private boolean alive = true;
    private int difficulty;
    private static final String EXTRA_DIFFICULTY = "extra-difficulty";

    public static Intent launchIntent(Context context, int difficulty) {
        Intent intent = new Intent(context, game.class);
        intent.putExtra(EXTRA_DIFFICULTY, difficulty);
        return intent;
    }

    private final Runnable thrust = new Runnable() {
        @Override
        public void run() {
            if (alive) {
                if (gameLayout.isPressed()) {
                    spaceship.incrementForwardVelocity(0.33);
                    step(33);
                    handler.postDelayed(thrust, 33);
                }
                else if (spaceship.getSpaceshipForwardVel() == 0){
                    handler.postDelayed(rotate, 33);
                }
                else{
                    if (spaceship.getSpaceshipForwardVel() > 0) {
                        if (inGravity() == null) {
                            spaceship.incrementForwardVelocity(-0.33);
                        }
                    } else if (spaceship.getSpaceshipForwardVel() < 0){
                        spaceship.setSpaceshipForwardVel(0);
                    }
                    step(33);
                    handler.postDelayed(thrust, 33);
                }

            }
            else{
                handler.postDelayed(thrust, 33);
            }
        }
    };

    private final Runnable rotate = new Runnable() {
        @Override
        public void run() {
            if (!gameLayout.isPressed()){
                setSpaceshipRotation(2);
                handler.postDelayed(rotate,33);
            }
        }
    };

    private final Runnable handleAsteroids = new Runnable() {
        @Override
        public void run() {
            handleAsteroidPositions();
            asteroidHandler.postDelayed(handleAsteroids,33);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_game);

        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);

        displayX = (int) display.widthPixels;
        displayY = (int) display.heightPixels;

        Intent intent = getIntent();
        difficulty = intent.getIntExtra(EXTRA_DIFFICULTY, 0);

        double spaceshipX = displayX/2 - 50;
        double spaceshipY = displayY - 300;

        spaceship = new Spaceship(spaceshipX,spaceshipY);
        Spaceship spaceship = new Spaceship(spaceshipX,spaceshipY);

        goal = new Planet(displayX/2, 200, 100, 100);
        setGoal(goal);
        setSpaceshipLocation(spaceship.getX(), spaceship.getY());
        gameLayout = findViewById(R.id.game);

        handler.post(rotate);

        if (difficulty > 0) {
            planetList.add(new Planet(400,499, 100,100));
            setupPlanet(planetList.get(0));
        }
        else{
            ConstraintLayout planetLayout = findViewById(R.id.planetLayout);
            planetLayout.setVisibility(View.INVISIBLE);
        }

        if (difficulty > 1){
            asteroidList.add(new Asteroid(1,1,75));
            setupAsteroid(asteroidList.get(0), 0);
        }
        else{
            ImageView asteroid = findViewById(R.id.asteroid);
            asteroid.setVisibility(View.INVISIBLE);
        }
        if (difficulty > 2){
            asteroidList.add(new Asteroid(2,2,75));
            setupAsteroid(asteroidList.get(1), 1);
        }
        else{
            ImageView satellite = findViewById(R.id.satellite);
            satellite.setVisibility(View.INVISIBLE);
        }
        asteroidHandler.post(handleAsteroids);

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
        gameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                touchCounter++;
                updateScore();
            }
        });
    }

    private void updateScore() {
        TextView score = findViewById(R.id.score);
        score.setText("Score: "+ touchCounter);
    }

    private void setSpaceshipLocation(double x, double y) {
        ImageView spaceshipIcon = findViewById(R.id.spaceship);
        spaceshipIcon.setX((float) x);
        spaceshipIcon.setY((float) y);
        spaceship.setX(x);
        spaceship.setY(y);
        reachedGoal();
    }

    private void reachedGoal() {

        if (distanceBetween(spaceship.getX(), spaceship.getY(), goal.getX(),goal.getY()) <= goal.getRadius()){
                Log.i("Reached Goal", "true");
                handler.removeCallbacks(thrust);
                explodeHandler.removeCallbacks(unExplode);
                asteroidHandler.removeCallbacks(handleAsteroids);
                spaceship.setSpaceshipForwardVel(0);
                alive = false;
                //handler.removeCallbacks(rotate);
                //obstacleHandler.removeCallbacks(handleObstacles);
                Intent intent = level_complete.launchIntent(game.this, touchCounter, difficulty);
                finish();
                startActivity(intent);

        }
    }

    private void setGoal(Planet p){
        ImageView goal = findViewById(R.id.goal);
        goal.setX((float) p.getX());
        goal.setY((float) p.getY());
        goal.getLayoutParams().height = (int) (2*p.getRadius());
        goal.getLayoutParams().width = (int) (2*p.getRadius());

    }

    private void setupAsteroid(Asteroid a, int i){
        ImageView asteroidIcon = null;
        if (i == 0) {
            asteroidIcon = findViewById(R.id.asteroid);
        } else if (i == 1) {
            asteroidIcon = findViewById(R.id.satellite);
        }
        if (asteroidIcon != null) {
            asteroidIcon.setX((float) (a.getX() - 75));
            asteroidIcon.setY((float) (a.getY() - 75));
            asteroidIcon.getLayoutParams().height = 150;
            asteroidIcon.getLayoutParams().width = 150;
        }
    }

    private void setupPlanet(Planet p){
        ConstraintLayout planetLayout = findViewById(R.id.planetLayout);
        ImageView planet = findViewById(R.id.planet);
        ImageView gravity = findViewById(R.id.gravity1);

        gravity.getLayoutParams().height = (int) (4*p.getRadius());
        gravity.getLayoutParams().width = (int) (4*p.getRadius());
        planet.getLayoutParams().height = (int) (2*p.getRadius());
        planet.getLayoutParams().width = (int) (2*p.getRadius());
        planetLayout.getLayoutParams().height = (int) (4*p.getRadius());
        planetLayout.getLayoutParams().width = (int) (4*p.getRadius());
        planetLayout.setX((float) p.getX());
        planetLayout.setY((float) p.getY());

    }

    private void setSpaceshipRotation(int r) {
        ImageView spaceshipIcon = findViewById(R.id.spaceship);
        spaceship.updateAngle(r);
        spaceshipIcon.setRotation((float) spaceship.getAngle());
    }

    private void handleAsteroidPositions() {
        for (int i = 0; i < asteroidList.size(); i++){
            Asteroid a = asteroidList.get(i);
            ImageView asteroidIcon = null;
            if (i == 0){
                double asteroidPhase = Math.toRadians(System.currentTimeMillis() / (3.6 * 6.283185307));
                a.setX(displayX/2 - 100 + (displayX/3) * Math.sin(asteroidPhase));
                a.setY(displayY * 0.35);
                asteroidIcon = findViewById(R.id.asteroid);
            }
            else if (i == 1){
                double asteroidPhase = Math.toRadians(System.currentTimeMillis() / (2.1 * 6.283185307));
                a.setX(displayX/2 - 100 + (displayX/3) * Math.sin(asteroidPhase));
                a.setY(displayY * 0.6);
                asteroidIcon = findViewById(R.id.satellite);
            }
            asteroidIcon.setX((float) a.getX());
            asteroidIcon.setY((float) a.getY());
        }
    }

    private final Runnable unExplode = new Runnable() {
        @Override
        public void run() {
            ImageView spaceshipIcon = findViewById(R.id.spaceship);
            spaceship.setX(displayX/2 - 50);
            spaceship.setY(displayY - 300);
            spaceship.setSpaceshipForwardVel(0);
            spaceship.setSpaceshipVelX(0);
            spaceship.setSpaceshipVelY(0);
            spaceship.setAngle(0);
            spaceshipIcon.setImageResource(R.drawable.rocket);
            spaceshipIcon.setX((float) spaceship.getX());
            spaceshipIcon.setY((float) spaceship.getY());
            spaceshipIcon.setRotation((float) spaceship.getAngle());
            alive = true;
        }
    };

    private void explode() {
        alive = false;
        spaceship.setSpaceshipForwardVel(0);
        spaceship.setSpaceshipVelX(0);
        spaceship.setSpaceshipVelY(0);
        ImageView spaceshipIcon = findViewById(R.id.spaceship);
        spaceshipIcon.setImageResource(R.drawable.explosion);
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.explosion_sound);
        mp.start();
        explodeHandler.postDelayed(unExplode,1000);
    }

    private double distanceBetween(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    private boolean hitDetect() {
        double x1 = spaceship.getX();
        double y1 = spaceship.getY();
        if (x1 < 0 || x1 > displayX) {
            return true;
        } else if (y1 < 0 || y1 > displayY) {
            return true;
        }
        double x2;
        double y2;
        for (Asteroid a: asteroidList){
            x2 = a.getX();
            y2 = a.getY();
            // assume every asteroid has radius 75 right now
            if (distanceBetween(x1, y1, x2, y2) < 75){
                return true;
            }
        }
        for (Planet p: planetList){
            x2 = p.getX();
            y2 = p.getY();
            if (distanceBetween(x1, y1, x2, y2) < p.getRadius()){
                return true;
            }
        }
        return false;
    }

    private void step(long stepTime) {
        double x = spaceship.getX();
        double y = spaceship.getY();
        double r = spaceship.getAngle();
        spaceship.setSpaceshipVelX(spaceship.getSpaceshipForwardVel() * Math.sin(Math.toRadians(r)));
        spaceship.setSpaceshipVelY(spaceship.getSpaceshipForwardVel() * -1 * Math.cos(Math.toRadians(r)));
        Planet p = inGravity();
        if (p != null) {
            double angle = p.getAngle(spaceship.getX(), spaceship.getY());
            double gravity = p.getGravity(spaceship.getX(), spaceship.getY());
            Log.i("Angle", "true " + angle);
            Log.i("Gravity Added", "true " + gravity);
            Log.i("orig xVel:", "" + spaceship.getSpaceshipVelX());
            Log.i("orig yVel:", "" + spaceship.getSpaceshipVelY());
            spaceship.addGravityAttraction(angle, gravity);
//            if (!gameLayout.isPressed() && angle < spaceship.getAngle()) {
//                setSpaceshipRotation(2);
//            } else if (!gameLayout.isPressed()) {
//                setSpaceshipRotation(-2);
//            }
            //Log.i(" xVel:", "" + spaceship.getSpaceshipVelX());
            //Log.i(" yVel:", "" + spaceship.getSpaceshipVelY());
//            if (!gameLayout.isPressed() && angle < spaceship.getAngle()){
//                setSpaceshipRotation(1);
//            }
//            else if (!gameLayout.isPressed()){
//                setSpaceshipRotation(-1);
//            }
            Log.i("xVel:", "" + spaceship.getSpaceshipVelX());
            Log.i("yVel:", "" + spaceship.getSpaceshipVelY());
            Log.i(" x:", "" + spaceship.getX());
            Log.i(" y:", "" + spaceship.getY());
        }
        double newX = x + (spaceship.getSpaceshipVelX() * stepTime/10);
        double newY = y + (spaceship.getSpaceshipVelY() * stepTime/10);
        setSpaceshipLocation((int) newX, (int) newY);

        boolean collision = hitDetect();
        if (collision) {
            explode();
        }
    }

    private Planet inGravity(){
        double x1 = spaceship.getX();
        double y1 = spaceship.getY();
        for (Planet p: planetList){

            if (distanceBetween(x1, y1, p.getX(), p.getY()) < 2 * p.getRadius()) {
                return p;
            }

        }
        return null;
    }

}