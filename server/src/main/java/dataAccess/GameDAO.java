package dataAccess;

import Model.Game;

import java.util.List;

public interface GameDAO {
    List<Game> listGames();

    Game getGame(int gameID);

    Game createGame(Game game);

    Game updateGame(String username, Integer gameID, String playerColor, Game upGame);

    void clear();
}
