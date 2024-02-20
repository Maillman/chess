package dataAccess;

import Model.Auth;
import Model.User;

import java.util.HashMap;
import java.util.UUID;

public class memoryDataAccess implements DataAccess {
    final private HashMap<String, User> users = new HashMap<>();
    final private HashMap<String, Auth> auths = new HashMap<>();

    @Override
    public User getUser(String username) {
        return users.get(username);
    }

    @Override
    public void createUser(User user) {
        users.put(user.getUsername(), user);
    }

    @Override
    public Auth createAuth(String username) {
        String authToken = UUID.randomUUID().toString();
        Auth auth = new Auth(authToken,username);
        auths.put(authToken, auth);
        return auth;
    }

}
