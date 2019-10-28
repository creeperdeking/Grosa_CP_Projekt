package com.example.grosa_cp_projekt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ExplanationActivity extends AppCompatActivity {

    public static final String EXPLANATION_TEXT_KEY = "explanation_text_key";
    public static final String EXPLANATION_TITLE_KEY = "explanation_title_key";
    public static final String LAUNCH_ACTIVITY_NAME_KEY = "launch_activity_name_key";
    public static final String EXTRA_PARAMETERS_KEY = "extra_parameters_key";

    private String activityClassname;
    private String extraParameters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explanation);
        String titleText = getIntent().getStringExtra(EXPLANATION_TITLE_KEY);
        String explanationText = getIntent().getStringExtra(EXPLANATION_TEXT_KEY);
        activityClassname = getIntent().getStringExtra(LAUNCH_ACTIVITY_NAME_KEY);
        extraParameters = getIntent().getStringExtra(EXTRA_PARAMETERS_KEY);

        TextView titleTextView = findViewById(R.id.expTitle);
        titleTextView.setText(titleText);
        TextView explanationTextView = findViewById(R.id.expText);
        explanationTextView.setText(explanationText);
    }

    public void onContinueButtonPushed(View view) {
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
