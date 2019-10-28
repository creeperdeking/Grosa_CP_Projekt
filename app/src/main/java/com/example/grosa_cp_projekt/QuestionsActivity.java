package com.example.grosa_cp_projekt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class QuestionsActivity extends AppCompatActivity {

    enum Topic {
        English,
        History,
        Gegraphy
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic_choice);
    }

    protected void onEnglishButtonPushed(View view) {

    }

    protected void onHistoryButtonPushed(View view) {

    }

    protected void onGeographyButtonPushed(View view) {

    }

    private void launchGame(OperationActivity.Operation operation) {
        Intent intent = new Intent(this, OperationGameActivity.class);
        intent.putExtra(OperationGameActivity.EXTRA_PARAMETERS_KEY, operation.toString());
        startActivity(intent);
    }

    public void onHelpButtonPushed(View view) {
        Intent intent = new Intent(this, ExplanationActivity.class);
        intent.putExtra(ExplanationActivity.EXPLANATION_TEXT_KEY, getString(R.string.explanation_questions));
        intent.putExtra(ExplanationActivity.EXPLANATION_TITLE_KEY, getString(R.string.questions_game_name));
        intent.putExtra(ExplanationActivity.GAME_NAME_KEY, getString(R.string.questions_game_name));
        intent.putExtra(ExplanationActivity.GAME_IMAGE_ID_KEY, R.drawable.questions);
        startActivity(intent);
    }
}
