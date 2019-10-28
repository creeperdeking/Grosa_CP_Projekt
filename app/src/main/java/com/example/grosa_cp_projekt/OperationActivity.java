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
        String title = getString(game.getGameNameId());
        String text = getString(game.getExplanationId());
        Intent intent = new Intent(this, ExplanationActivity.class);
        intent.putExtra(ExplanationActivity.EXPLANATION_TITLE_KEY, title);
        intent.putExtra(ExplanationActivity.EXPLANATION_TEXT_KEY, text);
        intent.putExtra(ExplanationActivity.LAUNCH_ACTIVITY_NAME_KEY, "com.example.grosa_cp_projekt.OperationGameActivity");
        intent.putExtra(ExplanationActivity.EXTRA_PARAMETERS_KEY, operation.toString());
        startActivity(intent);
    }
}
