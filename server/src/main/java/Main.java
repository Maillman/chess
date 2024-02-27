import chess.*;
import dataAccess.memoryAuthDAO;
import dataAccess.memoryGameDAO;
import dataAccess.memoryUserDAO;
import server.Server;

public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Server: " + piece);
        new Server(new memoryUserDAO(), new memoryAuthDAO(), new memoryGameDAO()).run(8080);
    }
}