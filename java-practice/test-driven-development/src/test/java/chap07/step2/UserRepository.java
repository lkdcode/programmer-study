package chap07.step2;

interface UserRepository {
    void save(User user);

    User findById(String id);
}
