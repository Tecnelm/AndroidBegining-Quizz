package ovh.garrigues.application.question;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.nio.channels.AlreadyBoundException;
import java.util.ArrayList;

import ovh.garrigues.application.R;

public class AlertDialogBuilderModifyAnswer extends AlertDialog.Builder {
    private ImageButton mValidButton;
    private EditText mEditText;
    private Context context;
    private Answer ans ;
    private final TextView text;
    private final View content;
    private AlertDialog dialog;



    public AlertDialogBuilderModifyAnswer(Context context, TextView text, View contentView, Answer ans) {
        super(context);
        this.context = context;
        this.ans=ans;
        this.content = contentView;
        this.text = text;
        configure();
        this.setView(contentView);
        dialog = this.create();
    }

    public void configure() {

        mValidButton = content.findViewById(R.id.buttonImageValidnewAnswer);
        mEditText = content.findViewById(R.id.EditTextViewModifyAnswer);

        mEditText.setText(text.getText());

            mValidButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    text.setText(mEditText.getText());
                    ans.setText(String.valueOf(mEditText.getText()));
                    dialog.dismiss();
                }
            });

    }

    public AlertDialog getDialog() {
        return dialog;
    }
}
