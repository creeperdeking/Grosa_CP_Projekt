package com.example.grosa_cp_projekt;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grosa_cp_projekt.db.DatabaseClient;
import com.example.grosa_cp_projekt.db.Score;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.ceil;
import static java.lang.Math.round;

public class QuestionGameActivity extends AppCompatActivity {
    public static final String EXTRA_PARAMETERS_KEY = "extra_parameters_key";

    private ArrayList<Questions.Question> questions;
    private Integer numCurrentQuestion = 1;
    private QuestionsActivity.Topic topic;
    private ChooseDifficulty.Difficulty difficulty;
    private DatabaseClient mDb;
    private boolean review = false;
    private Integer nbGoodAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_game);
        topic = QuestionsActivity.Topic.valueOf(getIntent().getStringExtra(EXTRA_PARAMETERS_KEY));
        difficulty = ChooseDifficulty.Difficulty.HARD; //todo: implement real difficulty
        mDb = DatabaseClient.getInstance(this);
        System.out.println("Topic: "+ topic.toString());

        questions = Questions.getQuestions(topic, difficulty);
        System.out.println(questions.size());

        showQuestion(numCurrentQuestion);
    }

    private void showQuestion(Integer numQuestion) {
        if (numQuestion == questions.size()) {
            finish();
        } else {
            Questions.Question currentQuestion = questions.get(numQuestion);
            TextView pageNbView = findViewById(R.id.page_number);
            pageNbView.setText(String.format("%d/%d", numQuestion, questions.size()));
            TextView questionNameView = findViewById(R.id.question_text);
            questionNameView.setText(currentQuestion.question);

            RadioGroup questionGroupView = findViewById(R.id.question_choices);
            questionGroupView.removeAllViews();
            ArrayList<String> answers = currentQuestion.getQuestionsRandom();
            for (String answer : answers) {
                RadioButton questionView = new RadioButton(this);
                questionView.setText(answer);
                float factor = this.getResources().getDisplayMetrics().density; // the density factor
                LinearLayout.LayoutParams horizontalLayoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                horizontalLayoutParams.setMargins((int)(20*factor), (int)(10*factor), (int)(10*factor), 0);
                questionView.setLayoutParams(horizontalLayoutParams);
                questionView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                questionGroupView.addView(questionView);
            }
        }

    }

    public void finish() {
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
        final Integer scoreFinal = (int)round(ceil(Double.valueOf(scoreVal) * Double.valueOf(nbGoodAnswers)/Double.valueOf(questions.size())));
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

    public void onContinuerButtonPushed(View view) {
        if (review) {
            review = false;
            numCurrentQuestion++;
            showQuestion(numCurrentQuestion);
        } else {
            RadioGroup questionGroupView = findViewById(R.id.question_choices);
            Integer id = questionGroupView.getCheckedRadioButtonId();
            boolean goodAnswer = true;
            RadioButton selectedRadioButton = findViewById(id);
            String goodAnswerStr = questions.get(numCurrentQuestion).good_answer;
            if (id != -1) {
                String selectedRadioButtonText = selectedRadioButton.getText().toString();
                goodAnswer = selectedRadioButtonText.equals(goodAnswerStr);
            } else {
                goodAnswer = false;
            }

            if (goodAnswer) {
                nbGoodAnswers++;
                numCurrentQuestion++;
                showQuestion(numCurrentQuestion);
            } else {
                review = true;
                selectedRadioButton.setBackgroundColor(getResources().getColor(R.color.colorLightRed));
                for(int index=0; index < questionGroupView.getChildCount(); ++index) {
                    RadioButton nextChild = (RadioButton)questionGroupView.getChildAt(index);
                    if (nextChild.getText().toString().equals(goodAnswerStr)) {
                        nextChild.setBackgroundColor(getResources().getColor(R.color.colorLightGreen));
                    }
                }
            }
        }
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
