package server.WebSocket;

import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    public final ConcurrentHashMap<String, Connection> connections = new ConcurrentHashMap<>();
    public final ConcurrentHashMap<String, Integer> activeGames = new ConcurrentHashMap<>();

    public void add(String authToken, Integer gameID, Session session) {
        var connection = new Connection(authToken, session);
        connections.put(authToken, connection);
        activeGames.put(authToken, gameID);
    }

    public void remove(String authToken, Integer gameID) {
        connections.remove(authToken);
        activeGames.remove(authToken, gameID);
    }

    public void displayRoot(String authToken, ServerMessage loadRoot) throws IOException {
        var connection = connections.get(authToken);
        connection.send(loadRoot.toString());
    }
    public void broadcastNotification(String excludeAuthToken, Integer gameID, ServerMessage notification) throws IOException {
        var removeList = new ArrayList<Connection>();
        for (var c : connections.values()) {
            if (c.session.isOpen()) {
                if (!c.authToken.equals(excludeAuthToken)) {
                    if(Objects.equals(activeGames.get(c.authToken), gameID)) {
                        c.send(notification.toString());
                    }
                }
            } else {
                removeList.add(c);
            }
        }

        // Clean up any connections that were left open.
        for (var c : removeList) {
            connections.remove(c.authToken);
        }
    }

    public void broadcastGame(ServerMessage loadGame) throws IOException{
        Integer gameID = loadGame.getGame().getGameID();
        var removeList = new ArrayList<Connection>();
        for (var c : connections.values()) {
            if (c.session.isOpen()) {
                if (Objects.equals(activeGames.get(c.authToken), gameID)) {
                    c.send(loadGame.toString());
                }
            } else {
                removeList.add(c);
            }
        }

        // Clean up any connections that were left open.
        for (var c : removeList) {
            connections.remove(c.authToken);
        }
    }
}
