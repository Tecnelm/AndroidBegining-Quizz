package ovh.garrigues.application.view;

import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.zip.Inflater;

import ovh.garrigues.application.R;
import ovh.garrigues.application.adapter.ActivityRequest;
import ovh.garrigues.application.adapter.QuestionAdminAdapter;
import ovh.garrigues.application.adapter.QuestionAdminModifyAdapter;
import ovh.garrigues.application.question.Question;
import ovh.garrigues.application.request.Request;
import ovh.garrigues.application.request.VolleySingleton;

public class AdminActivity extends ActivityRequest {

    private AdminActivity instance;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        instance = this;
        refresh();
        gson = new Gson();


    }

    @Override
    public void changeActiError(Request.EditTypeQuestion typeRequest) {
        switch (typeRequest)
        {
            case GET_QUESTION:Toast.makeText(getApplicationContext(), "Error getting Question", Toast.LENGTH_SHORT).show();break;
            case NO_QUESTION_GET:Toast.makeText(getApplicationContext(), typeRequest.abv, Toast.LENGTH_SHORT).show();
                ListView view = this.findViewById(R.id.recyclerAdmin);
                QuestionAdminAdapter adapter = (QuestionAdminAdapter) view.getAdapter();
                if(adapter !=null)
                {
                    adapter.resetView();
                }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
    public synchronized void changeActiSucess(Request.EditTypeQuestion typeRequest) {

        switch (typeRequest) {
            case GET_QUESTION:
                setUpQuestionInView();
                break;
            case ADD:
            case MODIFY:
            case DELETE:
                this.refresh();
                Toast.makeText(getApplicationContext(), "Question successful" + typeRequest.abv, Toast.LENGTH_SHORT).show();
                break;
        }


    }

    private void setUpQuestionInView() {
        ArrayList<Question> questionlist = Request.getInstance().getQuestion();
        if (questionlist != null) {
            ListView view = this.findViewById(R.id.recyclerAdmin);
            QuestionAdminAdapter questionAdminAdapter = new QuestionAdminAdapter(this, questionlist);
            QuestionAdminModifyAdapter qes = new QuestionAdminModifyAdapter(this,questionlist.get(0));
            //view.setAdapter(questionAdminAdapter);
            view.setAdapter(qes);

            view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, long id) {
                    final Question questionClick = (Question) parent.getAdapter().getItem(position);

                    //final Question questionOld = (Question) parent.getAdapter().getItem(position + 1);
                    //final Question[] questionsClick = {questionOld, questionClick};

                    PopupMenu popup = new PopupMenu(getApplicationContext(), view);
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
                    popup.getMenuInflater().inflate(R.menu.pop_admin_ask_action, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.admin_ask_delete:
                                    deleteQuestion(questionClick);
                                    break;

                                case R.id.admin_ask_modify:
                                      modifyQuestion(questionClick,view);
                                    break;

                            }
                            return false;

                        }
                    });
                    popup.show();


                    return false;
                }
            });

        }
    }

    private void deleteQuestion(Question questionClick) {
        QuestionRequestEdit(Request.EditTypeQuestion.DELETE, gson.toJson(questionClick));
    }

    private void modifyQuestion(Question questionsClick,View vf) {
        LayoutInflater inflater = getLayoutInflater();


        View v =inflater.inflate(R.layout.activity_modify_admin,null);

        PopupWindow popupWindow = new PopupWindow(v, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setElevation(5.0f);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(findViewById(R.id.mainLayoutActivityAdmin), Gravity.CENTER,0,0);
        //popupWindow.showAtLocation(vf, Gravity.CENTER,0,0);


        //QuestionRequestEdit(Request.EditTypeQuestion.MODIFY, gson.toJson(questionsClick));

    }

    private void addQuestion(Question questionClick) {
        QuestionRequestEdit(Request.EditTypeQuestion.ADD, gson.toJson(questionClick));
    }

    private void QuestionRequestEdit(Request.EditTypeQuestion type, String encodedString) {

        new Request(getApplicationContext(), VolleySingleton.getInstance(getApplicationContext()).getRequestQueue()).QuestionRequestEdit(this, type, encodedString);
    }


    @Override
    public ActivityRequest getInstance() {
        return instance;
    }


}
