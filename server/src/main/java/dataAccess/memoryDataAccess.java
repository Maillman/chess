package dataAccess;

import Model.Auth;
import Model.Game;
import Model.User;
import chess.ChessGame;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;
//TODO: Refactor this into several memory DAOs, shouldn't be too hard. Just cut and paste
//TODO: the code into the userDAO, authDAO, and gameDAO. REMEMBER: Single Responsibility!
public class memoryDataAccess implements DataAccess {
    private int gameID = 1;
    final private HashMap<String, User> users = new HashMap<>();
    final private HashMap<String, Auth> auths = new HashMap<>();
    final private HashMap<Integer, Game> games = new HashMap<>();

    @Override
    public User getUser(String username) {
        return users.get(username);
    }

    @Override
    public void createUser(User user) {
        users.put(user.getUsername(), user);
    }

    @Override
    public Auth createAuth(String username) {
        String authToken = UUID.randomUUID().toString();
        Auth auth = new Auth(authToken,username);
        auths.put(authToken, auth);
        return auth;
    }

    @Override
    public Auth getAuth(String authToken) {
        return auths.get(authToken);
    }

    @Override
    public void deleteAuth(String authToken) {
        auths.remove(authToken);
    }

    @Override
    public Game createGame(Game game){
        Game createGame = new Game(gameID++,"","",game.getGameName(), new ChessGame());
        games.put(gameID,createGame);
        return createGame;
    }

    @Override
    public void clear() {
        users.clear();
        auths.clear();
        games.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof memoryDataAccess that)) return false;
        return Objects.equals(users, that.users) && Objects.equals(auths, that.auths) && Objects.equals(games, that.games);
    }

    @Override
    public int hashCode() {
        return Objects.hash(users, auths, games);
    }
}
