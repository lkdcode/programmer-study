package chap01;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringTest {
    @Test
    void substring() {
        String str = "abcde";
        Assertions.assertEquals("cd", str.substring(2, 4));
    }
}
