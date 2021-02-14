package ca.cmpt276.cosmos;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ca.cmpt276.cosmos.classes.Book;
import ca.cmpt276.cosmos.classes.Camera;
import ca.cmpt276.cosmos.classes.Level;
import ca.cmpt276.cosmos.models.Asteroid;
import ca.cmpt276.cosmos.models.Planet;
import ca.cmpt276.cosmos.models.Spaceship;

public class game extends AppCompatActivity {

    private Spaceship spaceship = new Spaceship(0.0, 0.0, 0.0, 5.0,0.0,0.0,0.0);
    private Planet goal;
    private List<Asteroid> asteroidList = new ArrayList<>();
    private List<Planet> planetList = new ArrayList<>();
    private List<ImageView> asteroidIconList = new ArrayList<>();
    private List<ConstraintLayout> planetLayoutList = new ArrayList<>();
    private List<ImageView> planetIconList = new ArrayList<>();
    private List<ImageView> gravityIconList = new ArrayList<>();
    private ConstraintLayout gameLayout;
    private final Handler handler = new Handler();
    private int r = 1;
    private int touchCounter = 0;
    private final Handler asteroidHandler = new Handler();
    private final Handler explodeHandler = new Handler();
    private int displayX;
    private int displayY;
    private boolean alive = true;
    //private int difficulty;
    private int stage = 1; // "level" was already used for the Level class
    private static final String EXTRA_DIFFICULTY = "extra-difficulty";

    /*
    public static Intent launchIntent(Context context, int difficulty) {
        Intent intent = new Intent(context, game.class);
        intent.putExtra(EXTRA_DIFFICULTY, difficulty);
        return intent;
    }
     */
    public static Intent launchIntent(Context context, int stage) {
        Intent intent = new Intent(context, game.class);
        intent.putExtra(EXTRA_DIFFICULTY, stage);
        return intent;
    }

    GsonBuilder builder = new GsonBuilder();
    //builder.setPrettyPrinting(); // setPrettyPrinting does not exist for some reason
    Gson gson = builder.create();
    Book book = new Book(); // insane workaround
    String levelString;
    private void getLevelString(int levelNumber) {
        /*
        //File levelFile = new File("level" + level + ".json");
        //levelString = Files.readString("level" + levelNumber + ".json", StandardCharsets.US_ASCII); // readString does not exist for some reason
        try {
            levelString = "";
            //System.out.println(Environment.getDataDirectory().getAbsolutePath());
            //for (String s: Environment.getDataDirectory().list()) {
            //    System.out.println(s);
            //}
            R.levels.level1
            File levelFile = new File("level" + levelNumber);
            Scanner levelFileReader = new Scanner(levelFile);
            while (levelFileReader.hasNextLine()) {
                levelString += levelFileReader.nextLine(); // android studio gets mad when strings are joined together in loops but I'm lazy
            }
            levelFileReader.close();
        } catch (FileNotFoundException e) { // I don't know what this does. I copied it with the rest of the file code.
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        */
        // I can't find the file.
        levelString = book.getPage(levelNumber - 1); // note that it starts on page 0
    }
    Level level = null;
    private void loadLevel(int levelNumber) {
        getLevelString(levelNumber);
        level = gson.fromJson(levelString, Level.class);
    }
    double[] bounds;

