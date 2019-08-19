package ovh.garrigues.application.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import ovh.garrigues.application.R;
import ovh.garrigues.application.question.Player;

public class DynamicView {
    private Context mContext ;
    public DynamicView(Context context)
    {
        mContext = context;
    }
    public  Button create_Button(LinearLayout lay,LinearLayout.LayoutParams params)
    {
        Button b = new Button(mContext);
        b.setBackgroundColor(mContext.getColor(R.color.colorBackgroundQuestion));
        b.setTextSize(20);
        b.setGravity(Gravity.CENTER);
        b.setLayoutParams(params);
        lay.addView(b);
        return b;
    }
    public Button create_Button(LinearLayout lay)
    {
        LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1.0f);
        int valueLTR =Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,10, mContext.getResources().getDisplayMetrics()));
        int valueB =Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,10, mContext.getResources().getDisplayMetrics()));
        params.setMargins(valueLTR,valueLTR,valueLTR,valueB);

        Button b = create_Button(lay,params);
        b.setLayoutParams(params);
        return b;
    }
    public void createScoreTabble(TableLayout lay, Player p)
    {
        TableRow row = new TableRow(mContext);
        TextView Name = new TextView(mContext);
        TextView Score = new TextView(mContext);

        ShapeDrawable border = new ShapeDrawable(new RectShape());
        border.getPaint().setStyle(Paint.Style.STROKE);
        border.getPaint().setColor(Color.BLACK);
        border.getPaint().setStrokeWidth(7);

        TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1);
        row.setLayoutParams(params);

        Name.setTextSize(20);
        Name.setLayoutParams(params);
        Name.setGravity(Gravity.CENTER);
        Name.setText(p.getName());
        Name.setTextColor(mContext.getColor(R.color.colorAnswer));
        Name.setBackgroundColor(mContext.getColor(R.color.colorScroll));


       // Name.setBackground(border);


       Score.setTextSize(20);
       Score.setLayoutParams(params);
       Score.setGravity(Gravity.CENTER);
       Score.setText(""+p.getScore());
       Score.setBackgroundColor(mContext.getColor(R.color.colorScroll));
        //Score.setBackground(border);
       row.addView(Name);
       row.addView(Score);
       lay.addView(row);
    }
}
