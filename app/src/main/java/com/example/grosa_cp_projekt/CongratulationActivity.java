package com.example.grosa_cp_projekt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CongratulationActivity extends AppCompatActivity {

    public static final String NB_POINTS_KEY = "nb_points_key";

    String nbPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.congratulation);
        nbPoints = getIntent().getStringExtra(NB_POINTS_KEY);
        TextView scoreText = findViewById(R.id.score_text);
        scoreText.setText(nbPoints);
    }

    public void onContinueButtonPushed(View view) {
        Intent intent = new Intent(this, ChooseGameActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
