package service;

import Model.User;
import dataAccess.DataAccess;

public class UserService {
    private final DataAccess dataAccess;
    public UserService(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }
    public User registerUser(User user) {
        String username = user.Username();
        user = dataAccess.getUser(username);
        return user;
    }
}
