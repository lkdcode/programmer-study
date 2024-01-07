package practice.baseball;

import java.util.ArrayList;
import java.util.List;

public class Computer {

    public BaseBallNumbers getNumbers() {
        final List<Integer> numbers = new ArrayList<>();

        while (numbers.size() != 3) {
            final int number = RandomIntegerGenerator.getNumber();
            if (!numbers.contains(number)) {
                numbers.add(number);
            }
        }

        return new BaseBallNumbers(numbers);
    }

}
