package ovh.garrigues.application.adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import ovh.garrigues.application.R;
import ovh.garrigues.application.question.AlertDialogBuilderModifyAnswer;
import ovh.garrigues.application.question.Answer;
import ovh.garrigues.application.question.CheckBoxGroup;
import ovh.garrigues.application.question.CustomCheckBox;
import ovh.garrigues.application.question.Question;

/**
 * This class is use during the creation and modification of quention
 * this adapter adapte the answer into the view
 */
public class QuestionAdminModifyAdapter extends ArrayAdapter<Answer> {

    private Context context;
    private LayoutInflater inflater;
    private CheckBoxGroup checkBoxGroup;
    private ArrayList<Answer> stringAnswer;
    private AppCompatActivity ac;


    public QuestionAdminModifyAdapter(Context context, Question q, AppCompatActivity ac) {
        super(context, 0, q.convertAnswer());
        stringAnswer = q.convertAnswer();
        this.ac = ac;
        this.context = context;
        inflater = LayoutInflater.from(context);
        q.resetArray();
        checkBoxGroup = new CheckBoxGroup(stringAnswer, this);
    }

    public QuestionAdminModifyAdapter(Context context, ArrayList<Answer> arrayList, AppCompatActivity ac) {
        super(context, 0, arrayList);
        this.ac = ac;
        this.context = context;
        inflater = LayoutInflater.from(context);
        checkBoxGroup = new CheckBoxGroup(arrayList, this);
        stringAnswer = arrayList;
    }


    public CheckBoxGroup getCheckBoxGroup() {
        return checkBoxGroup;
    }

    public String[] getStringsAnswer() {
        String[] answer = new String[stringAnswer.size()];
        for (int i = 0; i < stringAnswer.size(); i++) {
            answer[i] = stringAnswer.get(i).toString();
        }
        return answer;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.question_modify_adapter_layout, null);
            holder.mCheckBox = convertView.findViewById(R.id.ModifyQuestionListAnswerCheckBox);
            holder.mTextAnswer = convertView.findViewById(R.id.ModifyQuestionListAnswerText);
            checkBoxGroup.add(holder.mCheckBox);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mCheckBox.setID(position);
        if (getItem(position).rightAnswer)
            holder.mCheckBox.setChecked(getItem(position).rightAnswer);
        else
            holder.mCheckBox.setChecked(false);
        holder.mTextAnswer.setText(getItem(position).toString());

        holder.mTextAnswer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                PopupMenu popup = new PopupMenu(context, v);
                try {
                    Field[] fields = popup.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        if ("mPopup".equals(field.getName())) {
                            field.setAccessible(true);
                            Object menuPopupHelper = field.get(popup);
                            Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                            Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                            setForceIcons.invoke(menuPopupHelper, true);

                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                popup.getMenuInflater().inflate(R.menu.popupdeleteanswermodifyactivity, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.popupIdActivityModifyDelete:
                                QuestionAdminModifyAdapter.this.remove(getItem(position));
                                QuestionAdminModifyAdapter.this.notifyDataSetChanged();
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
                return false;
            }
        });

        holder.mTextAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(context);

                View view = inflater.inflate(R.layout.modify_answer_windows, null);

                AlertDialogBuilderModifyAnswer builder = new AlertDialogBuilderModifyAnswer(ac, holder.mTextAnswer,
                        view,
                        getItem(position)
                );
                builder.getDialog().show();


            }
        });


        return convertView;

    }

    private class ViewHolder {
        CustomCheckBox mCheckBox;
        TextView mTextAnswer;
    }
}
