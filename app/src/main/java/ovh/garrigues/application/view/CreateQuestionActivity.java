package ovh.garrigues.application.view;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ovh.garrigues.application.R;
import ovh.garrigues.application.adapter.ActivityRequest;
import ovh.garrigues.application.adapter.QuestionAdminModifyAdapter;
import ovh.garrigues.application.question.AdminMoodifyPopupWindow;
import ovh.garrigues.application.question.Answer;
import ovh.garrigues.application.question.Question;

public class CreateQuestionActivity extends AppCompatActivity {

    private View mValidButton;
    private View mAboardButton;
    private ListView mListView;
    private TextView mtextQuestion;
    private QuestionAdminModifyAdapter adapter;
    private ImageView mImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_admin);
        initPopupButton();

    }

    private void initPopupButton() {
        mAboardButton = findViewById(R.id.ModifyAdminActivityBack);
        mValidButton = findViewById(R.id.ModifyAdminActivityValid);
        mListView = findViewById(R.id.ModifyAdminListAnswer);
        mtextQuestion = findViewById(R.id.ModifyAdminTextQuestion);
        mImageButton = findViewById(R.id.modifyAdminActivityAddAnswer);
        adapter = new QuestionAdminModifyAdapter(this,new ArrayList<Answer>());
        mListView.setAdapter(adapter);

        mAboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mValidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Question newQuestion;

                boolean b1 = mtextQuestion.getText().length() !=0;
                boolean b2 = adapter.getCheckBoxGroup().getChekedPosition() != -1;
                boolean b3 = adapter.getStringsAnswer().length != 0;
                if (b1 && b2 && b3) {

                    newQuestion = new Question(String.valueOf(mtextQuestion.getText()), adapter.getCheckBoxGroup().getChekedPosition(), adapter.getStringsAnswer());
                    if (!newQuestion.isError()) {
                        AdminActivity.getIstance().addQuestion(newQuestion);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Invalid question ",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "";
                adapter.add(new Answer("",false));
                adapter.notifyDataSetChanged();
            }
        });
    }
}
