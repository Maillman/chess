import Model.*;
import com.google.gson.Gson;

import java.io.*;
import java.net.*;
import java.util.List;

public class ServerFacade {
    private static ClientConnector conn;

    public ServerFacade(String url){
        conn = new ClientConnector(url);
    }

    public Auth register(User user) throws ResponseException{
        var path = "/user";
        return conn.makeRequest("POST", path, user, Auth.class);
    }

    public Auth login(User user) throws ResponseException {
        var path = "/session";
        return conn.makeRequest("POST", path, user, Auth.class);
    }

    public List<Game> list(String authToken) throws ResponseException {
        var path = "/game";
        return conn.makeRequest("GET", path, authToken, List.class);
    }

    public Game create(Game game) throws ResponseException {
        var path = "/game";
        return conn.makeRequest("POST", path, game, Game.class);
    }
}
