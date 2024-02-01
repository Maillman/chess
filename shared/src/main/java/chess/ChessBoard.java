package chess;

import java.util.Arrays;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessBoard that)) return false;
        return Arrays.deepEquals(squares, that.squares);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(squares);
    }

    @Override
    public String toString() {
        return "ChessBoard{" +
                "squares=" + Arrays.deepToString(squares) +
                '}';
    }

    private ChessPiece[][] squares = new ChessPiece[8][8];
    public ChessBoard() {
        
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        squares[position.getRow()-1][position.getColumn()-1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return squares[position.getRow()-1][position.getColumn()-1];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        ChessGame.TeamColor Color = ChessGame.TeamColor.WHITE;
        int rank = 0;
        //White-Side
        squares[rank][0] = new ChessPiece(Color, ChessPiece.PieceType.ROOK);
        squares[rank][7] = new ChessPiece(Color, ChessPiece.PieceType.ROOK);
        squares[rank][1] = new ChessPiece(Color, ChessPiece.PieceType.KNIGHT);
        squares[rank][6] = new ChessPiece(Color, ChessPiece.PieceType.KNIGHT);
        squares[rank][2] = new ChessPiece(Color, ChessPiece.PieceType.BISHOP);
        squares[rank][5] = new ChessPiece(Color, ChessPiece.PieceType.BISHOP);
        squares[rank][3] = new ChessPiece(Color, ChessPiece.PieceType.QUEEN);
        squares[rank][4] = new ChessPiece(Color, ChessPiece.PieceType.KING);
        rank++;
        for (int i = 0; i < 8; i++) {
            squares[rank][i] = new ChessPiece(Color, ChessPiece.PieceType.PAWN);
        }
        //Black-Side
        Color = ChessGame.TeamColor.BLACK;
        rank = 7;
        squares[rank][0] = new ChessPiece(Color, ChessPiece.PieceType.ROOK);
        squares[rank][7] = new ChessPiece(Color, ChessPiece.PieceType.ROOK);
        squares[rank][1] = new ChessPiece(Color, ChessPiece.PieceType.KNIGHT);
        squares[rank][6] = new ChessPiece(Color, ChessPiece.PieceType.KNIGHT);
        squares[rank][2] = new ChessPiece(Color, ChessPiece.PieceType.BISHOP);
        squares[rank][5] = new ChessPiece(Color, ChessPiece.PieceType.BISHOP);
        squares[rank][3] = new ChessPiece(Color, ChessPiece.PieceType.QUEEN);
        squares[rank][4] = new ChessPiece(Color, ChessPiece.PieceType.KING);
        rank--;
        for (int i = 0; i < 8; i++) {
            squares[rank][i] = new ChessPiece(Color, ChessPiece.PieceType.PAWN);
        }
    }
}
