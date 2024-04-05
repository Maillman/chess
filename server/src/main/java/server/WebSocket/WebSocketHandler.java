package server.WebSocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import java.io.IOException;

@WebSocket
public class WebSocketHandler {
    private final ConnectionManager connections = new ConnectionManager();
    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        UserGameCommand userGameCommand = new Gson().fromJson(message, UserGameCommand.class);
        switch (userGameCommand.getCommandType()){
            case JOIN_PLAYER -> join(userGameCommand.getAuthString(), session);
        }
    }

    private void join(String authToken, Session session) throws IOException {
        connections.add(authToken,session);
        var message = String.format("%s has joined the game.",authToken);
        var serverMessage = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        connections.broadcast(authToken, serverMessage);
    }
}
