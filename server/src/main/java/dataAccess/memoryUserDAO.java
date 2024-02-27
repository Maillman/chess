package dataAccess;

import Model.User;

import java.util.HashMap;
import java.util.Objects;

public class memoryUserDAO implements UserDAO {
    final private HashMap<String, User> users = new HashMap<>();

    @Override
    public User getUser(String username) {
        return users.get(username);
    }

    @Override
    public void createUser(User user) {
        users.put(user.getUsername(), user);
    }
    @Override
    public void clear() {
        users.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof memoryUserDAO that)) return false;
        return Objects.equals(users, that.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(users);
    }
}
