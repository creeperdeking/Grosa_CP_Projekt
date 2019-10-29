package com.example.grosa_cp_projekt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grosa_cp_projekt.db.Game;

public class OperationActivity extends AppCompatActivity {
    enum Operation{
        ADDITION,
        SUBSTRACTION,
        MULTIPLICATION,
        DIVISION
    }

    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.operation_choice);
        game = ((MyApplication)getApplication()).getCurrentGame();
    }

    public void onAdditionButtonPushed(View view) {
        launchGame(Operation.ADDITION);
    }

    public void onSubstractionButtonPushed(View view) {
        launchGame(Operation.SUBSTRACTION);
    }

    public void onMultiplicationButtonPushed(View view) {
        launchGame(Operation.MULTIPLICATION);
    }

    public void onDivisionButtonPushed(View view) {
        launchGame(Operation.DIVISION);
    }

    private void launchGame(Operation operation) {
        Intent intent = new Intent(this, ChooseDifficulty.class);
        intent.putExtra(ChooseDifficulty.EXTRA_PARAMETERS_KEY, operation.toString());
        intent.putExtra(ChooseDifficulty.LAUNCH_ACTIVITY_NAME_KEY, OperationGameActivity.class.getName());
        startActivity(intent);
    }

    public void onHelpButtonPushed(View view) {
        Intent intent = new Intent(this, ExplanationActivity.class);
        intent.putExtra(ExplanationActivity.EXPLANATION_TEXT_KEY, getString(R.string.explanation_operation));
        intent.putExtra(ExplanationActivity.EXPLANATION_TITLE_KEY, getString(R.string.operation_game_name));
        intent.putExtra(ExplanationActivity.GAME_NAME_KEY, getString(R.string.operation_game_name));
        intent.putExtra(ExplanationActivity.GAME_IMAGE_ID_KEY, R.drawable.operations);
        startActivity(intent);
    }
}
