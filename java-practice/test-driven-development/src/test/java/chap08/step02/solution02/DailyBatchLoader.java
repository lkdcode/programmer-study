package chap08.step02.solution02;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class DailyBatchLoader {
    private String basePath = ".";
    private Times times = new Times();

    public int load() {
        final LocalDate date = times.today();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        final Path batchPath = Paths.get(basePath, date.format(formatter), "batch.txt");
        //... batchPath 에서 데이터를 읽어와 저장하는 코드
        int result = 3;
        return result;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public void setTimes(Times times) {
        this.times = times;
    }
}
