package chap08.step02.solution02;

import java.time.LocalDate;

class Subscription {
    private LocalDate localDate;
    private Grade grade;

    public Subscription(LocalDate localDate, Grade grade) {
        this.localDate = localDate;
        this.grade = grade;
    }

    public String getProductId() {
        return null;
    }

    public boolean isFinished(LocalDate now) {
        return false;
    }

    public Grade getGrade() {
        return Grade.GOLD;
    }
}
