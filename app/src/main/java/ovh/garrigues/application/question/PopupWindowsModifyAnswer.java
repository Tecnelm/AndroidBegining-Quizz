package ovh.garrigues.application.question;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

import ovh.garrigues.application.R;

public class PopupWindowsModifyAnswer extends PopupWindow {
    private ImageButton mValidButton;
    private EditText mEditText;
    private Context context;
    private ArrayList<String> adapter;
    private int position;


    public PopupWindowsModifyAnswer(Context context, TextView text, View contentView, ArrayList<String> adapter, int position, int width, int height) {
        super(contentView, width, height);
        this.context = context;
        this.adapter=adapter;
        this.position = position;
        initPopupButton(contentView, text);
    }

    private void initPopupButton(final View contentView, final TextView textView) {

        mValidButton = contentView.findViewById(R.id.buttonImageValidnewAnswer);
        mEditText = contentView.findViewById(R.id.EditTextViewModifyAnswer);

        this.setBackgroundDrawable(new ColorDrawable(Color.WHITE));


        mEditText.setText(textView.getText());

            mValidButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textView.setText(mEditText.getText());
                    adapter.set(position,String.valueOf(mEditText.getText()));
                    dismiss();

                }
            });

    }
}
