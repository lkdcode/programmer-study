package practice.baseball;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class BaseBallNumbers {
    private final List<Integer> numbers;
    private int currentIndex = 0;

    public BaseBallNumbers(List<Integer> numbers) {
        this.numbers = numbers;
    }

    public boolean hasNext() {
        return this.numbers.size() > this.currentIndex;
    }

    public int getNext() {
        if (hasNext()) {
            return this.numbers.get(this.currentIndex++);
        }

        throw new NoSuchElementException();
    }

    public boolean isContains(int number) {
        return this.numbers.contains(number);
    }

    public void resetIndex() {
        this.currentIndex = 0;
    }

    @Override
    public String toString() {
        return "BaseBallNumbers{" +
                "numbers=" + numbers +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseBallNumbers that = (BaseBallNumbers) o;
        return Objects.equals(numbers, that.numbers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numbers);
    }

}
