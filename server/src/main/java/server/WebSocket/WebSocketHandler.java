package server.WebSocket;

import Model.Auth;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import service.GameService;
import service.UserService;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import java.io.IOException;

@WebSocket
public class WebSocketHandler {
    private final ConnectionManager connections = new ConnectionManager();
    private final UserService userService;
    private final GameService gameService;

    public WebSocketHandler(UserService userService, GameService gameService){
        this.userService = userService;
        this.gameService = gameService;
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException, DataAccessException {
        UserGameCommand userGameCommand = new Gson().fromJson(message, UserGameCommand.class);
        Auth auth = userService.verifyUser(userGameCommand.getAuthString());
        switch (userGameCommand.getCommandType()){
            case JOIN_PLAYER, JOIN_OBSERVER -> {
                String joinColor = userGameCommand.getJoinColor();
                join(auth, session, joinColor);
            }
        }
    }

    private void join(Auth auth, Session session, String joinColor) throws IOException {
        String username = auth.getUsername();
        String authToken = auth.getAuthToken();
        connections.add(authToken, session);
        var message = String.format("%s has joined the game as %s.",username, joinColor);
        var serverMessage = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        serverMessage.addMessage(message);
        connections.broadcast(authToken, serverMessage);
    }
    /*
    public void announce(String userName) throws DataAccessException {
        try {

        }catch(Exception e){
            throw new DataAccessException(e.getMessage());
        }
    }
     */
}
