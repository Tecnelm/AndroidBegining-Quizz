package ovh.garrigues.application;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

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

    public void getQuestionArray() {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(JsonArrayRequest.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
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
                    MainActivity.getInstance().changeActiError();
                else
                    MainActivity.getInstance().changeActiSucess();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MainActivity.getInstance().changeActiError();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public ArrayList<Question> getQuestion() {

        return questiontab;
    }

    public void resetQuestion() {
        questiontab = null;
    }

}
