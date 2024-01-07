package practice.baseball;

import java.security.SecureRandom;

public class RandomIntegerGenerator {
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public static int getNumber() {
        return SECURE_RANDOM.nextInt(10);
    }

}
