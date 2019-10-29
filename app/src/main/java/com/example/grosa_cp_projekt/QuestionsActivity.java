package com.example.grosa_cp_projekt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class QuestionsActivity extends AppCompatActivity {

    enum Topic {
        English,
        History,
        Geography
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic_choice);
    }

    public void onEnglishButtonPushed(View view) {
        launchGame(Topic.English);
    }

    public void onHistoryButtonPushed(View view) {
        launchGame(Topic.History);
    }

    public void onGeographyButtonPushed(View view) {
        launchGame(Topic.Geography);
    }

    private void launchGame(Topic topic) {
        Intent intent = new Intent(this, QuestionGameActivity.class);
        intent.putExtra(OperationGameActivity.EXTRA_PARAMETERS_KEY, topic.toString());
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
