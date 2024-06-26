package service;

import Model.Auth;
import Model.User;
import dataAccess.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String hashedPassword = encoder.encode(user.getPassword());
            User hashedUser = new User(user.getUsername(), hashedPassword, user.getEmail());
            userDAO.createUser(hashedUser);
            return createNewAuth(username);
        }else {
            throw new DataAccessException("Already Taken!");
        }
    }

    public Auth login(User user) throws DataAccessException {
        String username = user.getUsername();
        User getUser = userDAO.getUser(username);
        if(getUser!=null) {
            var hashedPassword = getUser.getPassword();
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if(encoder.matches(user.getPassword(), hashedPassword)){
                return createNewAuth(username);
            }
        }
        throw new DataAccessException("Unauthorized!");
    }

    private Auth createNewAuth(String username) throws DataAccessException {
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

    public Auth verifyUser(String authToken) throws DataAccessException{
        return authDAO.getAuth(authToken);
    }
}
