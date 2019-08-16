package ovh.garrigues.application.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import ovh.garrigues.application.R;

public class ModifyAdminActivity extends PopupWindow {
    private View mValidButton;
    private View mAboardButton;
    private ListView mListView;
    private TextView mtextQuestion;

    public ModifyAdminActivity(Context context) {
        super(context);
    }

    public ModifyAdminActivity(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ModifyAdminActivity(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ModifyAdminActivity(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public ModifyAdminActivity() {
    }

    public ModifyAdminActivity(View contentView) {
        super(contentView);
    }

    public ModifyAdminActivity(int width, int height) {
        super(width, height);
    }

    public ModifyAdminActivity(View contentView, int width, int height) {
        super(contentView, width, height);
    }

    public ModifyAdminActivity(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
    }

    private void initPopupButton(View contentView)
    {
        mAboardButton = contentView.findViewById(R.id.ModifyAdminActivityBack);
        mValidButton = contentView.findViewById(R.id.ModifyAdminActivityValid);
        mListView = contentView.findViewById(R.id.ModifyAdminListAnswer);
        mtextQuestion=contentView.findViewById(R.id.ModifyAdminTextQuestion);
    }
}
