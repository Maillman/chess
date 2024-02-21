package service;

import Model.Auth;
import Model.User;
import dataAccess.DataAccess;


public class UserService {
    private final DataAccess dataAccess;
    public UserService(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }
    public Auth registerUser(User user) {
        Auth auth = null;
        String username = user.getUsername();
        if(dataAccess.getUser(username)==null){
            dataAccess.createUser(user);
            auth = dataAccess.createAuth(username);
        }
        return auth;
    }

    public void logout(String authToken) {
        if(dataAccess.getAuth(authToken)!=null){
            dataAccess.deleteAuth(authToken);
        }
    }
}
