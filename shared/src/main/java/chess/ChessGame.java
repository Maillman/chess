package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    TeamColor turn;
    ChessBoard theBoard;
    public ChessGame() {

    }

    public ChessGame(TeamColor team, ChessBoard board){
        turn = team;
        theBoard = board;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return turn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        turn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        //TODO: May need to validate moves later...
        if(theBoard.getPiece(startPosition)!=null) {
            return theBoard.getPiece(startPosition).pieceMoves(theBoard, startPosition);
        }else{
            return null;
        }
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        //TODO: Make sure the move is valid.
        if(validMoves(move.getStartPosition())==null){
            throw new InvalidMoveException("Invalid Move: No piece found");
        }
        //TODO: For now, let's just make the move whether it's valid or not.
        theBoard.addPiece(move.getEndPosition(), theBoard.getPiece(move.getStartPosition()));
        theBoard.addPiece(move.getStartPosition(), null);
        //Assuming the move is valid, and made. Switch turns.
        switch (turn) {
            case WHITE -> setTeamTurn(TeamColor.BLACK);
            case BLACK -> setTeamTurn(TeamColor.WHITE);
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingPos = null;
        ChessPosition curPos;
        ArrayList<ChessMove> pieceMoves = new ArrayList<>();
        for(int i = 1; i < 8; i++){         //Iterating through the rows
            for(int j = 1; j < 8; j++){     //Iterating through the columns
                curPos = new ChessPosition(i,j);
                if((theBoard.getPiece(curPos)!=null)&&(theBoard.getPiece(curPos).getPieceType()==ChessPiece.PieceType.KING)&&(theBoard.getPiece(curPos).getTeamColor())==teamColor){
                    kingPos = curPos;
                }
                if((theBoard.getPiece(curPos)!=null)&&theBoard.getPiece(curPos).getTeamColor()!=teamColor){
                    pieceMoves.addAll(theBoard.getPiece(curPos).pieceMoves(theBoard, curPos));
                }
            }
        }
        for(ChessMove pieceMove : pieceMoves){
            if(pieceMove.getEndPosition().equals(kingPos)){
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        ChessPosition curPos;
        isInCheck(teamColor);
        ArrayList<ChessMove> pieceMoves = new ArrayList<>();
        for(int i = 1; i < 8; i++){         //Iterating through the rows
            for(int j = 1; j < 8; j++){     //Iterating through the columns
                curPos = new ChessPosition(i,j);
                if((theBoard.getPiece(curPos)!=null)&&theBoard.getPiece(curPos).getTeamColor()==teamColor){
                    pieceMoves.addAll(theBoard.getPiece(curPos).pieceMoves(theBoard, curPos));
                }
            }
        }
        for(ChessMove pieceMove : pieceMoves){
            ChessGame checkGame = new ChessGame(teamColor, theBoard);
            try {
                checkGame.makeMove(pieceMove);
            }catch(InvalidMoveException ime){
                System.out.println("Invalid Move?");
            }
            if(!checkGame.isInCheck(teamColor)){
                return false;
            }
        }
        return true;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        theBoard = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return theBoard;
    }
}
