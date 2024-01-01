package chap10;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class DateFormatTest {
    private final DateFormat dateFormat = new DateFormat();

    @Test
    void dateFormat_good() {
        LocalDate date = LocalDate.of(1945, 8, 15);
        String dateStr = dateFormat.formatDate(date);
        assertThat(dateStr)
                .isEqualTo("1945년 8월 15일");
    }

    @Test
    void dateFormat_bad() {
        LocalDate date = LocalDate.of(1945, 8, 15);
        String dateStr = dateFormat.formatDate(date);
        assertThat(dateStr)
                .isEqualTo(
                        date.getYear() + "년 " +
                                date.getMonthValue() + "월 " +
                                date.getDayOfMonth() + "일"
                );
    }

}
