package ovh.garrigues.application;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class Question
{
    private String question;
    private int numberAnswer;// between 0.3
    private String answerStr[];
    private boolean error;

    public Question(String question , int answer, String []answerStr)
    {
        this.question = question;
        this.numberAnswer = answer-1;
        this.answerStr = answerStr;
        error = false;
        if(numberAnswer >= answerStr.length ||numberAnswer <0)
        {
            for(int i =0 ; i < answerStr.length ;i++)
            {
                answerStr[i] = "ERROR";
            }
            numberAnswer = 0;
            this.question = "ERROR";
            error = true;
        }
    }
    public Question(String question , int answer )
    {
        this.question = question;
        this.numberAnswer = answer;
    }
    public String getQuestion()
    {
        return question;
    }
    public int getNumberAnswer()
    {
        return numberAnswer;
    }

    public String[] getAnswerStr() {
        return answerStr;
    }
    public boolean isError() {
        return error;
    }



}