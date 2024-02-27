package service;

import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;

public class ClearService {
    //private final DataAccess dataAccess;
    private final UserDAO userDAO;
    private final AuthDAO authDAO;
    private final GameDAO gameDAO;
    /*public ClearService(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }
     */
    public ClearService(UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    public void clear(){
        userDAO.clear();
        authDAO.clear();
        gameDAO.clear();
    }
}
