package ovh.garrigues.application;

public class Player {
    private String name;
    private int score = -1;
    public Player(String name)
    {
        this.name = name;
    }

    public Player(String name,int score)
    {
        this(name);
        this.score=score;
    }
    public void setScore(int score)
    {
        this.score = score;
    }
    public int getScore()
    {
        return score;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Name : "+name+" Score :"+score;
    }
}
