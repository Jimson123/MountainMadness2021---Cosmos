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
        Spaceship spaceship = new Spaceship(spaceshipX,spaceshipY);
        setSpaceshipLocation(spaceship.getX(), spaceship.getY());
    }

    private void setSpaceshipLocation(int x, int y) {
        ImageView spaceshipIcon = findViewById(R.id.spaceship);
        spaceshipIcon.setX(x);
        spaceshipIcon.setY(y);
    }


}