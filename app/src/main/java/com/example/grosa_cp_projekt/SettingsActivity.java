package com.example.grosa_cp_projekt;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grosa_cp_projekt.db.DatabaseClient;
import com.example.grosa_cp_projekt.db.Game;
import com.example.grosa_cp_projekt.db.Score;
import com.example.grosa_cp_projekt.db.User;

import java.util.List;
import java.util.Set;

///
public class SettingsActivity extends AppCompatActivity {

    private DatabaseClient mDb;
    private boolean startup = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        mDb = DatabaseClient.getInstance(this);
        if (!((MyApplication)getApplication()).isAdmin()) {
            Intent intent = new Intent(this, PasswordActivity.class);
            startActivity(intent);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!startup) {
            if (!((MyApplication)getApplication()).isAdmin()) {
                Intent intent = new Intent(this, ConnectActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        } else {
            startup = false;
        }
    }

    public void onRemoveUserButtonPushed(View view) {
        final User user = ((MyApplication)getApplication()).getUser();
        if (user == null) {
            return;
        }

        removeUser(user);

        ((MyApplication)getApplication()).setUser(null);

        Toast toast = Toast.makeText(this, R.string.user_removed, Toast.LENGTH_LONG);
        toast.show();
    }

    public void onReinitUserButtonPushed(View view) {
        final User user = ((MyApplication)getApplication()).getUser();
        if (user == null) {
            return;
        }

        resetUserScore(user);
        Toast toast = Toast.makeText(this, R.string.scores_removed, Toast.LENGTH_LONG);
        toast.show();
    }

    private void removeUser(final User user) {
        class RemoveUser extends AsyncTask<Void, Void, List<Score>> {

            @Override
            protected List<Score> doInBackground(Void... voids) {
                mDb.getAppDatabase().userDao().delete(user);
                List<Score> scores = mDb.getAppDatabase().scoreDao().getAllUserScore(user.getId());
                return scores;
            }

            @Override
            protected void onPostExecute(List<Score> scores) {
                super.onPostExecute(scores);
                for (Score score : scores) {
                    class RemoveUserScore extends AsyncTask<Score, Void, Void> {

                        @Override
                        protected Void doInBackground(Score... scores) {
                            mDb.getAppDatabase().scoreDao().delete(scores[0]);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void voids) {
                        }
                    }
                    RemoveUserScore removeUserScore = new RemoveUserScore();
                    removeUserScore.execute(score);
                }
            }
        }
        RemoveUser removeUser = new RemoveUser();
        removeUser.execute();
    }

    private void resetUserScore(final User user) {
        class ResetUserScore extends AsyncTask<Void, Void, List<Score>> {

            @Override
            protected List<Score> doInBackground(Void... voids) {
                List<Score> scores = mDb.getAppDatabase().scoreDao().getAllUserScore(user.getId());
                return scores;
            }

            @Override
            protected void onPostExecute(List<Score> scores) {
                super.onPostExecute(scores);
                for (Score score : scores) {
                    score.setScore(0);
                    class UpdateScore extends AsyncTask<Score, Void, Void> {

                        @Override
                        protected Void doInBackground(Score... scores) {
                            mDb.getAppDatabase().scoreDao().update(scores[0]);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void scores) {
                        }
                    }
                    UpdateScore updateScore = new UpdateScore();
                    updateScore.execute(score);
                }
            }
        }
        ResetUserScore resetUserScore = new ResetUserScore();
        resetUserScore.execute();
    }

    public void onContinueButtonPushed(View view) {
        Intent intent = new Intent(this, ConnectActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