    Camera camera = new Camera();
    private void setSpaceshipLocation(double x, double y) {
        ImageView spaceshipIcon = findViewById(R.id.spaceship);
        spaceship.setX(x);
        spaceship.setY(y);
        int[] pixels = camera.positionToPixels(x, y, 2 * spaceship.getRadius(), 2 * spaceship.getRadius());
        spaceshipIcon.setX(pixels[0]);
        spaceshipIcon.setY(pixels[1]);
        if (goal != null) {
            reachedGoal();
        }
    }
    private void setSpaceshipRotation(double rotation) {
        ImageView spaceshipIcon = findViewById(R.id.spaceship);
        spaceship.setRotation(rotation);
        spaceshipIcon.setRotation((float) rotation);
    }
    /*
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
    private void setupPlanet(Planet p) {
        ConstraintLayout planetLayout = findViewById(R.id.planetLayout);
        ImageView planet = findViewById(R.id.planet);
        ImageView gravity = findViewById(R.id.gravity1);
        gravity.getLayoutParams().height = (int) (4 * p.getRadius());
        gravity.getLayoutParams().width = (int) (4 * p.getRadius());
        planet.getLayoutParams().height = (int) (2 * p.getRadius());
        planet.getLayoutParams().width = (int) (2 * p.getRadius());
        planetLayout.getLayoutParams().height = (int) (4 * p.getRadius());
        planetLayout.getLayoutParams().width = (int) (4 * p.getRadius());
        planetLayout.setX((float) p.getX());
        planetLayout.setY((float) p.getY());
    }
    */
    private void buildLevel() {
        double currentTime = System.currentTimeMillis() / 1000.0;
        bounds = level.getBounds(); // allegedly, memory in Java is automatically freed when no longer in use
        camera.setCamera(bounds[0], bounds[1], bounds[2], bounds[3], displayX, displayY);
        //System.out.println(bounds[0]);
        //System.out.println(bounds[1]);
        //System.out.println(bounds[2]);
        //System.out.println(bounds[3]);
        //System.out.println(camera.w);
        //System.out.println(camera.h);
        //System.out.println(camera.zoom);
        // clear asteroids and planets
        goal = null;
        asteroidList.clear();
        planetList.clear();
        // build spaceship
        ImageView spaceshipIcon = findViewById(R.id.spaceship);
        setSpaceshipLocation(level.spaceship.getX(), level.spaceship.getY());
        setSpaceshipRotation(level.spaceship.getRotation());
        spaceship.setRotation(level.spaceship.getRotation());
        spaceshipIcon.setRotation((float) spaceship.getRotation());
        spaceship.setRadius(level.spaceship.getRadius());
        spaceshipIcon.getLayoutParams().width = camera.dimensionToPixels(2 * spaceship.getRadius());
        spaceshipIcon.getLayoutParams().height = camera.dimensionToPixels(2 * spaceship.getRadius());
        spaceship.setSpeed(0.0);
        spaceship.setVelX(0.0);
        spaceship.setVelY(0.0);
        // hide asteroids
        for (ImageView asteroidIcon: asteroidIconList) {
            asteroidIcon.setX(50000);
        }
        // build asteroids
        Asteroid aTemplate;
        //int aId;
        int aIndex;
        ImageView asteroidIcon;
        int[] aPixels;
        for (int i = 0; i < level.asteroids.size(); i++) {
            aTemplate =  level.asteroids.get(i);
            asteroidList.add(new Asteroid(
                aTemplate.getName(),
                aTemplate.getOrbit(),
                aTemplate.getNewX(currentTime),
                aTemplate.getNewY(currentTime),
                aTemplate.getRadius()
            ));
            //aId = getResources().getIdentifier(aTemplate.getName(), "id", android.content.Context.getPackageName());
            //android.content.Context.getPackageName() cannot be used for some reason
            //aId = getResources().getIdentifier(aTemplate.getName(), "id", "ca.cmpt276.cosmos");
            //asteroidIcon = findViewById(aId);
            aIndex = Integer.parseInt(aTemplate.getName().substring(8)); // get the number in the name
            asteroidIcon = asteroidIconList.get(aIndex);
            aPixels = camera.positionToPixels(asteroidList.get(i).getX(), asteroidList.get(i).getX(), 2 * aTemplate.getRadius(), 2 * aTemplate.getRadius());
            asteroidIcon.setX(aPixels[0]);
            asteroidIcon.setY(aPixels[1]);
            asteroidIcon.getLayoutParams().width = camera.dimensionToPixels(2 * aTemplate.getRadius());
            asteroidIcon.getLayoutParams().height = camera.dimensionToPixels(2 * aTemplate.getRadius());
        }
        // hide planets
        for (ConstraintLayout planetLayout: planetLayoutList) {
            planetLayout.setX(50000);
        }
        // build planets
        Planet pTemplate;
        int pIndex;
        ConstraintLayout planetLayout;
        ImageView planetIcon;
        ImageView gravityIcon;
        int greaterRadius;
        int[] pPixels;
        for (int i = 0; i < level.planets.size(); i++) {
            pTemplate = level.planets.get(i);
            System.out.println(i);
            System.out.println(pTemplate.getName());
            planetList.add(new Planet(
                    pTemplate.getName(),
                    pTemplate.getX(),
                    pTemplate.getY(),
                    pTemplate.getRadius(),
                    pTemplate.getMass(),
                    pTemplate.getGravityRadius()
            ));
            pIndex = pTemplate.getIndex();
            if (pIndex == 0) { // this is the moon
                goal = planetList.get(i);
            }
            planetLayout = planetLayoutList.get(pIndex);
            planetIcon = planetIconList.get(pIndex);
            gravityIcon = gravityIconList.get(pIndex);
            greaterRadius = (int) Math.max(2 * pTemplate.getRadius(), 2 * pTemplate.getGravityRadius());
            pPixels = camera.positionToPixels(pTemplate.getX(), pTemplate.getY(), 2 * greaterRadius, 2 * greaterRadius);
            planetLayout.setX(pPixels[0]);
            planetLayout.setY(pPixels[1]);
            planetLayout.getLayoutParams().width = camera.dimensionToPixels(2 * greaterRadius);
            planetLayout.getLayoutParams().height = camera.dimensionToPixels(2 * greaterRadius);
            planetIcon.getLayoutParams().width = (int) camera.dimensionToPixels(2 * pTemplate.getRadius());
            planetIcon.getLayoutParams().height = (int) camera.dimensionToPixels(2 * pTemplate.getRadius());
            gravityIcon.getLayoutParams().width = (int) camera.dimensionToPixels(Math.max(2 * pTemplate.getGravityRadius(), 1.0));
            gravityIcon.getLayoutParams().height = (int) camera.dimensionToPixels(Math.max(2 * pTemplate.getGravityRadius(), 1.0));
        }
    }

