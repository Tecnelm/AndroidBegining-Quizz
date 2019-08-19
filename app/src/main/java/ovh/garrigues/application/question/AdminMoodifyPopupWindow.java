package ovh.garrigues.application.question;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import ovh.garrigues.application.R;
import ovh.garrigues.application.adapter.QuestionAdminModifyAdapter;
import ovh.garrigues.application.view.AdminActivity;

public class AdminMoodifyPopupWindow extends PopupWindow {
    private View mValidButton;
    private View mAboardButton;
    private ListView mListView;
    private TextView mtextQuestion;
    private Question question;
    private Context context;
    private QuestionAdminModifyAdapter adapter;
    private ImageView mImageButton;
    private AppCompatActivity ac;




    public AdminMoodifyPopupWindow(AdminActivity ac, View contentView, int width, int height, Question question) {
        super(contentView, width, height);
        this.context = ac.getApplicationContext();
        this.ac=ac;
        setFocusable(true);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        initPopupButton(contentView, ac);
        setQuestion(question);
    }


    private void initPopupButton(final View contentView, final AdminActivity ac) {
        mAboardButton = contentView.findViewById(R.id.ModifyAdminActivityBack);
        mValidButton = contentView.findViewById(R.id.ModifyAdminActivityValid);
        mListView = contentView.findViewById(R.id.ModifyAdminListAnswer);
        mtextQuestion = contentView.findViewById(R.id.ModifyAdminTextQuestion);
        mImageButton = contentView.findViewById(R.id.modifyAdminActivityAddAnswer);
        mtextQuestion.setShowSoftInputOnFocus(true);


        this.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        mAboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminMoodifyPopupWindow.this.dismiss();
            }
        });
        mValidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Question newQuestion;

                boolean b1 = !question.getQuestion().equals(String.valueOf(mtextQuestion.getText()));
                boolean b2 = question.getNumberAnswer() != adapter.getCheckBoxGroup().getChekedPosition();
                boolean b3 = !question.getAnswerStr().equals(adapter.getStringsAnswer());
                if (b1 || b2 || b3) {

                        newQuestion = new Question(String.valueOf(mtextQuestion.getText()),
                                adapter.getCheckBoxGroup().getChekedPosition(),
                                adapter.getStringsAnswer());

                        if (!newQuestion.isError()) {
                            Question[] questions = {question, newQuestion};
                            ac.modifyQuestion(questions);
                        }
                }
                AdminMoodifyPopupWindow.this.dismiss();
            }
        });

        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(adapter.getCount() <6)
                {
                    String str = "";
                    adapter.add(new Answer(str,false));
                    adapter.notifyDataSetChanged();

                }
                else
                {
                    Toast.makeText(context,"ERROR TOO MANY QUESTION",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setQuestion(Question q) {

        this.question = q;
        if (q != null) {
            QuestionAdminModifyAdapter adapter = new QuestionAdminModifyAdapter(context, question,ac);
            this.adapter = adapter;
            mListView.setAdapter(adapter);
            mtextQuestion.setText(q.getQuestion());
        }

    }

}
