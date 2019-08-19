package ovh.garrigues.application.question;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;

/**
 * custom checkbox use to get answer in the adapter
 * this checkBox have an ID
 */
@SuppressLint("AppCompatCustomView")
public class CustomCheckBox extends CheckBox{
    private int ID ;

    public CustomCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setID(int id)
    {
        this.ID=id;
    }

    public int getId()
    {
        return ID;
    }
}
