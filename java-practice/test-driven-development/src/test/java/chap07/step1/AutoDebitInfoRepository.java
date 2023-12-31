package chap07.step1;

interface AutoDebitInfoRepository {
    void save(AutoDebitInfo info);

    AutoDebitInfo findOne(String userId);
}
