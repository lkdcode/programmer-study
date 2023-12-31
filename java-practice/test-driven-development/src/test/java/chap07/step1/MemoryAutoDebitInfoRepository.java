package chap07.step1;

import java.util.HashMap;
import java.util.Map;

class MemoryAutoDebitInfoRepository implements AutoDebitInfoRepository {
    private Map<String, AutoDebitInfo> infos = new HashMap<>();


    @Override
    public void save(AutoDebitInfo info) {
        this.infos.put(info.getUserId(), info);
    }

    @Override
    public AutoDebitInfo findOne(String userId) {
        return this.infos.get(userId);
    }
}
