package ovh.garrigues.application.request;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import ovh.garrigues.application.adapter.ActivityRequest;
import ovh.garrigues.application.question.Question;

public class Request {

    private static Request instance;
    private final String url = "http://garrigues.ovh";
    private Context context;
    private RequestQueue requestQueue;
    private ArrayList<Question> questiontab;

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
                parseQuestionGet(act, response, EditTypeQuestion.GET_QUESTION);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.getInstance().changeActiError(EditTypeQuestion.GET_QUESTION);
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public void QuestionRequestEdit(ActivityRequest ac, final EditTypeQuestion typeRequest, String content) {
        final ActivityRequest act = ac;

        Gson gson = new Gson();
        final String encodedpost = content;
        StringRequest request = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String res = response.substring(1);

                if (res.equals("OK")) {

                    act.changeActiSucess(typeRequest);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                String str = typeRequest.abv + encodedpost;
                return str.getBytes(StandardCharsets.UTF_8);
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

    private void parseQuestionGet(ActivityRequest act, JSONArray response, EditTypeQuestion typeRequest) {
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
            act.changeActiError(EditTypeQuestion.NO_QUESTION_GET);
        else
            act.getInstance().changeActiSucess(typeRequest);

    }

    public enum EditTypeQuestion {
        DELETE("Delete"), ADD("add   "), MODIFY("Modify"), GET_QUESTION(null),

        QUESTION_GET_SUCC("successful getting Question"), NO_QUESTION_GET("No Question Available"),
        DELALL("DelALL");

        public String abv;

        EditTypeQuestion(String abv) {
            this.abv = abv;
        }
    }

}
