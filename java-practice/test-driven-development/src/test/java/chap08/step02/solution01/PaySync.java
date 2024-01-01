package chap08.step02.solution01;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.*;

class PaySync {

    private String filePath = "D:\\data\\pay\\cp0001.csv";
    private PayInfoDao payInfoDao;

    public PaySync() {

    }

    public PaySync(PayInfoDao payInfoDao) {
        this.payInfoDao = payInfoDao;
    }

    public void setPayInfoDao(PayInfoDao payInfoDao) {
        this.payInfoDao = payInfoDao;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void sync() throws IOException {
        final Path path = Paths.get(this.filePath);
        //...
    }

    public void sync(String filePath) throws IOException {
        final Path path = Paths.get(filePath);
        //...
    }

}
