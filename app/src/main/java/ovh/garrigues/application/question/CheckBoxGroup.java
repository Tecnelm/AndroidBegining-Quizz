package ovh.garrigues.application.question;

import android.graphics.Color;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.function.Consumer;

public class CheckBoxGroup {

    private ArrayList<CustomCheckBox> checkBoxes;
    private ArrayList<Answer> answers;
    private ArrayAdapter<Answer> adapter;
    private  CompoundButton.OnClickListener clickListener;
    public CheckBoxGroup(ArrayList<Answer> ans, final ArrayAdapter<Answer> adapter) {
        checkBoxes = new ArrayList<>();
        this.adapter = adapter;
        this.answers = ans;
        clickListener = new CompoundButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomCheckBox box = (CustomCheckBox) v;
                clearCheck();
                box.setChecked(true);
                adapter.getItem(box.getId()).setCorrect(true);
            }
        };

    }

    public void add(CustomCheckBox box)
    {
        checkBoxes.add(box);
        box.setOnClickListener(clickListener);
    }
    public void remove(CustomCheckBox box)
    {
        checkBoxes.remove(box);
    }

    private void clearCheck()
    {
        answers.forEach(new Consumer<Answer>() {
            @Override
            public void accept(Answer answer) {
                answer.setCorrect(false);

            }
        });
        for (int i = 0 ; i <checkBoxes.size() ; i ++)
        {
            checkBoxes.get(i).setChecked(false);
        }
        adapter.notifyDataSetChanged();
    }
    public CustomCheckBox get(int position)
    {
        try
        {
            return checkBoxes.get(position);
        }catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
            return null;
        }
    }
    public boolean isChecked(int position)
    {
        return checkBoxes.get(position).isChecked();
    }
    public void clear()
    {
        checkBoxes.clear();
    }
    public int getChekedPosition()
    {
        try
        {
            for (int i = 0 ; i<answers.size();i++)
            {
                if(answers.get(i).rightAnswer) return i;
            }
        }catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();

        }

        return -1;
    }
    public  void setCheck(int position)
    {
        try
        {
            checkBoxes.get(position).setChecked(true);
        }catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();

        }
    }
}
