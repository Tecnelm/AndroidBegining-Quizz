package ovh.garrigues.application.request;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import ovh.garrigues.application.adapter.ActivityRequest;
import ovh.garrigues.application.question.Question;

public class Request {

    private Context context;
    private RequestQueue requestQueue;
    private final String url = "http://garrigues.ovh";
    private ArrayList<Question> questiontab;
    private static Request instance;

    public Request(Context context, RequestQueue requestQueue) {
        this.context = context;
        this.requestQueue = requestQueue;
        instance = this;
    }

    public static synchronized Request getInstance() {


        return instance;
    }

    public void getQuestionArray(ActivityRequest ac) {
        final ActivityRequest act = ac;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(JsonArrayRequest.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                parseQuestionGet(act,response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.getInstance().changeActiError();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    public void deleteQuestion(ActivityRequest ac, final Question question)
    {
        final ActivityRequest act  = ac;
        Gson gson = new Gson();
       final String encodedpost = gson.toJson(question);
        StringRequest request = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              String res = response.substring(1);
                if (res.equals("OK"))
                {

                    act.changeActiSucessPost();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return encodedpost.getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        requestQueue.add(request);
    }

    public ArrayList<Question> getQuestion() {

        return questiontab;
    }

    public void resetQuestion() {
        questiontab = null;
    }
    private void parseQuestionGet(ActivityRequest act,JSONArray response){
        Gson gson = new Gson();
        questiontab = new ArrayList<>();
        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject object = response.getJSONObject(i);

                String str = object.toString().replace("\\", "").replace(":\"[", ":[").replace("]\"}", "]}");
                Question quest = gson.fromJson(str, Question.class);
                if (quest.checkValidity())
                    questiontab.add(quest);
            } catch (Exception e) {

            }
        }

        if (questiontab.size() == 0)
            act.changeActiError();
        else
            act.getInstance().changeActiSucess();

    }

}
