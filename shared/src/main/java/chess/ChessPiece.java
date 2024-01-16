package chess;

import java.util.ArrayList;
import java.util.Collection;
//import java.util.HashSet;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
//Maybe generate test-comparison here?

public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessPiece that)) return false;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        //ArrayList<ChessMove> pieceMoves = new ArrayList<>();
        ArrayList<ChessMove> pieceMoves = new ArrayList<>();
        /*
        final int[] startPos = {myPosition.getRow(), myPosition.getColumn()};
        int[] curPos = startPos.clone();
         */
        switch (board.getPiece(myPosition).getPieceType()) {
            case BISHOP -> {
                System.out.println("This is a bishop!");
                //Loop through the diagonal, starting top-left
                diagonal("Top","Left",board,myPosition,pieceMoves);
                //Now top-right
                diagonal("Top","Right",board,myPosition,pieceMoves);
                //Now bottom-left
                diagonal("Bottom","Left",board,myPosition,pieceMoves);
                //Now bottom-right
                diagonal("Bottom","Right",board,myPosition,pieceMoves);

            }
            case ROOK -> {
                System.out.println("This is a rook!");
                //Loop through the orthogonal, starting up
                orthogonal("Up",board,myPosition,pieceMoves);
                //Now down
                orthogonal("Down",board,myPosition,pieceMoves);
                //Now left
                orthogonal("Left",board,myPosition,pieceMoves);
                //Now right
                orthogonal("Right",board,myPosition,pieceMoves);
            }
            case QUEEN -> {
                System.out.println("This is a queen!");
                //Loop through the diagonal, then the orthogonal
                //Loop through the diagonal, starting top-left
                diagonal("Top","Left",board,myPosition,pieceMoves);
                //Now top-right
                diagonal("Top","Right",board,myPosition,pieceMoves);
                //Now bottom-left
                diagonal("Bottom","Left",board,myPosition,pieceMoves);
                //Now bottom-right
                diagonal("Bottom","Right",board,myPosition,pieceMoves);
                //Loop through the orthogonal, starting up
                orthogonal("Up",board,myPosition,pieceMoves);
                //Now down
                orthogonal("Down",board,myPosition,pieceMoves);
                //Now left
                orthogonal("Left",board,myPosition,pieceMoves);
                //Now right
                orthogonal("Right",board,myPosition,pieceMoves);
            }
            default -> {
                System.out.println("What is this piece?");
            }
        }
        return pieceMoves;
    }
    private void diagonal(String Vertical, String Horizontal, ChessBoard board, ChessPosition myPosition, Collection<ChessMove> pieceMoves){
        final int[] startPos = {myPosition.getRow(), myPosition.getColumn()};
        int[] curPos = startPos.clone();
        if((Objects.equals(Vertical, "Top")) && (Objects.equals(Horizontal, "Left"))){
            while((curPos[0]+1 != 9)&&(curPos[1]-1 != 0)){
                curPos[0]++;
                curPos[1]--;
                ChessPosition newPos = new ChessPosition(curPos[0],curPos[1]);
                if((board.getPiece(newPos) != null) && (board.getPiece(newPos).getTeamColor() == board.getPiece(myPosition).getTeamColor())){
                    break;
                }
                ChessMove newMove = new ChessMove(myPosition,newPos);
                pieceMoves.add(newMove);
                if((board.getPiece(newPos) != null) && (board.getPiece(newPos).getTeamColor() != board.getPiece(myPosition).getTeamColor())){
                    break;
                }
            }
        }else if((Objects.equals(Vertical, "Top")) && (Objects.equals(Horizontal, "Right"))){
            while((curPos[0]+1 != 9)&&(curPos[1]+1 != 9)){
                curPos[0]++;
                curPos[1]++;
                ChessPosition newPos = new ChessPosition(curPos[0],curPos[1]);
                if((board.getPiece(newPos) != null) && (board.getPiece(newPos).getTeamColor() == board.getPiece(myPosition).getTeamColor())){
                    break;
                }
                ChessMove newMove = new ChessMove(myPosition,newPos);
                pieceMoves.add(newMove);
                if((board.getPiece(newPos) != null) && (board.getPiece(newPos).getTeamColor() != board.getPiece(myPosition).getTeamColor())){
                    break;
                }
            }
        }else if((Objects.equals(Vertical, "Bottom")) && (Objects.equals(Horizontal, "Left"))){
            while((curPos[0]-1 != 0)&&(curPos[1]-1 != 0)){
                curPos[0]--;
                curPos[1]--;
                ChessPosition newPos = new ChessPosition(curPos[0],curPos[1]);
                if((board.getPiece(newPos) != null) && (board.getPiece(newPos).getTeamColor() == board.getPiece(myPosition).getTeamColor())){
                    break;
                }
                ChessMove newMove = new ChessMove(myPosition,newPos);
                pieceMoves.add(newMove);
                if((board.getPiece(newPos) != null) && (board.getPiece(newPos).getTeamColor() != board.getPiece(myPosition).getTeamColor())){
                    break;
                }
            }
        }else if((Objects.equals(Vertical, "Bottom")) && (Objects.equals(Horizontal, "Right"))){
            while((curPos[0]-1 != 0)&&(curPos[1]+1 != 9)){
                curPos[0]--;
                curPos[1]++;
                ChessPosition newPos = new ChessPosition(curPos[0],curPos[1]);
                if((board.getPiece(newPos) != null) && (board.getPiece(newPos).getTeamColor() == board.getPiece(myPosition).getTeamColor())){
                    break;
                }
                ChessMove newMove = new ChessMove(myPosition,newPos);
                pieceMoves.add(newMove);
                if((board.getPiece(newPos) != null) && (board.getPiece(newPos).getTeamColor() != board.getPiece(myPosition).getTeamColor())){
                    break;
                }
            }
        }
    }

    private void orthogonal(String Direction, ChessBoard board, ChessPosition myPosition, Collection<ChessMove> pieceMoves){
        final int[] startPos = {myPosition.getRow(), myPosition.getColumn()};
        int[] curPos = startPos.clone();
        if(Objects.equals(Direction,"Up")){
            while((curPos[0]+1 != 9)){
                curPos[0]++;
                ChessPosition newPos = new ChessPosition(curPos[0],curPos[1]);
                if((board.getPiece(newPos) != null) && (board.getPiece(newPos).getTeamColor() == board.getPiece(myPosition).getTeamColor())){
                    break;
                }
                ChessMove newMove = new ChessMove(myPosition,newPos);
                pieceMoves.add(newMove);
                if((board.getPiece(newPos) != null) && (board.getPiece(newPos).getTeamColor() != board.getPiece(myPosition).getTeamColor())){
                    break;
                }
            }
        }else if(Objects.equals(Direction,"Down")){
            while((curPos[0]-1 != 0)){
                curPos[0]--;
                ChessPosition newPos = new ChessPosition(curPos[0],curPos[1]);
                if((board.getPiece(newPos) != null) && (board.getPiece(newPos).getTeamColor() == board.getPiece(myPosition).getTeamColor())){
                    break;
                }
                ChessMove newMove = new ChessMove(myPosition,newPos);
                pieceMoves.add(newMove);
                if((board.getPiece(newPos) != null) && (board.getPiece(newPos).getTeamColor() != board.getPiece(myPosition).getTeamColor())){
                    break;
                }
            }
        }else if(Objects.equals(Direction,"Left")){
            while((curPos[1]-1 != 0)){
                curPos[1]--;
                ChessPosition newPos = new ChessPosition(curPos[0],curPos[1]);
                if((board.getPiece(newPos) != null) && (board.getPiece(newPos).getTeamColor() == board.getPiece(myPosition).getTeamColor())){
                    break;
                }
                ChessMove newMove = new ChessMove(myPosition,newPos);
                pieceMoves.add(newMove);
                if((board.getPiece(newPos) != null) && (board.getPiece(newPos).getTeamColor() != board.getPiece(myPosition).getTeamColor())){
                    break;
                }
            }
        }else if(Objects.equals(Direction,"Right")){
            while((curPos[1]+1 != 9)){
                curPos[1]++;
                ChessPosition newPos = new ChessPosition(curPos[0],curPos[1]);
                if((board.getPiece(newPos) != null) && (board.getPiece(newPos).getTeamColor() == board.getPiece(myPosition).getTeamColor())){
                    break;
                }
                ChessMove newMove = new ChessMove(myPosition,newPos);
                pieceMoves.add(newMove);
                if((board.getPiece(newPos) != null) && (board.getPiece(newPos).getTeamColor() != board.getPiece(myPosition).getTeamColor())){
                    break;
                }
            }
        }
    }
}
