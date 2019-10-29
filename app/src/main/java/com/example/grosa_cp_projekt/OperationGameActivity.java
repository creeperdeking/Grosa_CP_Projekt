package com.example.grosa_cp_projekt;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.grosa_cp_projekt.db.DatabaseClient;
import com.example.grosa_cp_projekt.db.Score;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.ceil;
import static java.lang.Math.round;


public class OperationGameActivity extends GameActivity {

    private OperationActivity.Operation operation;
    private ChooseDifficulty.Difficulty difficulty = ChooseDifficulty.Difficulty.EASY;

    private DatabaseClient mDb;
    private boolean reviewMode = false;
    private Integer scoreFinal = 0;

    class OperationResult {
        public int firstNumber;
        public int secondNumber;
        public EditText result;

        public OperationResult(int firstNumber, int secondNumber, EditText result) {
            this.firstNumber = firstNumber;
            this.secondNumber = secondNumber;
            this.result = result;
        }

        public boolean isResultCorrect() {
            try {
                int resultVal = Integer.decode(result.getText().toString());
                return computeResult() == resultVal;

            } catch (Exception ex){
                return false;
            }
        }

        public Integer computeResult() {
            switch (operation) {
                case ADDITION:
                    return firstNumber + secondNumber;
                case SUBSTRACTION:
                    return firstNumber - secondNumber;
                case MULTIPLICATION:
                    return firstNumber * secondNumber;
                case DIVISION:
                    return firstNumber / secondNumber;
                    default:
                        return 0;
            }
        }
    }

    ArrayList<OperationResult> operationResults = new ArrayList<>();

    @Override
    protected void onCreate(Bundle activityBundle) {
        super.onCreate(activityBundle);
        setContentView(R.layout.operations_game);
        operation = OperationActivity.Operation.valueOf(getIntent().getStringExtra(EXTRA_PARAMETERS_KEY));
        difficulty = ChooseDifficulty.Difficulty.valueOf(getIntent().getStringExtra(DIFFICULTY_KEY));
        mDb = DatabaseClient.getInstance(this);

        LinearLayout mainLayout = findViewById(R.id.operations_layout);
        int nbOperations = 0;
        int rangeMin = 0;
        int rangeMax = 0;
        switch (operation) {
            case ADDITION:
            case SUBSTRACTION:
                switch (difficulty) {
                    case EASY:
                        nbOperations = 3;
                        rangeMin = 1;
                        rangeMax = 10;
                        break;
                    case NORMAL:
                        nbOperations = 4;
                        rangeMin = 11;
                        rangeMax = 100;
                        break;
                    case HARD:
                        nbOperations = 5;
                        rangeMin = 100;
                        rangeMax = 1500;
                        break;
                }
                break;
            case MULTIPLICATION:
            case DIVISION:
                switch (difficulty) {
                    case EASY:
                        nbOperations = 3;
                        rangeMin = 1;
                        rangeMax = 10;
                        break;
                    case NORMAL:
                        nbOperations = 4;
                        rangeMin = 1;
                        rangeMax = 50;
                        break;
                    case HARD:
                        nbOperations = 5;
                        rangeMin = 50;
                        rangeMax = 300;
                        break;
                }
                break;
        }
        // Building Layout:
        float factor = this.getResources().getDisplayMetrics().density; // the density factor
        mainLayout.removeAllViews();
        for (int i = 0; i < nbOperations; i++) {
            int firstNumberVal;
            int secondNumberVal;
            boolean pairExist;
            do {
                firstNumberVal = getRandomNumberInRange(rangeMin, rangeMax+1);
                do {
                    secondNumberVal = getRandomNumberInRange(rangeMin, rangeMax+1);
                } while(secondNumberVal == firstNumberVal);
                pairExist = false;
                for (OperationResult operationResult : operationResults) {
                    if (operationResult.firstNumber == firstNumberVal && operationResult.secondNumber == secondNumberVal) {
                        pairExist = true;
                        break;
                    }
                }
            } while(pairExist);
            if (operation.equals(OperationActivity.Operation.DIVISION)) {
                firstNumberVal = firstNumberVal * secondNumberVal;
            }

            LinearLayout.LayoutParams fieldsParameters = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            fieldsParameters.gravity = Gravity.CENTER;
            fieldsParameters.weight = 1;

            LinearLayout horizontalLayout = new LinearLayout(this);
            LinearLayout.LayoutParams horizontalLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            horizontalLayoutParams.setMargins(0, (int)(10*factor), 0, (int)(10*factor));
            horizontalLayoutParams.gravity = Gravity.CENTER;
            horizontalLayout.setLayoutParams(horizontalLayoutParams);
            horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);

            TextView firstNumber = new TextView(this);
            firstNumber.setLayoutParams(fieldsParameters);
            firstNumber.setText(String.valueOf(firstNumberVal));
            firstNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 33);

