package service;

import Model.Auth;
import Model.User;
import dataAccess.*;

import java.util.Objects;
import java.util.UUID;


public class UserService {
    private final UserDAO userDAO;
    private final AuthDAO authDAO;

    public UserService(UserDAO userDAO, AuthDAO authDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
    }

    public Auth registerUser(User user) throws DataAccessException {
        if(user.getUsername()==null||user.getPassword()==null||user.getEmail()==null){
            throw new DataAccessException("Bad Request!");
        }
        String username = user.getUsername();
        if(userDAO.getUser(username)==null){
            userDAO.createUser(user);
            return createNewAuth(username);
        }else {
            throw new DataAccessException("Already Taken!");
        }
    }

    public Auth login(User user) throws DataAccessException {
        String username = user.getUsername();
        User getUser = userDAO.getUser(username);
        if((getUser!=null)&&(Objects.equals(user.getPassword(), getUser.getPassword()))){
            return createNewAuth(username);
        }else {
            throw new DataAccessException("Unauthorized!");
        }
    }

    private Auth createNewAuth(String username) {
        String authToken = UUID.randomUUID().toString();
        return authDAO.createAuth(authToken, username);
    }

    public void logout(String authToken) throws DataAccessException {
        if(authDAO.getAuth(authToken)!=null){
            authDAO.deleteAuth(authToken);
        }else{
            throw new DataAccessException("Unauthorized!");
        }
    }
}
