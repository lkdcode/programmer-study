package chap10;

import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

@EnabledOnOs({OS.AIX, OS.MAC, OS.LINUX, OS.WINDOWS, OS.SOLARIS, OS.OTHER})
public class OsTest {
}
