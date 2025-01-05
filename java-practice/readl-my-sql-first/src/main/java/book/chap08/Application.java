package book.chap08;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;

public class Application {
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public static void main(String[] args) throws Exception {
        final var file = new File("/Users/kiducklee/IdeaProjects/programmer-study/java-practice/readl-my-sql-first/src/main/resources/city-row.dump");

        try (final var fileWriter = new FileWriter(file);) {
            fileWriter.write("INSERT INTO tb_city_row (city, country) VALUES\n");
            for (int i = 1; i <= 10_000; i++) {
                if (i == 10_000) {
                    fileWriter.write("('" + getCity(i) + "', '" + getCountry(i) + "');");
                    break;
                }

                fileWriter.write("('" + getCity(i) + "', '" + getCountry(i) + "'),\n");
            }
        } catch (IOException ignore) {
            System.err.println(ignore.getMessage());
        }
    }

    private static String getCity(final int i) {
        return "city" + i;
    }

    private static String getCountry(final int i) {
//        final var index = (i % 10 == 0) ? 10 : (i % 10);
        final var index = (i % 1_000 == 0) ? 1_000 : (i % 1_000);
        return "country" + index;
    }
}