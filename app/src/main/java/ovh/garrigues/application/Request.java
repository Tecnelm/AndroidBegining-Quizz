package ovh.garrigues.application;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;

public class Request {

    private Context context;
    private RequestQueue requestQueue;
    private final String url="http://garrigues.ovh";
    private Question []questiontab;
    private static Request instance;

    public Request(Context context, RequestQueue requestQueue) {
        this.context = context;
        this.requestQueue = requestQueue;
        instance = this;
    }
    public static synchronized Request getInstance()
    {
        return instance;
    }
    public void getQuestionArray()
    {

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(JsonArrayRequest.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Gson gson = new Gson();

                try {
                    String test = response.toString();
                   test= test.replace("\\","");
                   test = test.replace(":\"[",":[");
                    test = test.replace("]\"}","]}");
                    questiontab = gson.fromJson(test,Question[].class);

                    MainActivity.getInstance().changeActiSucess();

                }catch (Exception e)
                {
                    MainActivity.getInstance().changeActiError();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                questiontab = new Question[0];
                MainActivity.getInstance().changeActiError();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    public ArrayList<Question> getQuestion()
    {
        ArrayList<Question> questionsarray=new ArrayList<>();
        for (int i = 0 ; i< questiontab.length ; i++)
        {
            questionsarray.add(questiontab[i]);
        }
        return questionsarray;
    }
    public void resetQuestion()
    {
        questiontab = new Question[0];
    }

}
