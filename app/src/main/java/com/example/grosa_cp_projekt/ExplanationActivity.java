package com.example.grosa_cp_projekt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ExplanationActivity extends AppCompatActivity {

    public static final String EXPLANATION_TEXT_KEY = "explanation_text_key";
    public static final String EXPLANATION_TITLE_KEY = "explanation_title_key";
    public static final String GAME_NAME_KEY = "game_name_key";
    public static final String GAME_IMAGE_ID_KEY = "game_image_id_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explanation);
        String titleText = getIntent().getStringExtra(EXPLANATION_TITLE_KEY);
        String explanationText = getIntent().getStringExtra(EXPLANATION_TEXT_KEY);
        String game_name = getIntent().getStringExtra(GAME_NAME_KEY);
        Integer game_image_id = getIntent().getIntExtra(GAME_IMAGE_ID_KEY, 0);

        TextView titleTextView = findViewById(R.id.expTitle);
        titleTextView.setText(titleText);
        TextView explanationTextView = findViewById(R.id.expText);
        explanationTextView.setText(explanationText);
        TextView gameNameView = findViewById(R.id.game_name_text);
        gameNameView.setText(game_name);
        ImageView gameImageView = findViewById(R.id.game_image);
        gameImageView.setImageResource(game_image_id);
    }

    public void onContinueButtonPushed(View view) {
        finish();
    }
}
