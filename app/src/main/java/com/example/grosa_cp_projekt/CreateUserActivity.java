package com.example.grosa_cp_projekt;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.grosa_cp_projekt.db.DatabaseClient;
import com.example.grosa_cp_projekt.db.Game;
import com.example.grosa_cp_projekt.db.Score;
import com.example.grosa_cp_projekt.db.User;

import java.util.ArrayList;
import java.util.List;

public class CreateUserActivity extends AppCompatActivity {
    private DatabaseClient mDb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_user);
        mDb = DatabaseClient.getInstance(this);
    }

    public void onCreateUserButtonClick(View view) {
        final String userName = ((EditText)findViewById(R.id.name_text)).getText().toString();
        String userSurname = ((EditText)findViewById(R.id.surname_text)).getText().toString();

        if (userName.equals("") || userSurname.equals("")) {
            Toast toast = Toast.makeText(this, R.string.name_or_surname_empty, Toast.LENGTH_LONG);
            toast.show();
        } else {
            class CreateUser extends AsyncTask<String, Void, User> {

                @Override
                protected User doInBackground(String... strings) {
                    User user = new User(0, strings[0], strings[1], R.drawable.user_1);
                    mDb.getAppDatabase().userDao().insert(user);

                    return user;
                }

                @Override
                protected void onPostExecute(final User userCreated) {
                    super.onPostExecute(userCreated);
                    ArrayList<Game> games = new ArrayList<>();
                    class GetGames extends AsyncTask<ArrayList<Game>, Void, ArrayList<Game>> {
                        ArrayList<Game> gameArray;

                        @Override
                        protected ArrayList<Game> doInBackground(ArrayList<Game>... gameArray_) {
                            gameArray = gameArray_[0];
                            return new ArrayList<>(mDb.getAppDatabase().gameDao().getAll());
                        }

                        @Override
                        protected void onPostExecute(ArrayList<Game> games_) {
                            super.onPostExecute(games_);
                            gameArray.addAll(games_);
                            int id = 0;
                            for (Game game : gameArray) {
                                id++;
                                final int idd = id;
                                Score score = new Score();
                                score.setScore(0);
                                System.out.println("gameid: "+game.getId());
                                System.out.println("userid: "+userCreated.getId());//Todo: bugs, bugs everywere
                                score.setGameId(game.getId());
                                score.setUserId(userCreated.getId());
                                class InsertScore extends AsyncTask<Score, Void, Void> {

                                    @Override
                                    protected Void doInBackground(Score... scores) {
                                        mDb.getAppDatabase().scoreDao().insert(scores[0]);
                                        return null;
                                    }

                                    @Override
                                    protected void onPostExecute(Void games_) {
                                        super.onPostExecute(games_);
                                        if (idd == gameArray.size()) {
                                            Intent intent = new Intent(CreateUserActivity.this, ConnectActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                        }
                                    }
                                }
                                InsertScore insertScore = new InsertScore();
                                insertScore.execute(score);
                            }
                        }
                    }
                    GetGames getGames = new GetGames();
                    getGames.execute(games);
                }
            }

            CreateUser createUser = new CreateUser();
            createUser.execute(userName, userSurname);
        }
    }
}
