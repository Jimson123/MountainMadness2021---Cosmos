package ca.cmpt276.cosmos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class activity_credits extends AppCompatActivity {

    public static Intent LaunchIntent(Context context) {
        Intent intent = new Intent(context, activity_credits.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
        setupExitButton();
    }

    private void setupExitButton() {
        Button button = findViewById(R.id.exit_credits);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}