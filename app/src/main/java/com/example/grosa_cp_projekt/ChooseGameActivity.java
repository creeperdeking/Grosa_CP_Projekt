package com.example.grosa_cp_projekt;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grosa_cp_projekt.db.DatabaseClient;
import com.example.grosa_cp_projekt.db.Game;
import com.example.grosa_cp_projekt.db.Score;

import java.util.List;

public class ChooseGameActivity extends AppCompatActivity {
    private DatabaseClient mDb;

    private LinearLayout gamesLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_game);
        ((MyApplication)getApplication()).setCurrentGame(null);
        mDb = DatabaseClient.getInstance(this);
        gamesLayout = findViewById(R.id.games_layout);

        class GetGameList extends AsyncTask<Void, Void, List<Game>> {

            @Override
            protected List<Game> doInBackground(Void... voids) {
                List<Game> games = mDb.getAppDatabase().gameDao().getAll();

                return games;
            }

            @Override
            protected void onPostExecute(List<Game> games) {
                super.onPostExecute(games);
                updateGamesLayout(games);
            }
        }

        TextView scoreTotal = findViewById(R.id.score_total);

        class GetUserScore extends AsyncTask<TextView, Void, List<Score>> {
            TextView view;

            @Override
            protected List<Score> doInBackground(TextView... view) {
                List<Score> scores = mDb.getAppDatabase().scoreDao().getAllUserScore(((MyApplication)getApplication()).getUser().getId());
                this.view = view[0];
                return scores;
            }

            @Override
            protected void onPostExecute(List<Score> scores) {
                super.onPostExecute(scores);
                Integer sum = 0;
                for (Score score : scores) {
                    sum += score.getScore();
                    System.out.println(sum);
                }
                view.setText(sum.toString());
                view.invalidate();
            }
        }
        GetUserScore getUserScore = new GetUserScore();
        getUserScore.execute(scoreTotal);

        GetGameList getGameList = new GetGameList();
        getGameList.execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((MyApplication)getApplication()).setCurrentGame(null);
        if (((MyApplication)getApplication()).getUser() == null) {
            Intent intent = new Intent(this, ConnectActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    public void onUserSettingsClick(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void updateGamesLayout(List<Game> games) {
        gamesLayout.removeAllViews();

        float factor = this.getResources().getDisplayMetrics().density; // the density factor

        for (final Game game : games) {
            LinearLayout.LayoutParams textParameter = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            textParameter.setMargins((int)(10*factor), 0, 0, 0);
            LinearLayout.LayoutParams textParameterShifted = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            textParameterShifted.setMargins((int)(20*factor), 0, 0, 0);

            LinearLayout gameLayout = new LinearLayout(this);
            LinearLayout.LayoutParams parameter = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            parameter.setMargins((int)(10*factor), (int)(20*factor), (int)(10*factor), 0);
            gameLayout.setBackgroundColor(getResources().getColor(R.color.colorLightGrayElement));
            gameLayout.setLayoutParams(parameter);
            gameLayout.setOrientation(LinearLayout.HORIZONTAL);

            ImageView gameIcon = new ImageView(this);
            gameIcon.setImageResource(game.getImageId());
            gameIcon.setScaleType(ImageView.ScaleType.FIT_XY);
            gameIcon.setLayoutParams(new LinearLayout.LayoutParams((int)(100*factor), (int)(100*factor)));

            LinearLayout rightLayout = new LinearLayout(this);
            rightLayout.setOrientation(LinearLayout.VERTICAL);

            LinearLayout scoreTextLayout = new LinearLayout(this);
            scoreTextLayout.setOrientation(LinearLayout.HORIZONTAL);

            TextView gameName = new TextView(this);
            gameName.setText(game.getGameNameId());
            gameName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 33);
            gameName.setLayoutParams(textParameter);


            final TextView userScore = new TextView(this);
            class GetUserGameScore extends AsyncTask<Game, Void, List<Score>> {
                Game game;

                @Override
                protected List<Score> doInBackground(Game... games) {
                    List<Score> scores = mDb.getAppDatabase().scoreDao().getAllUserScore(((MyApplication)getApplication()).getUser().getId());
                    this.game = games[0];
                    return scores;
                }

                @Override
                protected void onPostExecute(List<Score> scores) {
                    super.onPostExecute(scores);
                    for (Score score : scores) {
                        if (score.getGameId() == game.getId()) {
                            userScore.setText(score.getScore().toString());
                            return;
                        }
                    }
                }
            }
            GetUserGameScore getUserGameScore = new GetUserGameScore();
            getUserGameScore.execute(game);
            userScore.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
            userScore.setLayoutParams(textParameterShifted);

            TextView scoreText = new TextView(this);
            scoreText.setText(getString(R.string.points));
            scoreText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
            scoreText.setLayoutParams(textParameter);

            scoreTextLayout.addView(userScore);
            scoreTextLayout.addView(scoreText);

            rightLayout.addView(gameName);
            rightLayout.addView(scoreTextLayout);

            gameLayout.addView(gameIcon);
            gameLayout.addView(rightLayout);
            gameLayout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    try {
                        Intent intent = new Intent(ChooseGameActivity.this, Class.forName(game.getActivityClassname()));
                        ((MyApplication)getApplication()).setCurrentGame(game);
                        startActivity(intent);
                    } catch (ClassNotFoundException e) {
                        Toast toast = Toast.makeText(ChooseGameActivity.this, "Game not found", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            });
            gamesLayout.addView(gameLayout);
        }
    }
}
