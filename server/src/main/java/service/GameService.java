package service;

import Model.Auth;
import Model.Join;
import Model.User;
import Model.Game;
import dataAccess.DataAccess;
import dataAccess.DataAccessException;

import java.util.List;
import java.util.Objects;

public class GameService {
    private final DataAccess dataAccess;
    public GameService(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }

    public Game createGame(String authToken, Game game) throws DataAccessException {
        checkAuthToken(authToken);
        return dataAccess.createGame(game);
    }

    public void joinGame(String authToken, Join join) throws DataAccessException {
        Auth auth = dataAccess.getAuth(authToken);
        if(auth!=null){
            Game foundGame = dataAccess.getGame(join.getGameID());
            if(foundGame!=null){
                if(     ((Objects.equals(join.getPlayerColor(), "WHITE") && !Objects.equals(foundGame.getWhiteUsername(), "WHITE"))) ||
                        ((Objects.equals(join.getPlayerColor(), "BLACK") && !Objects.equals(foundGame.getBlackUsername(), "BLACK"))) ||
                        ((!Objects.equals(join.getPlayerColor(),"WHITE") && Objects.equals(join.getPlayerColor(),"BLACK")))){
                    dataAccess.updateGame(auth.getUsername(),foundGame.getGameID(),join.getPlayerColor(),foundGame);
                }else{
                    throw new DataAccessException("Color is already taken!");
                }
            }else{
                throw new DataAccessException("Bad Request!");
            }
        }else{
            throw new DataAccessException("Unauthorized!");
        }
    }

    public List<Game> listGames(String authToken) throws DataAccessException {
        checkAuthToken(authToken);
        return dataAccess.listGames();
    }

    private void checkAuthToken(String authToken) throws DataAccessException {
        if(dataAccess.getAuth(authToken)==null){
            throw new DataAccessException("Unauthorized!");
        }
    }
}
