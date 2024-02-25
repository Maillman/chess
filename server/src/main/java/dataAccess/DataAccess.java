package dataAccess;
import Model.Auth;
import Model.Game;
import Model.User;

import java.util.List;

public interface DataAccess {
    User getUser(String username);

    void createUser(User user);
    Auth createAuth(String authToken, String username);

    Auth getAuth(String authToken);

    void deleteAuth(String authToken);

    List<Game> listGames();

    Game getGame(int gameID);

    Game createGame(Game game);

    void clear();
}
