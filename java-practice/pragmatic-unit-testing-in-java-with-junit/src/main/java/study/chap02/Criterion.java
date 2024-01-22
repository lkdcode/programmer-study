package study.chap02;

public class Criterion implements Scoreable {
    private final Weight weight;
    private final Answer answer;
    private int score;

    public Criterion(Weight weight, Answer answer) {
        this.weight = weight;
        this.answer = answer;
    }

    public Weight getWeight() {
        return weight;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int getScore() {
        return score;
    }
}
