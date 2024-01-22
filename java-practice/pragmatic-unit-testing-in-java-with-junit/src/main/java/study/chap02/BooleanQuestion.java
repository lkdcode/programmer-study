package study.chap02;

public class BooleanQuestion extends Question {
    public BooleanQuestion(String text, int id) {
        super(text, new String[]{"No", "Yes"}, id);
    }

    @Override
    public boolean match(int expected, int actual) {
        return expected == actual;
    }
}
