package chap08.step02.solution02;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class DailyBatchLoaderTest {
    private Times mockTimes = Mockito.mock(Times.class);
    private final DailyBatchLoader loader = new DailyBatchLoader();

    @BeforeEach
    void setUp() {
        this.loader.setBasePath("src/test/resources");
        this.loader.setTimes(mockTimes);
    }

    @Test
    void loadCount() {
        BDDMockito.given(mockTimes.today()).willReturn(LocalDate.of(2019, 1, 1));
        final int ret = loader.load();

        assertThat(ret).isEqualTo(3);
    }

}
