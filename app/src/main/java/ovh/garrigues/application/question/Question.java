package ovh.garrigues.application.question;

import java.util.ArrayList;

public class Question {

    private String question;
    private int numberAnswer;// between 0.3
    private String[] answerStr;


    private transient ArrayList<Answer> ansArray;
    private transient boolean error = false;
    public Question()
    {
        question = "";
        numberAnswer = -1;
        answerStr=new String[0];
    }

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
            valid = false;

        }
        return valid;
    }

    /**
     * convert the array string answer into array list of answer for the  adapter
     * @return
     */
    public ArrayList<Answer>convertAnswer()
    {
        if (ansArray == null)
        {
            ansArray= new ArrayList<>();
            for(int i = 0 ; i<answerStr.length ; i++)
            {
                ansArray.add(new Answer(answerStr[i],i==getNumberAnswer()));
            }
        }
        return ansArray;
    }
    public void resetArray(){
        ansArray = null;
    }


}