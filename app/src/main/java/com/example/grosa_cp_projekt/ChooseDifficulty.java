package com.example.grosa_cp_projekt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ChooseDifficulty extends AppCompatActivity {

    public enum Difficulty{
        EASY,
        NORMAL,
        HARD
    };

    public static final String LAUNCH_ACTIVITY_NAME_KEY = "launch_activity_name_key";
    public static final String EXTRA_PARAMETERS_KEY = "extra_parameters_key";

    private String activityClassname;
    private String extraParameters;

    @Override
    protected void onCreate(Bundle activityBundle) {
        super.onCreate(activityBundle);
        setContentView(R.layout.operations_game);

        activityClassname = getIntent().getStringExtra(LAUNCH_ACTIVITY_NAME_KEY);
        extraParameters = getIntent().getStringExtra(EXTRA_PARAMETERS_KEY);
    }

    public void onEasyButtonPushed(View view) {
        startGame(Difficulty.EASY);
    }

    public void onNormalButtonPushed(View view) {
        startGame(Difficulty.NORMAL);
    }

    public void onHardButtonPushed(View view) {
        startGame(Difficulty.HARD);
    }

    private void startGame(Difficulty difficulty) {

        try {
            Intent intent = new Intent(this, Class.forName(activityClassname));
            GameActivity obj = (GameActivity)(Object)Class.forName(activityClassname);
            intent.putExtra(obj.EXTRA_PARAMETERS_KEY, extraParameters);
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            Toast toast = Toast.makeText(this, "Game not found", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
