package server.WebSocket;

import Model.Auth;
import Model.Game;
import chess.ChessMove;
import chess.InvalidMoveException;
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
                int gameID = userGameCommand.getGameID();
                Game game = gameService.getGame(gameID);
                join(auth, session, joinColor, game);
            }
            case LEAVE -> {
                int gameID = userGameCommand.getGameID();
                Game game = gameService.getGame(gameID);
                leave(auth, gameID);
            }
            case MAKE_MOVE -> {
                int gameID = userGameCommand.getGameID();
                ChessMove chessMove = userGameCommand.getMove();
                Game foundGame = gameService.getGame(gameID);
                String chessPiece = foundGame.getGame().getBoard().getPiece(chessMove.getStartPosition()).toString();
                try{
                    foundGame.getGame().makeMove(chessMove);
                    Game updatedGame = gameService.updateGame(auth.getAuthToken(), gameID, foundGame);
                    move(auth, gameID, chessPiece, chessMove, updatedGame);
                }catch(InvalidMoveException ime){
                    error(auth,ime.getMessage());
                }
            }
        }
    }

    private void move(Auth auth, Integer gameID, String chessPiece, ChessMove chessMove, Game updatedGame) throws IOException {
        String username = auth.getUsername();
        String authToken = auth.getAuthToken();
        var message = String.format("%s has moved a %s from %s to %s.",username, chessPiece, chessMove.getStartPosition(),chessMove.getEndPosition());
        var serverMessage = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        var displayMessage = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
        displayMessage.addGame(updatedGame);
        connections.broadcastGame(displayMessage);
        serverMessage.addMessage(message);
        connections.broadcastNotification(authToken, gameID, serverMessage);
    }

    private void leave(Auth auth, Integer gameID) throws IOException {
        String username = auth.getUsername();
        String authToken = auth.getAuthToken();
        connections.remove(authToken, gameID);
        var message = String.format("%s has left the game.",username);
        var serverMessage = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        serverMessage.addMessage(message);
        connections.broadcastNotification(authToken, gameID, serverMessage);
    }

    private void join(Auth auth, Session session, String joinColor, Game game) throws IOException {
        String username = auth.getUsername();
        String authToken = auth.getAuthToken();
        connections.add(authToken, game.getGameID(), session);
        var message = String.format("%s has joined the game as %s.",username, joinColor);
        var serverMessage = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        var displayMessage = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
        serverMessage.addMessage(message);
        connections.broadcastNotification(authToken, game.getGameID(), serverMessage);
        displayMessage.addGame(game);
        connections.displayRoot(authToken, displayMessage);
    }
    private void error(Auth auth, String exceptionMessage) throws IOException {
        String authToken = auth.getAuthToken();
        var message = String.format("Error: %s.",exceptionMessage);
        var displayMessage = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
        displayMessage.addMessage(message);
        connections.displayRoot(authToken, displayMessage);
    }
}
