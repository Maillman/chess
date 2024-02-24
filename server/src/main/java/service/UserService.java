package service;

import Model.Auth;
import Model.User;
import dataAccess.DataAccess;
import dataAccess.DataAccessException;

import java.util.Objects;


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

    public Auth login(User user) throws DataAccessException {
        String username = user.getUsername();
        User getUser = dataAccess.getUser(username);
        if((getUser!=null)&&(Objects.equals(user.getPassword(), getUser.getPassword()))){
            return dataAccess.createAuth(username);
        }else {
            throw new DataAccessException("Unauthorized!");
        }
    }

    public void logout(String authToken) {
        if(dataAccess.getAuth(authToken)!=null){
            dataAccess.deleteAuth(authToken);
        }
    }
}
