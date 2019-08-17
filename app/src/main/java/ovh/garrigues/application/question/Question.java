package ovh.garrigues.application.question;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Question {

    private String question;
    private int numberAnswer;// between 0.3
    private String[] answerStr;

    private transient boolean error = false;
    public Question()
    {
        question = "";
        numberAnswer = -1;
        answerStr=new String[0];
    }

    public ArrayList<String> getArraylistanswer() {
        if (arraylistanswer == null)
            arraylistanswer = new ArrayList<String>(Arrays.asList(answerStr));
        return arraylistanswer;

    }
    public void resetArraylistgetter()
    {
        arraylistanswer = null;
    }

    private  transient ArrayList<String> arraylistanswer;

    public Question(String question, int answer, String[] answerStr) {
        this.question = question;
        this.numberAnswer = answer + 1;
        this.answerStr = answerStr;
        if (numberAnswer > answerStr.length || numberAnswer <= 0) {
            for (int i = 0; i < answerStr.length; i++) {
                answerStr[i] = "ERROR";
            }
            numberAnswer = 1;
            this.question = "ERROR";

            error = true;

        }
    }

    public Question(String question, int answer) {
        this.question = question;
        this.numberAnswer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public int getNumberAnswer() {
        return numberAnswer - 1;
    }

    public String[] getAnswerStr() {
        return answerStr;
    }

    public boolean isError() {
        return error;
    }

    public boolean checkValidity() {
        boolean valid = true;
        if (numberAnswer > answerStr.length || numberAnswer <= 0) {
            /*for(int i =0 ; i < answerStr.length ;i++)
            {
                answerStr[i] = "ERROR";
            }
            numberAnswer = 1;
            this.question = "ERROR";
            error = true;*/
            valid = false;

        }
        return valid;
    }


}