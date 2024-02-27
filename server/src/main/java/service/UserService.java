package service;

import Model.Auth;
import Model.User;
import dataAccess.DataAccess;
import dataAccess.DataAccessException;

import java.util.Objects;
import java.util.UUID;


public class UserService {
    private final DataAccess dataAccess;
    public UserService(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }
    public Auth registerUser(User user) throws DataAccessException {
        if(user.getUsername()==null||user.getPassword()==null||user.getEmail()==null){
            throw new DataAccessException("Bad Request!");
        }
        String username = user.getUsername();
        if(dataAccess.getUser(username)==null){
            dataAccess.createUser(user);
            return createNewAuth(username);
        }else {
            throw new DataAccessException("Already Taken!");
        }
    }

    public Auth login(User user) throws DataAccessException {
        String username = user.getUsername();
        User getUser = dataAccess.getUser(username);
        if((getUser!=null)&&(Objects.equals(user.getPassword(), getUser.getPassword()))){
            return createNewAuth(username);
        }else {
            throw new DataAccessException("Unauthorized!");
        }
    }

    private Auth createNewAuth(String username) {
        String authToken = UUID.randomUUID().toString();
        return dataAccess.createAuth(authToken, username);
    }

    public void logout(String authToken) throws DataAccessException {
        if(dataAccess.getAuth(authToken)!=null){
            dataAccess.deleteAuth(authToken);
        }else{
            throw new DataAccessException("Unauthorized!");
        }
    }
}
