package ovh.garrigues.application.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.zip.Inflater;

import ovh.garrigues.application.R;
import ovh.garrigues.application.question.CheckBoxGroup;
import ovh.garrigues.application.question.Question;

public class QuestionAdminModifyAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] stringsAnswer;
    private int correctAnswer;
    private LayoutInflater inflater ;
    private CheckBoxGroup checkBoxGroup;
    private ArrayList<String> stringAnswer;


    public QuestionAdminModifyAdapter( Context context, int resource) {
        super(context, resource);
    }

    public QuestionAdminModifyAdapter(Context context, Question q) {
        super(context,0,q.getArraylistanswer());
        stringsAnswer = q.getAnswerStr();
        stringAnswer = q.getArraylistanswer();
        correctAnswer = q.getNumberAnswer();
        this.context = context;
        inflater = LayoutInflater.from(context);
        checkBoxGroup = new CheckBoxGroup();
    }


    public CheckBoxGroup getCheckBoxGroup() {
        return checkBoxGroup;
    }

    public String[] getStringsAnswer() {
        return stringAnswer.toArray(stringsAnswer);
    }

    @Override
    public View getView(int position,  View convertView,ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.question_modify_adapter_layout, null);
            holder.mCheckBox=convertView.findViewById(R.id.ModifyQuestionListAnswerCheckBox);
            holder.mTextQuestion = convertView.findViewById(R.id.ModifyQuestionListAnswerText);
            checkBoxGroup.add(holder.mCheckBox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(correctAnswer ==position)
            holder.mCheckBox.setChecked(true);
        if (position <stringsAnswer.length)
            holder.mTextQuestion.setText( stringsAnswer[position]);
        else
            holder.mTextQuestion.setText( getItem(position));

        return convertView;

    }

    private class ViewHolder
    {
        CheckBox mCheckBox;
        TextView mTextQuestion;
    }
}
