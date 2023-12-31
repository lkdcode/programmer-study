package chap07.step1;

class StubAutoDebitInfoRepository implements AutoDebitInfoRepository {
    @Override
    public AutoDebitInfo findOne(String userName) {
        return null;
    }

    @Override
    public void save(AutoDebitInfo newInfo) {

    }
}
