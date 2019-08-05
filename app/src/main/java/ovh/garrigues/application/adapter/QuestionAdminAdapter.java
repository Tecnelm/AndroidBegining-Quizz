package ovh.garrigues.application.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ovh.garrigues.application.R;
import ovh.garrigues.application.question.Question;

public class QuestionAdminAdapter extends BaseAdapter {
    private ArrayList<Question> question_list;
    private LayoutInflater inflater;

    public QuestionAdminAdapter(Context context, ArrayList<Question> question_list) {
        this.question_list = question_list;
        this.inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return question_list.size();
    }

    @Override
    public Object getItem(int position) {
        return question_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null)
        {
            holder = new ViewHolder();
             convertView =  inflater.inflate(R.layout.layout_list_question,null);
             holder.textanswer = convertView.findViewById(R.id.correctanswerText);
             holder.textnumberAnwer = convertView.findViewById(R.id.numberAnswertext);
             holder.textQuestion = convertView.findViewById(R.id.questionTagText);
        }
        else
        {
            holder =(ViewHolder) convertView.getTag();
        }

        Question q = question_list.get(position);
        holder.textQuestion.setText(q.getQuestion());
        holder.textanswer.setText(q.getNumberAnswer());
        holder.textnumberAnwer.setText(q.getAnswerStr().length);



        return convertView;
    }
    private class ViewHolder{
        TextView textQuestion;
        TextView textanswer;
        TextView textnumberAnwer;
    }

}
