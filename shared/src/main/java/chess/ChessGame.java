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
    ChessBoard prevBoard;
    boolean castleQueenSideW = true, castleKingSideW = true;
    boolean castleQueenSideB = true, castleKingSideB = true;
    boolean makeQueenSide = false; boolean makeKingSide = false;
    public ChessGame() {

    }

    public ChessGame(TeamColor team, ChessBoard board){
        turn = team;
        theBoard = new ChessBoard(board);
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
        if(theBoard.getPiece(startPosition)!=null) {
            ArrayList<ChessMove> moves = new ArrayList<>(theBoard.getPiece(startPosition).pieceMoves(theBoard, startPosition));
            ArrayList<ChessMove> validMoves = new ArrayList<>();
            //Adding castling ability
            if(theBoard.getPiece(startPosition).getPieceType()==ChessPiece.PieceType.KING) {
                ChessPosition rookPos;
                ChessPosition kingSide = new ChessPosition(startPosition.getRow(), startPosition.getColumn() + 1);
                ChessPosition queenSide = new ChessPosition(startPosition.getRow(), startPosition.getColumn() - 1);
                if (turn != null) {
                    switch (turn) {
                        case WHITE -> {
                            //Castling KingSide
                            rookPos = new ChessPosition(startPosition.getRow(), 8);
                            if ((validMoves(rookPos).contains(new ChessMove(rookPos, kingSide))) && (castleKingSideW)) {
                                validMoves.add(new ChessMove(startPosition, new ChessPosition(startPosition.getRow(), startPosition.getColumn() + 2)));
                            }
                            //Castling QueenSide
                            rookPos = new ChessPosition(startPosition.getRow(), 1);
                            if ((validMoves(rookPos).contains(new ChessMove(rookPos, queenSide))) && (castleQueenSideW)) {
                                validMoves.add(new ChessMove(startPosition, new ChessPosition(startPosition.getRow(), startPosition.getColumn() - 2)));
                            }
                        }
                        case BLACK -> {
                            //Castling KingSide
                            rookPos = new ChessPosition(startPosition.getRow(), 8);
                            if ((validMoves(rookPos).contains(new ChessMove(rookPos, kingSide))) && (castleKingSideB)) {
                                validMoves.add(new ChessMove(startPosition, new ChessPosition(startPosition.getRow(), startPosition.getColumn() + 2)));
                            }
                            //Castling QueenSide
                            rookPos = new ChessPosition(startPosition.getRow(), 1);
                            if ((validMoves(rookPos).contains(new ChessMove(rookPos, queenSide))) && (castleQueenSideB)) {
                                validMoves.add(new ChessMove(startPosition, new ChessPosition(startPosition.getRow(), startPosition.getColumn() - 2)));
                            }
                        }
                    }
                }
            }
            for(ChessMove pieceMove : moves){
                ChessGame checkGame = new ChessGame(theBoard.getPiece(startPosition).getTeamColor(), theBoard);
                checkGame.getBoard().addPiece(pieceMove.getEndPosition(), checkGame.getBoard().getPiece(pieceMove.getStartPosition()));
                checkGame.getBoard().addPiece(pieceMove.getStartPosition(), null);
                if(!checkGame.isInCheck(theBoard.getPiece(startPosition).getTeamColor())){
                    validMoves.add(pieceMove);
                }
            }
            return validMoves;
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
        ChessGame preGame = new ChessGame(turn, theBoard);
        ArrayList<ChessMove> validMoves = new ArrayList<>(validMoves(move.getStartPosition()));
        //System.out.println("Moving" + theBoard.getPiece(move.getStartPosition()).toString() + move.toString());
        if(!validMoves.contains(move)){
            throw new InvalidMoveException("Invalid Move: Piece can't go there!");
        }
        if(theBoard.getPiece(move.getStartPosition()).getTeamColor()!=turn){
            throw new InvalidMoveException("Invalid Move: Piece move out of order!");
        }
        ChessGame checkGame = new ChessGame(turn, theBoard);
        checkGame.getBoard().addPiece(move.getEndPosition(), checkGame.getBoard().getPiece(move.getStartPosition()));
        checkGame.getBoard().addPiece(move.getStartPosition(), null);
        if(checkGame.isInCheck(turn)){
            throw new InvalidMoveException("Invalid Move: In check!");
        }
        //Makes the move.
        if(move.getPromotionPiece()!=null){
            theBoard.addPiece(move.getEndPosition(), new ChessPiece(turn,move.getPromotionPiece()));
        }else {
            theBoard.addPiece(move.getEndPosition(), theBoard.getPiece(move.getStartPosition()));
        }
        theBoard.addPiece(move.getStartPosition(), null);
        //Placing Castling Move
        if((preGame.getBoard().getPiece(move.getStartPosition())!=null)&&(preGame.getBoard().getPiece(move.getStartPosition()).getPieceType()== ChessPiece.PieceType.KING)&&((move.getEndPosition().getColumn()-move.getStartPosition().getColumn()==2))){
            theBoard.addPiece(new ChessPosition(move.getStartPosition().getRow(),move.getStartPosition().getColumn()+1),new ChessPiece(turn, ChessPiece.PieceType.ROOK));
            theBoard.addPiece(new ChessPosition(move.getStartPosition().getRow(),8),null);
        }else if((preGame.getBoard().getPiece(move.getStartPosition())!=null)&&(preGame.getBoard().getPiece(move.getStartPosition()).getPieceType()== ChessPiece.PieceType.KING)&&((move.getEndPosition().getColumn()-move.getStartPosition().getColumn()==-2))){
            theBoard.addPiece(new ChessPosition(move.getStartPosition().getRow(),move.getStartPosition().getColumn()-1),new ChessPiece(turn, ChessPiece.PieceType.ROOK));
            theBoard.addPiece(new ChessPosition(move.getStartPosition().getRow(),1),null);
        }
        //Checking for castling
        switch (turn) {
            case WHITE -> {
                if(((theBoard.getPiece(move.getStartPosition())!=null)&&(preGame.getBoard().getPiece(move.getStartPosition()).getPieceType()==ChessPiece.PieceType.KING))&&((theBoard.getPiece(new ChessPosition(1,8))!=null)&&(theBoard.getPiece(new ChessPosition(1,8)).getPieceType()==ChessPiece.PieceType.ROOK))){
                    castleKingSideW = false;
                }
                if(((theBoard.getPiece(move.getStartPosition())!=null)&&(preGame.getBoard().getPiece(move.getStartPosition()).getPieceType()==ChessPiece.PieceType.KING))&&((theBoard.getPiece(new ChessPosition(1,1))!=null)&&(theBoard.getPiece(new ChessPosition(1,1)).getPieceType()==ChessPiece.PieceType.ROOK))){
                    castleQueenSideW = false;
                }
            }
            case BLACK -> {
                if((theBoard.getPiece(move.getStartPosition())!=null)&&(preGame.getBoard().getPiece(move.getStartPosition()).getPieceType()==ChessPiece.PieceType.KING)&&(theBoard.getPiece(new ChessPosition(1,8))!=null)&&(theBoard.getPiece(new ChessPosition(8,8)).getPieceType()== ChessPiece.PieceType.ROOK)){
                    castleKingSideB = false;
                }
                if((theBoard.getPiece(move.getStartPosition())!=null)&&(preGame.getBoard().getPiece(move.getStartPosition()).getPieceType()==ChessPiece.PieceType.KING)&&(theBoard.getPiece(new ChessPosition(1,8))!=null)&&(theBoard.getPiece(new ChessPosition(8,1)).getPieceType()== ChessPiece.PieceType.ROOK)){
                    castleQueenSideB = false;
                }
            }
        }
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
        for(int i = 1; i <= 8; i++){         //Iterating through the rows
            for(int j = 1; j <= 8; j++){     //Iterating through the columns
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
        if(isInCheck(teamColor)) {
            ArrayList<ChessMove> pieceMoves = new ArrayList<>();
            for (int i = 1; i <= 8; i++) {         //Iterating through the rows
                for (int j = 1; j <= 8; j++) {     //Iterating through the columns
                    curPos = new ChessPosition(i, j);
                    if ((theBoard.getPiece(curPos) != null) && theBoard.getPiece(curPos).getTeamColor() == teamColor) {
                        pieceMoves.addAll(theBoard.getPiece(curPos).pieceMoves(theBoard, curPos));
                    }
                }
            }
            for (ChessMove pieceMove : pieceMoves) {
                ChessGame checkGame = new ChessGame(teamColor, theBoard);
                try {
                    checkGame.makeMove(pieceMove);
                } catch (InvalidMoveException ime) {
                    System.out.println("Invalid Move");
                }
                if (!checkGame.isInCheck(teamColor)) {
                    return false;
                }
            }
            return true;
        }else{
            return false;
        }
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        ChessPosition curPos;
        ArrayList<ChessMove> pieceMoves = new ArrayList<>();
        for(int i = 1; i <= 8; i++){         //Iterating through the rows
            for(int j = 1; j <= 8; j++){     //Iterating through the columns
                curPos = new ChessPosition(i,j);
                if((theBoard.getPiece(curPos)!=null)&&theBoard.getPiece(curPos).getTeamColor()==teamColor){
                    pieceMoves.addAll(theBoard.getPiece(curPos).pieceMoves(theBoard, curPos));
                }
            }
        }
        boolean stalemate = true;
        for(ChessMove pieceMove : pieceMoves){
            ChessGame checkGame = new ChessGame(teamColor, theBoard);
            try {
                checkGame.makeMove(pieceMove);
            }catch(InvalidMoveException ime){
                System.out.println("Invalid Move");
            }
            checkGame.getBoard().addPiece(pieceMove.getEndPosition(), checkGame.getBoard().getPiece(pieceMove.getStartPosition()));
            checkGame.getBoard().addPiece(pieceMove.getStartPosition(), null);
            if(!checkGame.isInCheck(teamColor)){
                stalemate = false;
            }
        }
        return stalemate;
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
