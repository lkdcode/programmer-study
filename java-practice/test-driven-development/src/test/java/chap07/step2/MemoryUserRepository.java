package chap07.step2;


import java.util.HashMap;
import java.util.Map;

class MemoryUserRepository implements UserRepository {
    private Map<String, User> users = new HashMap<>();

    @Override
    public void save(User user) {
        this.users.put(user.getId(), user);
    }

    @Override
    public User findById(String id) {
        return this.users.get(id);
    }
}
