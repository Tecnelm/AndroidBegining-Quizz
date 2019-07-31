package ovh.garrigues.application;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView mText;
    private Button mButton;
    private EditText mEdit;
    private TableLayout mTableLayout;
    private ProgressBar mProgressBar;
    private Context cont = this;
    private ArrayList<Player> players_list = new ArrayList<>();
    private Gson gson = new Gson();



    private static MainActivity instance;
    public static final int QUIZ_ACTIVITY_REQUEST_Code = 42;
    public static final int QUIZ_ACTIVITY_SEND_Code = 43;
    public static final String PLAYER_STRING = "OBJECT_PLAYER_GSON";
    private static String SAVE_DATA_LIST_KEY = "DATA_LIST_PLAYER";
    private static Player last_player;

    public static MainActivity getInstance() {
        return instance;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_main);
        action();
        onstart();
    }

    private void action()
    {
        mText = findViewById(R.id.T1);
        mButton = findViewById(R.id.B1);
        mEdit = findViewById(R.id.E1);
        mTableLayout = findViewById(R.id.Main_Activity_TabbleLayout);
        mProgressBar = findViewById(R.id.progressBar);

        mButton.setEnabled(false);

        mEdit.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        String str =null;
                        if (String.valueOf(source).equals("\n")) {
                            str = "";
                        }return str;
                    }
                }
        });
        mEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mButton.setEnabled(String.valueOf(s).length() != 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mProgressBar.animate();
               mProgressBar.setVisibility(View.VISIBLE);
               new Request(getApplicationContext(),VolleySingleton.getInstance(getApplicationContext()).getRequestQueue()).getQuestionArray();

            }
        });
    }
    public void changeActiSucess()
    {
        Player p = new Player(mEdit.getText().toString());
        Intent gameActivity = new Intent(MainActivity.this,QuizActivity.class);
        gameActivity.putExtra(PLAYER_STRING,gson.toJson(p));
        mProgressBar.setVisibility(View.GONE);
        startActivityForResult(gameActivity,QUIZ_ACTIVITY_REQUEST_Code);

    }
    public void changeActiError()
    {
        mProgressBar.setVisibility(View.GONE);
        Toast.makeText(getApplicationContext(),"Error getting Question",Toast.LENGTH_SHORT).show();
    }
    private void addPlayer(Player p)
    {
        players_list.add(p);
        new DynamicView(this).createScoreTabble(mTableLayout,p);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (QUIZ_ACTIVITY_REQUEST_Code == requestCode && RESULT_OK == resultCode) {
            last_player =gson.fromJson(data.getStringExtra(PLAYER_STRING),Player.class);
            addPlayer(last_player);
            Player [] p =players_list.toArray(new Player[players_list.size()]);
            saveData(SAVE_DATA_LIST_KEY,p,true);
            mEdit.setText("");
            mText.setText(last_player.toString());
        }
    }
    private void saveData(String ID,Object obj,Boolean reset)
    {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor= preferences.edit();
        if(reset)
            editor.remove(ID);
        editor.putString(ID,gson.toJson(obj));
        editor.apply();
    }
    private Object getDataSave(String ID,Class objectClass)
    {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        Object obj = gson.fromJson(preferences.getString(ID,null),objectClass);

        return obj;
    }
    private void onstart()
    {
        Player[] players= (Player[])getDataSave(SAVE_DATA_LIST_KEY,Player[].class );
        if (players!= null) {


            for (Player p : players) {
                addPlayer(p);
            }
        }
    }


    public void resetScore(View view) {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        final SharedPreferences.Editor editor= preferences.edit();
        new AlertDialog.Builder(cont).setTitle("Reset Tabble Score").setMessage("êtes vous sûr de vouloir reset le tableau de score")
                .setPositiveButton("oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        players_list.clear();
                        editor.remove(SAVE_DATA_LIST_KEY);
                        editor.apply();
                        mTableLayout.removeAllViews();
                        Toast.makeText(cont,"Score reset",Toast.LENGTH_SHORT);
                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();

    }
}
