package ovh.garrigues.application.question;

public class Answer {

    public boolean rightAnswer;
    String content;
    public Answer(String content, boolean correct)
    {
        this.content = content;
        this.rightAnswer = correct;
    }

    @Override
    public String toString() {
        return content;
    }
    public void setText(String str)
    {
        content = str;
    }
    public void setCorrect(boolean bool)
    {
        this.rightAnswer = bool;
    }
}
