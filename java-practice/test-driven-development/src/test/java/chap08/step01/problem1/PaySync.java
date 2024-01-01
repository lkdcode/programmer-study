package chap08.step01.problem1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class PaySync {
    private PayInfoDao payInfoDao = new PayInfoDao();

    public void sync() throws IOException {
        final Path path = Paths.get("D:\\data\\pay\\cp0001.scv");
        final List<PayInfo> payInfoList = Files.lines(path)
                .map(line -> {
                    final String[] data = line.split(",");
                    final PayInfo payInfo = new PayInfo(
                            data[0], data[1], Integer.parseInt(data[2])
                    );
                    return payInfo;
                })
                .toList();

        payInfoList.forEach(pi -> payInfoDao.insert(pi));
    }
}
