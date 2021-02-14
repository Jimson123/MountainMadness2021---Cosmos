package ca.cmpt276.cosmos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.InputStream;

public class level_complete extends AppCompatActivity {

    public static Intent launchIntent(Context context) {
        Intent intent = new Intent(context, level_complete.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_complete);
        setupExitButton();
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
                Intent intent = game.launchIntent(level_complete.this);
                finish();
                startActivity(intent);
            }
        });
    }
}