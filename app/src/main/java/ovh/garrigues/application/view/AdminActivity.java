package ovh.garrigues.application.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import ovh.garrigues.application.R;
import ovh.garrigues.application.adapter.QuestionAdminAdapter;
import ovh.garrigues.application.adapter.activityRequest;
import ovh.garrigues.application.question.Player;
import ovh.garrigues.application.question.Question;
import ovh.garrigues.application.request.Request;
import ovh.garrigues.application.request.VolleySingleton;

public class AdminActivity extends activityRequest {

    private AdminActivity instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        instance = this;
        refresh();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_admin,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.admin_add_menu:
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder
                        .setTitle("Add Question")
                        .setCancelable(false)
                        .setMessage("Will you add a question ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(getApplicationContext(), CreateQuestionActivity.class));
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create().show();

                break;
            case R.id.adminViewRefresh:
                refresh();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refresh() {
        new Request(this, VolleySingleton.getInstance(this).getRequestQueue()).getQuestionArray(this);
    }

    @Override
    public void changeActiSucess() {
        ArrayList<Question> questionlist = Request.getInstance().getQuestion();
        ListView view = this.findViewById(R.id.recyclerAdmin);
        QuestionAdminAdapter questionAdminAdapter = new QuestionAdminAdapter(this,questionlist);
        view.setAdapter(questionAdminAdapter);

        view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //Question questionClick = (Question) ((QuestionAdminAdapter)parent.getAdapter()).getItem(position);
                PopupMenu popup = new PopupMenu(getApplicationContext(), view);

                /*  The below code in try catch is responsible to display icons*/
                try {
                    Field[] fields = popup.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        if ("mPopup".equals(field.getName())) {
                            field.setAccessible(true);
                            Object menuPopupHelper = field.get(popup);
                            Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                            Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                            setForceIcons.invoke(menuPopupHelper, true);
                            popup.getMenuInflater().inflate(R.menu.pop_admin_ask_action,popup.getMenu());
                            popup.show();
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }



                return false;
            }
        });


    }

    @Override
    public activityRequest getInstance() {
        return instance;
    }

    @Override
    public void changeActiError() {

        Toast.makeText(getApplicationContext(), "Error getting Question", Toast.LENGTH_SHORT).show();
    }

}
