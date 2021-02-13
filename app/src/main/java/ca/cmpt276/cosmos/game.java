package ca.cmpt276.cosmos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
        Spaceship spaceship = new Spaceship(0,0);
        setSpaceshipLocation(spaceship.getX(), spaceship.getY());
    }

    private void setSpaceshipLocation(int x, int y) {
        ImageView spaceshipIcon = findViewById(R.id.spaceship);
        spaceshipIcon.setX(x);
        spaceshipIcon.setY(y);
    }


}