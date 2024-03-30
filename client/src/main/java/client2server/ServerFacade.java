package client2server;

import Model.*;
import com.google.gson.Gson;
import webSocketMessages.userCommands.UserGameCommand;

import java.io.IOException;

public class ServerFacade {
    private static HTTPCommunicator conn;
    private final WebSocketCommunicator ws;

    public ServerFacade(String url){
        conn = new HTTPCommunicator(url);
        ws = null;
    }

    public ServerFacade(String url, ServerMessageObserver smo) throws ResponseException {
        conn = new HTTPCommunicator(url);
        ws = new WebSocketCommunicator(url,smo);
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
        var game = conn.makeRequest("PUT", path, join, Game.class);
        if(ws!=null) {
            wsJoin(join, authToken);
        }
        return game;
    }

    public void clear() throws ResponseException {
        var path = "/db";
        conn.makeRequest("DELETE", path, null, null);
    }

    private void wsJoin(Join join, String authToken) throws ResponseException {
        try {
            var userGameCommand = new UserGameCommand(authToken);
            if (join.getPlayerColor() == null) {
                userGameCommand.setCommandType(UserGameCommand.CommandType.JOIN_OBSERVER);
            } else {
                userGameCommand.setCommandType(UserGameCommand.CommandType.JOIN_PLAYER);
            }
            assert ws != null;
            ws.session.getBasicRemote().sendText(new Gson().toJson(userGameCommand));
        }catch(IOException ioe){
            throw new ResponseException(500, ioe.getMessage());
        }
    }
}
