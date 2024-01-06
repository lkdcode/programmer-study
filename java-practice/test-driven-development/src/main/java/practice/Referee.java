package practice;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Referee {
    private final AtomicInteger strickeCount = new AtomicInteger();
    private final AtomicInteger ballCount = new AtomicInteger();

    public GameResult makeJudgment(List<Integer> computerNumbers, List<Integer> userNumbers) {
        IntStream.rangeClosed(0, computerNumbers.size() - 1)
                .forEach(index -> {
                    if (Objects.equals(computerNumbers.get(index), userNumbers.get(index))) {
                        strickeCount.getAndIncrement();
                    } else {
                        if (computerNumbers.contains(userNumbers.get(index))) {
                            ballCount.getAndIncrement();
                        }
                    }
                });

        return new GameResult(strickeCount.get(), ballCount.get());
    }
}
