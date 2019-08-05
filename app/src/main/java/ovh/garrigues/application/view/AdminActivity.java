package ovh.garrigues.application.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import ovh.garrigues.application.R;
import ovh.garrigues.application.question.Player;
import ovh.garrigues.application.question.Question;
import ovh.garrigues.application.request.Request;
import ovh.garrigues.application.request.VolleySingleton;

public class AdminActivity extends AppCompatActivity implements activityInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
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
        new Request(this, VolleySingleton.getInstance(this).getRequestQueue());
    }

    @Override
    public void changeActiSucess() {
        ArrayList<Question> questionlist = Request.getInstance().getQuestion();

    }

    @Override
    public void changeActiError() {

        Toast.makeText(getApplicationContext(), "Error getting Question", Toast.LENGTH_SHORT).show();
    }

}
