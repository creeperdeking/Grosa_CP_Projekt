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

import com.example.grosa_cp_projekt.db.AdminPassword;
import com.example.grosa_cp_projekt.db.DatabaseClient;
import com.example.grosa_cp_projekt.db.Game;
import com.example.grosa_cp_projekt.db.Score;
import com.example.grosa_cp_projekt.db.User;

import org.w3c.dom.Text;

import java.util.List;

public class ConnectActivity extends AppCompatActivity {
    private DatabaseClient mDb;

    private LinearLayout usersLayout;
    private AppCompatActivity me = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion);
        usersLayout = findViewById(R.id.users_layout);
        mDb = DatabaseClient.getInstance(this);

        updateUsers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUsers();
    }

    private void updateUsers() {
        /**
         * Création d'une classe asynchrone pour récupérer les utilisateurs
         */
        class GetUserList extends AsyncTask<Void, Void, List<User>> {

            @Override
            protected List<User> doInBackground(Void... voids) {
                List<User> users = mDb.getAppDatabase().userDao().getAll();

                return users;
            }

            @Override
            protected void onPostExecute(List<User> users) {
                super.onPostExecute(users);
                updateUsersLayout(users);
            }
        }

        GetUserList getUserList = new GetUserList();
        getUserList.execute();
    }

    public void updateUsersLayout(List<User> users) {
        usersLayout.removeAllViews();
        for (final User user : users) {
            float factor = this.getResources().getDisplayMetrics().density; // the density factor
            LinearLayout.LayoutParams textParameter = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            textParameter.setMargins((int)(10*factor), 0, 0, 0);
            LinearLayout.LayoutParams textParameterShifted = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            textParameterShifted.setMargins((int)(20*factor), 0, 0, 0);
            LinearLayout.LayoutParams subLayoutsParameters = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            LinearLayout userLayout = new LinearLayout(this);
            LinearLayout.LayoutParams mainLayoutParameter = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            mainLayoutParameter.setMargins((int)(10*factor), (int)(20*factor), (int)(10*factor), 0);
            userLayout.setBackgroundColor(getResources().getColor(R.color.colorLightGrayElement));
            userLayout.setLayoutParams(mainLayoutParameter);
            userLayout.setOrientation(LinearLayout.HORIZONTAL);

            ImageView userIcon = new ImageView(this);
            userIcon.setImageResource(user.getImageId());
            userIcon.setScaleType(ImageView.ScaleType.FIT_XY);
            userIcon.setLayoutParams(new LinearLayout.LayoutParams((int)(140*factor), (int)(140*factor)));

            LinearLayout rightLayout = new LinearLayout(this);
            rightLayout.setOrientation(LinearLayout.VERTICAL);
            rightLayout.setLayoutParams(subLayoutsParameters);

            LinearLayout scoreTextLayout = new LinearLayout(this);
            scoreTextLayout.setOrientation(LinearLayout.HORIZONTAL);
            scoreTextLayout.setLayoutParams(subLayoutsParameters);

            TextView userName = new TextView(this);
            userName.setText(user.getName());
            userName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 33);
            userName.setLayoutParams(textParameter);

            TextView userSurname = new TextView(this);
            userSurname.setText(user.getSurname());
            userSurname.setTextSize(TypedValue.COMPLEX_UNIT_SP, 33);
            userSurname.setLayoutParams(textParameter);

            final TextView userScore = new TextView(this);
            /**
             * Création d'une classe asynchrone pour trouver la valeure du score
             */
            class GetUserScore extends AsyncTask<TextView, Void, List<Score>> {
                TextView view;

                @Override
                protected List<Score> doInBackground(TextView... view) {
                    List<Score> scores = mDb.getAppDatabase().scoreDao().getAllUserScore(user.getId());
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
            getUserScore.execute(userScore);
            userScore.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
            userScore.setLayoutParams(textParameterShifted);

            TextView scoreText = new TextView(this);
            scoreText.setText(getString(R.string.points));
            scoreText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
            scoreText.setLayoutParams(textParameter);

            scoreTextLayout.addView(userScore);
            scoreTextLayout.addView(scoreText);

            rightLayout.addView(userName);
            rightLayout.addView(userSurname);
            rightLayout.addView(scoreTextLayout);


            userLayout.addView(userIcon);
            userLayout.addView(rightLayout);
            userLayout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    ((MyApplication) ConnectActivity.this.getApplication()).setUser(user);
                    startChooseGameActivity();
                }
            });
            usersLayout.addView(userLayout);
        }
    }

    private void startChooseGameActivity() {
        Intent intent = new Intent(this, ChooseGameActivity.class);
        startActivity(intent);
    }

    public void onCreateUserClick(View view) {
        Intent intent = new Intent(this, CreateUserActivity.class);
        startActivity(intent);
    }

    public void onAnonymousClick(View view) {
        User user = new User("", "", R.drawable.anonymous);
        ((MyApplication)getApplication()).setUser(user);
        startChooseGameActivity();
    }
}
