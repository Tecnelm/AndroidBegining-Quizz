package ovh.garrigues.application.question;

import android.graphics.Color;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.function.Consumer;

public class CheckBoxGroup<clearCheck> {

    private ArrayList<CheckBox> checkBoxes;
    private  CompoundButton.OnCheckedChangeListener clickListener;
    public CheckBoxGroup() {
        checkBoxes = new ArrayList<CheckBox>();
        clickListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    clearCheck((CheckBox)buttonView);
            }
        };

    }

    public void add(CheckBox box)
    {
        checkBoxes.add(box);
        box.setOnCheckedChangeListener(clickListener);
    }
    public void remove(CheckBox box)
    {
        checkBoxes.remove(box);
    }

    private void clearCheck( final CheckBox bb)
    {
        checkBoxes.forEach(new Consumer<CheckBox>() {
            @Override
            public void accept(CheckBox box) {
                if(box != bb)
                    box.setChecked(false);
            }
        });
    }
    public CheckBox get(int position)
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
            for (int i = 0 ; i<checkBoxes.size();i++)
            {
                if(isChecked(i)) return i;
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
