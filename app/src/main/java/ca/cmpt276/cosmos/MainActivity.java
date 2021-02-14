package ca.cmpt276.cosmos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // I have no idea how this works
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_main);
        setupStartButton();
        setupCreditsButton();
    }

    private void setupCreditsButton() {
        Button btn = findViewById(R.id.credits);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = activity_credits.LaunchIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }

    private void setupStartButton() {
        Button button = findViewById(R.id.start_game);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = game.launchIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }
}

