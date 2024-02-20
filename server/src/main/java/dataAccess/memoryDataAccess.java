package dataAccess;

import Model.User;

import java.util.HashMap;

public class memoryDataAccess implements DataAccess {
    final private HashMap<String, User> users = new HashMap<>();

    @Override
    public User getUser(String username) {
        return users.get(username);
    }
}
