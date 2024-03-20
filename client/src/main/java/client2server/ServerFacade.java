package client2server;

import Model.*;

public class ServerFacade {
    private static ClientConnector conn;

    public ServerFacade(String url){
        conn = new ClientConnector(url);
    }

    public Auth register(User user) throws ResponseException {
        var path = "/user";
        return conn.makeRequest("POST", path, user, Auth.class);
    }

    public Auth login(User user) throws ResponseException {
        var path = "/session";
        return conn.makeRequest("POST", path, user, Auth.class);
    }

    public void logout(String authToken) throws ResponseException {
        var path = "/session";
        conn.addRequestHeader(authToken);
        conn.makeRequest("DELETE", path, null, null);
    }

    public Games list(String authToken) throws ResponseException {
        var path = "/game";
        conn.addRequestHeader(authToken);
        return conn.makeRequest("GET", path, null, Games.class);
    }

    public Game create(Game game, String authToken) throws ResponseException {
        var path = "/game";
        conn.addRequestHeader(authToken);
        return conn.makeRequest("POST", path, game, Game.class);
    }

    public Game join(Join join, String authToken) throws ResponseException {
        var path = "/game";
        conn.addRequestHeader(authToken);
        return conn.makeRequest("PUT", path, join, Game.class);
    }

    public void clear() throws ResponseException {
        var path = "/db";
        conn.makeRequest("DELETE", path, null, null);
    }
}
