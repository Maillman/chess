package service;

import Model.Auth;
import Model.User;
import dataAccess.DataAccess;
import dataAccess.DataAccessException;


public class UserService {
    private final DataAccess dataAccess;
    public UserService(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }
    public Auth registerUser(User user) throws DataAccessException {
        String username = user.getUsername();
        if(dataAccess.getUser(username)==null){
            dataAccess.createUser(user);
            return dataAccess.createAuth(username);
        }else {
            throw new DataAccessException("Username already taken!");
        }
    }

    public void logout(String authToken) {
        if(dataAccess.getAuth(authToken)!=null){
            dataAccess.deleteAuth(authToken);
        }
    }
}
