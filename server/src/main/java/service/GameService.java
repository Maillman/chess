package service;

import Model.Auth;
import Model.User;
import Model.Game;
import dataAccess.DataAccess;
import dataAccess.DataAccessException;

import java.util.List;

public class GameService {
    private final DataAccess dataAccess;
    public GameService(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }

    public Game createGame(String authToken, Game game) throws DataAccessException {
        checkAuthToken(authToken);
        return dataAccess.createGame(game);
    }

    public List<Game> listGames(String authToken) throws DataAccessException {
        checkAuthToken(authToken);
        List<Game> list = dataAccess.listGames();
        return list;
    }

    private void checkAuthToken(String authToken) throws DataAccessException {
        if(dataAccess.getAuth(authToken)==null){
            throw new DataAccessException("Unauthorized!");
        }
    }
}