            ImageView operationImg = new ImageView(this);
            operationImg.setLayoutParams(fieldsParameters);
            switch (operation) {
                case ADDITION:
                    operationImg.setImageResource(R.drawable.plus);
                    break;
                case SUBSTRACTION:
                    operationImg.setImageResource(R.drawable.minus);
                    break;
                case MULTIPLICATION:
                    operationImg.setImageResource(R.drawable.mult);
                    break;
                case DIVISION:
                    operationImg.setImageResource(R.drawable.divide);
                    break;
            }

            TextView secondNumber = new TextView(this);
            secondNumber.setLayoutParams(fieldsParameters);
            secondNumber.setText(String.valueOf(secondNumberVal));
            secondNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, 33);

            TextView equals = new TextView(this);
            equals.setLayoutParams(fieldsParameters);
            equals.setText("=");
            equals.setTextSize(TypedValue.COMPLEX_UNIT_SP, 33);

            EditText result = new EditText(this);
            result.setLayoutParams(fieldsParameters);
            result.setWidth((int)(90*factor));

            operationResults.add(new OperationResult(firstNumberVal, secondNumberVal, result));

            horizontalLayout.addView(firstNumber);
            horizontalLayout.addView(operationImg);
            horizontalLayout.addView(secondNumber);
            horizontalLayout.addView(equals);
            horizontalLayout.addView(result);

            mainLayout.addView(horizontalLayout);
        }
    }

    public void finishAct() {
        // Update score:
        class UpdateGameScore extends AsyncTask<Void, Void, List<Score>> {
            @Override
            protected List<Score> doInBackground(Void... voids) {
                List<Score> scores = mDb.getAppDatabase().scoreDao().getAllUserScore(((MyApplication)getApplication()).getUser().getId());
                return scores;
            }

            @Override
            protected void onPostExecute(List<Score> scores) {
                super.onPostExecute(scores);
                for (Score score : scores) {
                    if (score.getGameId().equals(((MyApplication) getApplication()).getCurrentGame().getId())) {
                        class SetNewGameScore extends AsyncTask<Score, Void, Void> {
                            @Override
                            protected Void doInBackground(Score... score_) {
                                Score score = score_[0];
                                System.out.println("Score");
                                System.out.println(getString(((MyApplication) getApplication()).getCurrentGame().getGameNameId()));
                                score.setScore(score.getScore()+scoreFinal);
                                mDb.getAppDatabase().scoreDao().update(score);
                                return null;
                            }

                            @Override
                            protected void onPostExecute(Void voids) {
                                super.onPostExecute(voids);
                            }
                        }
                        SetNewGameScore setNewGameScore = new SetNewGameScore();
                        setNewGameScore.execute(score);
                        return;
                    }
                }
            }
        }
        UpdateGameScore updateGameScore = new UpdateGameScore();
        updateGameScore.execute();

        Intent intent = new Intent(this, CongratulationActivity.class);
        intent.putExtra(CongratulationActivity.NB_POINTS_KEY, scoreFinal.toString());
        startActivity(intent);
    }

    public void onCheckResultButtonPushed(View view) {
        if (reviewMode) {
            finishAct();
        } else {
            int errorNb = 0;
            for (OperationResult operationResult : operationResults) {
                if (!operationResult.isResultCorrect()) {
                    errorNb++;
                }
            }

            final Integer scoreVal;
            switch (difficulty) {
                case EASY:
                    scoreVal = 10;
                    break;
                case NORMAL:
                    scoreVal = 20;
                    break;
                case HARD:
                    scoreVal = 40;
                    break;
                default:
                    scoreVal = 0;
                    break;
            }

            scoreFinal = (int)round(ceil(Double.valueOf(scoreVal) * Double.valueOf(operationResults.size() - errorNb)/Double.valueOf(operationResults.size())));

            if (errorNb == 0) {
                finishAct();
            } else {
                Button checkButton = findViewById(R.id.check_button);
                checkButton.setText(getString(R.string.continue_));
                for (OperationResult operationResult : operationResults) {
                    if (!operationResult.isResultCorrect()) {
                        Integer result = operationResult.computeResult();
                        operationResult.result.setText(result.toString());
                        operationResult.result.setBackgroundColor(getResources().getColor(R.color.colorLightRed));
                    }
                    operationResult.result.setFocusable(false);
                }
                reviewMode = true;
            }
        }
    }

    public void onHelpButtonPushed(View view) {
        Intent intent = new Intent(this, ExplanationActivity.class);
        intent.putExtra(ExplanationActivity.EXPLANATION_TEXT_KEY, getString(R.string.explanation_operation));
        intent.putExtra(ExplanationActivity.EXPLANATION_TITLE_KEY, getString(R.string.operation_game_name));
        intent.putExtra(ExplanationActivity.GAME_NAME_KEY, getString(R.string.operation_game_name));
        intent.putExtra(ExplanationActivity.GAME_IMAGE_ID_KEY, R.drawable.operations);
        startActivity(intent);
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
