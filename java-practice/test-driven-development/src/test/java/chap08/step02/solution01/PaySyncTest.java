package chap08.step02.solution01;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PaySyncTest {
    private PaySync paySync = new PaySync();

    @Test
    void someTest() throws Exception {
        PaySync paySync = new PaySync(new PayInfoDao("name"));
        paySync.sync("src/test/resources/c0111.csv");
        //...
    }

    @Test
    void some_mock_Test() throws Exception {
        PayInfoDao mock = Mockito.mock(PayInfoDao.class);
        PaySync paySync = new PaySync(mock);
        paySync.sync("src/test/resources/c0111.csv");
        //...
        assertThat(paySync).isNotNull();
    }
}
