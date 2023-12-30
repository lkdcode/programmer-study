package chap06;

import java.io.*;

public class MathUtils {

    public int sum(final File file) {
        try {
            int sum = 0;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String read;
            while (true) {
                read = bufferedReader.readLine();
                if (read == null) return sum;
                sum += Integer.parseInt(read);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }
}
