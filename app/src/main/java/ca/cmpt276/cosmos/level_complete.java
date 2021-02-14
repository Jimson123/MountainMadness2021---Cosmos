package ca.cmpt276.cosmos;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.InputStream;

public class level_complete extends AppCompatActivity {

    private static String EXTRA_SCORE = "extra-score";
    private static String EXTRA_DIFFICULTY = "extra-difficultyNeeded";

    private int lastStage = 5;

    public static Intent launchIntent(Context context, int score, int difficulty) {
        Intent intent = new Intent(context, level_complete.class);
        intent.putExtra(EXTRA_SCORE, score);
        intent.putExtra(EXTRA_DIFFICULTY, difficulty);
        return intent;
    }

    @SuppressLint("SetTextI18n") // I have no idea what this does. Alt + Shift + Enter said it might help
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_complete);
        Intent levelIntent = getIntent();
        int stage = levelIntent.getIntExtra(EXTRA_DIFFICULTY, 1);
        TextView completionText = findViewById(R.id.completionText);
        if (stage < lastStage) {
            completionText.setText("You completed Level " + stage + ".\nPress the button below to start Level " + (stage + 1) + ".");
        } else {
            completionText.setText("You completed Level " + stage + ".\nThank you for playing!");
        }
        setupExitButton();
        setupNextLevelButton();
        setupScore();
    }

    private void setupScore() {
        TextView scoretxt = findViewById(R.id.endScore);
        Intent intent = getIntent();
        scoretxt.setText("Score: " + intent.getIntExtra(EXTRA_SCORE,0));
    }

    private void setupExitButton() {
        Button button = findViewById(R.id.exi_game_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void setupNextLevelButton(){
        Button button = findViewById(R.id.next_level);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent difficultyIntent = getIntent();
                //int difficulty = difficultyIntent.getIntExtra(EXTRA_DIFFICULTY, 0);
                //Intent intent = game.launchIntent(level_complete.this, difficulty + 1);
                Intent levelIntent = getIntent();
                int stage = levelIntent.getIntExtra(EXTRA_DIFFICULTY, 1);
                Intent intent = game.launchIntent(level_complete.this, stage + 1);
                finish();
                startActivity(intent);
            }
        });
    }
}