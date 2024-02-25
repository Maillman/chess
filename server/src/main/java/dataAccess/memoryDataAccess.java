package dataAccess;

import Model.Auth;
import Model.Game;
import Model.User;
import chess.ChessGame;

import java.util.*;

//TODO: Refactor this into several memory DAOs, shouldn't be too hard. Just cut and paste
//TODO: the code into the userDAO, authDAO, and gameDAO. REMEMBER: Single Responsibility!
public class memoryDataAccess implements DataAccess {
    private int gameID = 0;
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
    public Auth createAuth(String authToken, String username) {
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
    public List<Game> listGames(){
        List<Game> listGames = new ArrayList<>();
        for(int i = 1; i <= gameID; i++){
            listGames.add(games.get(i));
        }
        return listGames;
    }
    @Override
    public Game getGame(int gameID){
        return games.get(gameID);
    }

    @Override
    public Game createGame(Game game){
        gameID++;
        Game createGame = new Game(gameID,game.getWhiteUsername(), game.getBlackUsername(), game.getGameName(), game.getGame());
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
    public String toString() {
        return "memoryDataAccess{" +
                "gameID=" + gameID +
                ", users=" + users +
                ", auths=" + auths +
                ", games=" + games +
                '}';
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