    private final Runnable thrust = new Runnable() {
        @Override
        public void run() {
            if (alive) {
                if (gameLayout.isPressed()) {
                    spaceship.incrementSpeed(0.2);
                    step(33);
                    handler.postDelayed(thrust, 33);
                //}
                //else if (spaceship.getSpeed() == 0){
                //    handler.postDelayed(rotate, 33);
                } else {
                    if (spaceship.getSpeed() > 0) {
                        if (inGravity() == null) {
                            spaceship.incrementSpeed(-0.2);
                        }
                    } else if (spaceship.getSpeed() < 0){
                        spaceship.setSpeed(0);
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
            if (!gameLayout.isPressed() && (spaceship.getSpeed() <= 0)){
                setSpaceshipRotation(spaceship.getRotation() + 2);
            }
            handler.postDelayed(rotate,33);
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
        //difficulty = intent.getIntExtra(EXTRA_DIFFICULTY, 0);
        //stage = intent.getIntExtra(EXTRA_DIFFICULTY, 1); // stage keeps getting set to 0
        stage = 1;

        //double spaceshipX = displayX/2 - 50;
        //double spaceshipY = displayY - 300;

        //spaceship = new Spaceship(spaceshipX,spaceshipY);
        //Spaceship spaceship = new Spaceship(spaceshipX,spaceshipY);

        //goal = new Planet(displayX/2, 200, 100, 100);
        //setGoal(goal);
        //setSpaceshipLocation(spaceship.getX(), spaceship.getY());

        // this was the only place where it would fit
        asteroidIconList.add(findViewById(R.id.asteroid1));
        asteroidIconList.add(findViewById(R.id.asteroid2));
        asteroidIconList.add(findViewById(R.id.asteroid3));
        asteroidIconList.add(findViewById(R.id.asteroid4));
        asteroidIconList.add(findViewById(R.id.asteroid5));
        asteroidIconList.add(findViewById(R.id.asteroid6));
        asteroidIconList.add(findViewById(R.id.asteroid7));
        asteroidIconList.add(findViewById(R.id.asteroid8));

        planetLayoutList.add(findViewById(R.id.moonLayout));
        planetLayoutList.add(findViewById(R.id.earthLayout));
        planetLayoutList.add(findViewById(R.id.venusLayout));
        planetLayoutList.add(findViewById(R.id.marsLayout));
        planetLayoutList.add(findViewById(R.id.neptuneLayout));
        planetLayoutList.add(findViewById(R.id.redSunLayout));
        planetLayoutList.add(findViewById(R.id.sunLayout));
        planetLayoutList.add(findViewById(R.id.whiteSunLayout));
        planetLayoutList.add(findViewById(R.id.blackHoleLayout));

        planetIconList.add(findViewById(R.id.moonIcon));
        planetIconList.add(findViewById(R.id.earthIcon));
        planetIconList.add(findViewById(R.id.venusIcon));
        planetIconList.add(findViewById(R.id.marsIcon));
        planetIconList.add(findViewById(R.id.neptuneIcon));
        planetIconList.add(findViewById(R.id.redSunIcon));
        planetIconList.add(findViewById(R.id.sunIcon));
        planetIconList.add(findViewById(R.id.whiteSunIcon));
        planetIconList.add(findViewById(R.id.blackHoleIcon));

        gravityIconList.add(findViewById(R.id.moonGravity));
        gravityIconList.add(findViewById(R.id.earthGravity));
        gravityIconList.add(findViewById(R.id.venusGravity));
        gravityIconList.add(findViewById(R.id.marsGravity));
        gravityIconList.add(findViewById(R.id.neptuneGravity));
        gravityIconList.add(findViewById(R.id.redSunGravity));
        gravityIconList.add(findViewById(R.id.sunGravity));
        gravityIconList.add(findViewById(R.id.whiteSunGravity));
        gravityIconList.add(findViewById(R.id.blackHoleGravity));

        gameLayout = findViewById(R.id.game);

        handler.post(rotate);

        // TODO: difficulty / levels
        /*
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
         */

        loadLevel(stage);
        buildLevel();

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

    private void reachedGoal() {

        if (distanceBetween(spaceship.getX(), spaceship.getY(), goal.getX(),goal.getY()) <= goal.getRadius()){
                Log.i("Reached Goal", "true");
                handler.removeCallbacks(thrust);
                explodeHandler.removeCallbacks(unExplode);
                asteroidHandler.removeCallbacks(handleAsteroids);
                spaceship.setSpeed(0);
                alive = false;
                //handler.removeCallbacks(rotate);
                //obstacleHandler.removeCallbacks(handleObstacles);
                //Intent intent = level_complete.launchIntent(game.this, touchCounter, difficulty);
            Intent intent = level_complete.launchIntent(game.this, touchCounter, stage);
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
        //TODO: asteroids
        /*
        for (int i = 0; i < asteroidList.size(); i++){
            Asteroid a = asteroidList.get(i);
            ImageView asteroidIcon = null;
            if (i == 0){
                double asteroidPhase = Math.toRadians(System.currentTimeMillis() / (3.6 * 6.283185307));
                a.setX(displayX/2 - 100 + (displayX/3) * Math.sin(asteroidPhase));
                a.setY(displayY * 0.35);
                asteroidIcon = findViewById(R.id.asteroid1);
            }
            else if (i == 1){
                double asteroidPhase = Math.toRadians(System.currentTimeMillis() / (2.1 * 6.283185307));
                a.setX(displayX/2 - 100 + (displayX/3) * Math.sin(asteroidPhase));
                a.setY(displayY * 0.6);
                asteroidIcon = findViewById(R.id.asteroid2);
            }
            asteroidIcon.setX((float) a.getX());
            asteroidIcon.setY((float) a.getY());
        }
        */
    }

    private final Runnable unExplode = new Runnable() {
        @Override
        public void run() {
            ImageView spaceshipIcon = findViewById(R.id.spaceship);
            setSpaceshipLocation(level.spaceship.getX(), level.spaceship.getY());
            spaceship.setSpeed(0);
            spaceship.setVelX(0);
            spaceship.setVelY(0);
            setSpaceshipRotation(level.spaceship.getRotation());
            spaceshipIcon.setImageResource(R.drawable.rocket);
            alive = true;
        }
    };

    private void explode() {
        alive = false;
        spaceship.setSpeed(0);
        spaceship.setVelX(0);
        spaceship.setVelY(0);
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
        if (x1 < bounds[3] - 10 || x1 > bounds[1] + 10) {
            return true;
        } else if (y1 < bounds[0] - 10 || y1 > bounds[2] + 10) {
            return true;
        }
        double x2;
        double y2;
        for (Asteroid a: asteroidList){
            x2 = a.getX();
            y2 = a.getY();
            if (distanceBetween(x1, y1, x2, y2) < a.getRadius()){
                return true;
            }
        }
        for (Planet p: planetList){
            if (p.getIndex() != 0) { // you don't blow up when you collide into the moon
                x2 = p.getX();
                y2 = p.getY();
                if (distanceBetween(x1, y1, x2, y2) < p.getRadius()) {
                    return true;
                }
            }

        }
        return false;
    }

    private void step(long stepTime) {
        double x = spaceship.getX();
        double y = spaceship.getY();
        double r = spaceship.getRotation();
        spaceship.setVelX(spaceship.getSpeed() * Math.sin(Math.toRadians(r)));
        spaceship.setVelY(spaceship.getSpeed() * -1 * Math.cos(Math.toRadians(r)));
        Planet p = inGravity();
        if (p != null) {
            double angle = p.getAngle(spaceship.getX(), spaceship.getY());
            double gravity = p.getGravity(spaceship.getX(), spaceship.getY());
            Log.i("Angle", "true " + angle);
            Log.i("Gravity Added", "true " + gravity);
            Log.i("orig xVel:", "" + spaceship.getSpaceshipVelX());
            Log.i("orig yVel:", "" + spaceship.getSpaceshipVelY());
            spaceship.addGravityAttraction(angle, gravity);
            if (!gameLayout.isPressed() && angle < spaceship.getAngle()) {
                setSpaceshipRotation(2);
            } else if (!gameLayout.isPressed()) {
                setSpaceshipRotation(-2);
            }
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
        double newX = x + (spaceship.getVelX() * (stepTime / 1000.0));
        double newY = y + (spaceship.getVelY() * (stepTime / 1000.0));
        setSpaceshipLocation(newX, newY);

        boolean collision = hitDetect();
        if (collision) {
            explode();
        }
    }

    private Planet inGravity(){
        Planet strongestP = null; // in overlapping gravity, the smaller one will be used
        double x1 = spaceship.getX();
        double y1 = spaceship.getY();
        for (Planet p: planetList){
            if (distanceBetween(x1, y1, p.getX(), p.getY()) < 2 * p.getRadius()) {
                if (strongestP == null) {
                    strongestP = p;
                } else if (p.getRadius() < strongestP.getRadius()) {
                    strongestP = p;
                }
            }

        }
        return strongestP;
    }

}