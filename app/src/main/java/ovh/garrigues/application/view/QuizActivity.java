package ovh.garrigues.application.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import ovh.garrigues.application.adapter.DynamicView;
import ovh.garrigues.application.question.Player;
import ovh.garrigues.application.question.Question;
import ovh.garrigues.application.R;
import ovh.garrigues.application.request.Request;


public class QuizActivity extends AppCompatActivity {
    private TextView mTexview;
    private ArrayList<Button> mButton_list = new ArrayList<>();
    private ArrayList<Question> question_list = new ArrayList<>();
    private LinearLayout mLinearLayout;
    private int num_Question = -1;
    private boolean question_end = false;
    private int numberCorrectAnswer = 0;
    private Gson gson = new Gson();
    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        player = gson.fromJson(getIntent().getStringExtra(MainActivity.PLAYER_STRING), Player.class);
        start();
    }

    private void start() {
        mTexview = findViewById(R.id.quizTextView_1);
        mButton_list.add((Button) findViewById(R.id.quizButton_1));
        mButton_list.add((Button) findViewById(R.id.quizButton_2));
        mButton_list.add((Button) findViewById(R.id.quizButton_3));
        mButton_list.add((Button) findViewById(R.id.quizButton_4));
        mLinearLayout = findViewById(R.id.quizLinearLayout);

        for (int i = 0; i < mButton_list.size(); i++) {
            final Button b = mButton_list.get(i);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickMethode(b);
                }
            });
        }
        getQuestion();
        if (question_list != null)
            nextQuestion();
    }

    private void getQuestion() {


        question_list = Request.getInstance().getQuestion();
        Request.getInstance().resetQuestion();
        if (question_list == null)
            finish();

    }

    private boolean check_Answer(int numButton, int number_question) {
        return numButton == question_list.get(number_question).getNumberAnswer();
    }

    private void nextQuestion() {
        num_Question++;
        if (num_Question < question_list.size()) {
            Question question = question_list.get(num_Question);
            if (!question.isError()) {


                for (Button b : mButton_list) {
                    b.setBackgroundColor(getColor(R.color.colorBackgroundQuestion));
                }
                mTexview.setText(question.getQuestion());
                int numButton = mButton_list.size();
                int numberOfQuestion = question.getAnswerStr().length;
                if (numButton != numberOfQuestion) {
                    if (numButton < numberOfQuestion) {
                        while (numButton != numberOfQuestion) {
                            final Button b = new DynamicView(this).create_Button(mLinearLayout);
                            mButton_list.add(b);
                            b.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    clickMethode(b);
                                }
                            });
                            numButton = mButton_list.size();
                        }

                    } else {
                        while (numButton != numberOfQuestion) {
                            Button b = mButton_list.get((mButton_list.size()) - 1);
                            mLinearLayout.removeView(b);
                            mButton_list.remove(b);
                            numButton = mButton_list.size();


                        }
                    }
                }
                String[] answerQuestion = question.getAnswerStr();
                for (int i = 0; i < mButton_list.size(); i++) {
                    mButton_list.get(i).setText(answerQuestion[i]);
                }
            } else {
                nextQuestion();
            }
        } else {
            end_question();
        }
        question_end = false;

    }

    private void end_question() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Good Job!")
                .setMessage("Your score is : " + numberCorrectAnswer)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        player.setScore(numberCorrectAnswer);
                        intent.putExtra(MainActivity.PLAYER_STRING, gson.toJson(player));
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }).setCancelable(false).create().show();


    }

    private void clickMethode(Button b) {
        if (!question_end) {

            if (check_Answer(mButton_list.indexOf(b), num_Question)) {
                b.setBackgroundColor(getColor(R.color.colorCorrectAnswer));
                numberCorrectAnswer++;

            } else {
                b.setBackgroundColor(getColor(R.color.colorWrongAnswer));
                mButton_list.get(question_list.get(num_Question).getNumberAnswer()).setBackgroundColor(getColor(R.color.colorCorrectAnswer));
            }
            question_end = true;
        } else {
            nextQuestion();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (MainActivity.QUIZ_ACTIVITY_SEND_Code == requestCode && RESULT_OK == resultCode) {
            // Fetch the score from the Intent

            player = gson.fromJson(data.getStringExtra(MainActivity.PLAYER_STRING), Player.class);
        }
    }
}
