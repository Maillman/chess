package service;

import Model.Auth;
import Model.Join;
import Model.User;
import Model.Game;
import dataAccess.*;

import java.util.List;
import java.util.Objects;

public class GameService {
    private final AuthDAO authDAO;
    private final GameDAO gameDAO;
    public GameService(AuthDAO authDAO, GameDAO gameDAO) {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    public Game createGame(String authToken, Game game) throws DataAccessException {
        checkAuthToken(authToken);
        return gameDAO.createGame(game);
    }

    public void joinGame(String authToken, Join join) throws DataAccessException {
        Auth auth = authDAO.getAuth(authToken);
        if(auth!=null){
            Game foundGame = gameDAO.getGame(join.getGameID());
            if(foundGame!=null){
                if(     ((Objects.equals(join.getPlayerColor(), "WHITE") && ((Objects.equals(foundGame.getWhiteUsername(), ""))||foundGame.getWhiteUsername()==null))) ||
                        ((Objects.equals(join.getPlayerColor(), "BLACK") && ((Objects.equals(foundGame.getBlackUsername(), ""))||foundGame.getBlackUsername()==null))) ||
                        ((!Objects.equals(join.getPlayerColor(),"WHITE") && !Objects.equals(join.getPlayerColor(),"BLACK")))){
                    gameDAO.updateGame(auth.getUsername(),foundGame.getGameID(),join.getPlayerColor(),foundGame);
                }else{
                    throw new DataAccessException("Already Taken!");
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
        return gameDAO.listGames();
    }

    private void checkAuthToken(String authToken) throws DataAccessException {
        if(authDAO.getAuth(authToken)==null){
            throw new DataAccessException("Unauthorized!");
        }
    }
}
