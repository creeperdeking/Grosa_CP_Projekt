package com.example.grosa_cp_projekt;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.grosa_cp_projekt.db.DatabaseClient;
import com.example.grosa_cp_projekt.db.Score;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MagicSquareGameActivity extends GameActivity {
    private ChooseDifficulty.Difficulty difficulty;
    private DatabaseClient mDb;
    private int[][] magicSquare;
    private EditText[][] magicSquareView = new EditText[3][3];
    boolean review = false;
    Integer scoreFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.magic_square_game);
        difficulty = ChooseDifficulty.Difficulty.valueOf(getIntent().getStringExtra(DIFFICULTY_KEY));
        mDb = DatabaseClient.getInstance(this);
        createGrid();
    }

    private void createGrid() {
        generateMagicSquare(3);
        int nbHints = 0;
        boolean showHelp = true;
        switch (difficulty) {
            case EASY:
                nbHints = 6;
                scoreFinal = 30;
                break;
            case NORMAL:
                nbHints = 4;
                scoreFinal = 100;
                break;
            case HARD:
                nbHints = 2;
                scoreFinal = 350;
                showHelp = false;
                break;
        }


        TextView hint = findViewById(R.id.help);
        if (showHelp) {
            hint.setVisibility(View.VISIBLE);
            hint.setText(hint.getText()+" "+(magicSquare[0][0]+magicSquare[0][1]+magicSquare[0][2]));
        } else {
            hint.setVisibility(View.INVISIBLE);
        }

        ArrayList<Boolean> hintFlags = new ArrayList<>(Collections.nCopies(9, false));
        for (int i = 0; i < nbHints; i++) {
            hintFlags.set(i, true);
        }
        Collections.shuffle(hintFlags);

        LinearLayout mainLayout = findViewById(R.id.magic_square_container);
        mainLayout.removeAllViews();
        float factor = this.getResources().getDisplayMetrics().density;
        LinearLayout.LayoutParams horizontalLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        horizontalLayoutParams.setMargins((int)(50*factor), (int)(10*factor), (int)(50*factor), (int)(10*factor));
        horizontalLayoutParams.weight = 1;
        LinearLayout.LayoutParams cellParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        cellParams.weight = 1;
        cellParams.gravity = Gravity.CENTER;
        int c = 0;
        for (int i = 0; i < 3; i++) {
            LinearLayout rowLayout = new LinearLayout(this);
            rowLayout.setLayoutParams(horizontalLayoutParams);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0; j< 3; j++) {
                EditText cell = new EditText(this);
                cell.setLayoutParams(cellParams);
                cell.setInputType(InputType.TYPE_CLASS_NUMBER);
                if (hintFlags.get(c).equals(true)) {
                    cell.setText(String.valueOf(magicSquare[i][j]));
                    cell.setEnabled(false);
                }
                c++;
                rowLayout.addView(cell);
                magicSquareView[i][j] = cell;
            }
            mainLayout.addView(rowLayout);
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

    public void onCheckAnswerButtonPushed(View view) {
        if (review) {
            finishAct();
        } else {
            boolean isGood = true;
            int magicNumber = (magicSquare[0][0]+magicSquare[0][1]+magicSquare[0][2]);
            for (int i = 0; i < 3; i++) {
                int sum = 0;
                for (int j = 0; j < 3; j++) {
                    String val = magicSquareView[i][j].getText().toString();
                    if (!val.equals("")) {
                        sum += Integer.decode(val);
                    }
                }
                if (sum != magicNumber) {
                    isGood = false;
                    for (int j = 0; j < 3; j++) {
                        magicSquareView[i][j].setBackgroundColor(getResources().getColor(R.color.colorLightRed));
                    }
                }
            }
            for (int j = 0; j < 3; j++) {
                int sum = 0;
                for (int i = 0; i < 3; i++) {
                    String val = magicSquareView[i][j].getText().toString();
                    if (!val.equals("")) {
                        sum += Integer.decode(val);
                    }
                }
                if (sum != magicNumber) {
                    isGood = false;
                    for (int i = 0; i< 3; i++) {
                        magicSquareView[i][j].setBackgroundColor(getResources().getColor(R.color.colorLightRed));
                    }
                }
            }
            if (!isGood) {
                Button checkButton = findViewById(R.id.check_button);
                checkButton.setText(getString(R.string.continue_));
                scoreFinal = 0;
                review = true;
            } else {
                finishAct();
            }
        }
    }

    private void generateMagicSquare(int n) {
        if (n % 2 == 0) throw new RuntimeException("n must be odd");

        int[][] magic = new int[n][n];

        int row = n-1;
        int col = n/2;
        magic[row][col] = 1;

        for (int i = 2; i <= n*n; i++) {
            if (magic[(row + 1) % n][(col + 1) % n] == 0) {
                row = (row + 1) % n;
                col = (col + 1) % n;
            }
            else {
                row = (row - 1 + n) % n;
                // don't change col
            }
            magic[row][col] = i;
        }

        magicSquare = magic;
    }

    public void onHelpButtonPushed(View view) {
        Intent intent = new Intent(this, ExplanationActivity.class);
        intent.putExtra(ExplanationActivity.EXPLANATION_TEXT_KEY, getString(R.string.explanation_magicsquare));
        intent.putExtra(ExplanationActivity.EXPLANATION_TITLE_KEY, getString(R.string.magic_square_game_name));
        intent.putExtra(ExplanationActivity.GAME_NAME_KEY, getString(R.string.magic_square_game_name));
        intent.putExtra(ExplanationActivity.GAME_IMAGE_ID_KEY, R.drawable.magicsquare);
        startActivity(intent);
    }

}
