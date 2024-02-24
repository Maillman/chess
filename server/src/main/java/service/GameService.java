package service;

import Model.Auth;
import Model.User;
import Model.Game;
import dataAccess.DataAccess;
import dataAccess.DataAccessException;
public class GameService {
    private final DataAccess dataAccess;
    public GameService(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }

    public Game createGame(String authToken, Game game) throws DataAccessException {
        if(dataAccess.getAuth(authToken)!=null){
            return dataAccess.createGame(game);
        }else{
            throw new DataAccessException("Unauthorized!");
        }
    }
}
