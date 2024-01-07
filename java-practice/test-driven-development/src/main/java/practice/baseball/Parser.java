package practice.baseball;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Parser {

    public BaseBallNumbers parseInt(final String str) {
        Objects.requireNonNull(str);
        final char[] chars = str.toCharArray();

        return convert(chars);
    }

    private BaseBallNumbers convert(final char[] chars) {
        final List<Integer> list = new ArrayList<>();
        for (char aChar : chars) {
            list.add(Character.getNumericValue(aChar));
        }

        return new BaseBallNumbers(list);
    }

}
