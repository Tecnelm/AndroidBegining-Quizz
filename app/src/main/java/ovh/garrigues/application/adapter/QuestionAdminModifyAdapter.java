package ovh.garrigues.application.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputType;
import android.text.Layout;
import android.text.method.KeyListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import ovh.garrigues.application.R;
import ovh.garrigues.application.question.AdminMoodifyPopupWindow;
import ovh.garrigues.application.question.CheckBoxGroup;
import ovh.garrigues.application.question.PopupWindowsModifyAnswer;
import ovh.garrigues.application.question.Question;

public class QuestionAdminModifyAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] stringsAnswer;
    private int correctAnswer;
    private LayoutInflater inflater;
    private CheckBoxGroup checkBoxGroup;
    private ArrayList<String> stringAnswer;

    public QuestionAdminModifyAdapter(Context context, int resource) {
        super(context, resource);
    }

    public QuestionAdminModifyAdapter(Context context, Question q) {
        super(context, 0, q.getArraylistanswer());
        stringsAnswer = q.getAnswerStr();
        stringAnswer = q.getArraylistanswer();
        correctAnswer = q.getNumberAnswer();
        this.context = context;
        inflater = LayoutInflater.from(context);
        checkBoxGroup = new CheckBoxGroup();
    }
    public QuestionAdminModifyAdapter(Context context, ArrayList<String> arrayList) {
        super(context, 0, arrayList);

        this.context = context;
        inflater = LayoutInflater.from(context);
        checkBoxGroup = new CheckBoxGroup();
        stringsAnswer = new String[0];
        stringAnswer = arrayList;
    }



    public CheckBoxGroup getCheckBoxGroup() {
        return checkBoxGroup;
    }

    public String[] getStringsAnswer() {
        return stringAnswer.toArray(stringsAnswer);
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
        if (correctAnswer == position)
            holder.mCheckBox.setChecked(true);
        holder.mTextAnswer.setText(getItem(position));

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

                PopupWindowsModifyAnswer popupWindow = new PopupWindowsModifyAnswer(context,holder.mTextAnswer,
                        view,
                        stringAnswer,
                        position
                        , ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setElevation(5.0f);
                popupWindow.setFocusable(true);
                popupWindow.showAsDropDown(v);

            }
        });



        return convertView;

    }

    private class ViewHolder {
        CheckBox mCheckBox;
        TextView mTextAnswer;
    }
}
