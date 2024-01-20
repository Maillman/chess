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

    @Override
    public String toString() {
        return "ChessPiece{" +
                "pieceColor=" + pieceColor +
                ", type=" + type +
                '}';
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
            case KNIGHT -> {
                System.out.println("This is a Knight!");
                //Start with the top L's
                knightL("Up",board,myPosition,pieceMoves);
                //Now with the bottom L's
                knightL("Down",board,myPosition,pieceMoves);
                //Now with the left L's
                knightL("Left",board,myPosition,pieceMoves);
                //Now with the right L's
                knightL("Right",board,myPosition,pieceMoves);
            }
            case PAWN -> {
                System.out.println("This is a Pawn!");
                pawn(board,myPosition,pieceMoves);
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
    private void knightL(String Direction, ChessBoard board, ChessPosition myPosition, Collection<ChessMove> pieceMoves){
        final int[] startPos = {myPosition.getRow(), myPosition.getColumn()};
        int[] curPos = startPos.clone();
        if(Objects.equals(Direction, "Up")){
            if(!(curPos[0]+2 >= 9)){
                curPos[0] = curPos[0] + 2;
                if(!(curPos[1]-2 <= 0)){
                    curPos[1]--;
                    ChessPosition newPos = new ChessPosition(curPos[0],curPos[1]);
                    if(!((board.getPiece(newPos) != null) && (board.getPiece(newPos).getTeamColor() == board.getPiece(myPosition).getTeamColor()))){
                        ChessMove newMove = new ChessMove(myPosition, newPos);
                        pieceMoves.add(newMove);
                    }
                }
                if(!(curPos[1]+2 >= 9)){
                    curPos[1] = startPos[1];
                    curPos[1]++;
                    ChessPosition newPos = new ChessPosition(curPos[0],curPos[1]);
                    if(!((board.getPiece(newPos) != null) && (board.getPiece(newPos).getTeamColor() == board.getPiece(myPosition).getTeamColor()))){
                        ChessMove newMove = new ChessMove(myPosition, newPos);
                        pieceMoves.add(newMove);
                    }
                }
            }
        }else if(Objects.equals(Direction, "Down")){
            if(!(curPos[0]-2 <= 0)){
                curPos[0] = curPos[0] - 2;
                if(!(curPos[1]-2 <= 0)){
                    curPos[1]--;
                    ChessPosition newPos = new ChessPosition(curPos[0],curPos[1]);
                    if(!((board.getPiece(newPos) != null) && (board.getPiece(newPos).getTeamColor() == board.getPiece(myPosition).getTeamColor()))){
                        ChessMove newMove = new ChessMove(myPosition, newPos);
                        pieceMoves.add(newMove);
                    }
                }
                if(!(curPos[1]+2 >= 9)){
                    curPos[1] = startPos[1];
                    curPos[1]++;
                    ChessPosition newPos = new ChessPosition(curPos[0],curPos[1]);
                    if(!((board.getPiece(newPos) != null) && (board.getPiece(newPos).getTeamColor() == board.getPiece(myPosition).getTeamColor()))){
                        ChessMove newMove = new ChessMove(myPosition, newPos);
                        pieceMoves.add(newMove);
                    }
                }
            }
        }else if(Objects.equals(Direction, "Left")){
            if(!(curPos[1]-2 <= 0)){
                curPos[1] = curPos[1] - 2;
                if(!(curPos[0]-1 <= 0)){
                    curPos[0]--;
                    ChessPosition newPos = new ChessPosition(curPos[0],curPos[1]);
                    if(!((board.getPiece(newPos) != null) && (board.getPiece(newPos).getTeamColor() == board.getPiece(myPosition).getTeamColor()))){
                        ChessMove newMove = new ChessMove(myPosition, newPos);
                        pieceMoves.add(newMove);
                    }
                }
                if(!(curPos[0]+2 >= 9)){
                    curPos[0] = startPos[0];
                    curPos[0]++;
                    ChessPosition newPos = new ChessPosition(curPos[0],curPos[1]);
                    if(!((board.getPiece(newPos) != null) && (board.getPiece(newPos).getTeamColor() == board.getPiece(myPosition).getTeamColor()))){
                        ChessMove newMove = new ChessMove(myPosition, newPos);
                        pieceMoves.add(newMove);
                    }
                }
            }
        }else if(Objects.equals(Direction, "Right")){
            if(!(curPos[1]+2 >= 9)){
                curPos[1] = curPos[1] + 2;
                if(!(curPos[0]-1 <= 0)){
                    curPos[0]--;
                    ChessPosition newPos = new ChessPosition(curPos[0],curPos[1]);
                    if(!((board.getPiece(newPos) != null) && (board.getPiece(newPos).getTeamColor() == board.getPiece(myPosition).getTeamColor()))){
                        ChessMove newMove = new ChessMove(myPosition, newPos);
                        pieceMoves.add(newMove);
                    }
                }
                if(!(curPos[0]+2 >= 9)){
                    curPos[0] = startPos[0];
                    curPos[0]++;
                    ChessPosition newPos = new ChessPosition(curPos[0],curPos[1]);
                    if(!((board.getPiece(newPos) != null) && (board.getPiece(newPos).getTeamColor() == board.getPiece(myPosition).getTeamColor()))){
                        ChessMove newMove = new ChessMove(myPosition, newPos);
                        pieceMoves.add(newMove);
                    }
                }
            }
        }
    }

    private void pawn(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> pieceMoves){
        final int[] startPos = {myPosition.getRow(), myPosition.getColumn()};
        int[] curPos = startPos.clone();
        if(board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE){
            curPos[0]++;
            ChessPosition newPos = new ChessPosition(curPos[0],curPos[1]);
            ChessMove newMove;
            if((board.getPiece(new ChessPosition(curPos[0],curPos[1]-1))!= null) && (board.getPiece(new ChessPosition(curPos[0],curPos[1]-1)).getTeamColor() != board.getPiece(myPosition).getTeamColor())){
                if(curPos[0]==8){
                    newMove = new ChessMove(myPosition,new ChessPosition(curPos[0],curPos[1]-1),PieceType.QUEEN);
                    pieceMoves.add(newMove);
                    newMove = new ChessMove(myPosition,new ChessPosition(curPos[0],curPos[1]-1),PieceType.ROOK);
                    pieceMoves.add(newMove);
                    newMove = new ChessMove(myPosition,new ChessPosition(curPos[0],curPos[1]-1),PieceType.BISHOP);
                    pieceMoves.add(newMove);
                    newMove = new ChessMove(myPosition,new ChessPosition(curPos[0],curPos[1]-1),PieceType.KNIGHT);
                    pieceMoves.add(newMove);
                }else {
                    newMove = new ChessMove(myPosition, new ChessPosition(curPos[0], curPos[1] - 1));
                    pieceMoves.add(newMove);
                }
            }
            if((board.getPiece(new ChessPosition(curPos[0],curPos[1]+1))!= null) && (board.getPiece(new ChessPosition(curPos[0],curPos[1]+1)).getTeamColor() != board.getPiece(myPosition).getTeamColor())){
                if(curPos[0]==8){
                    newMove = new ChessMove(myPosition,new ChessPosition(curPos[0],curPos[1]+1),PieceType.QUEEN);
                    pieceMoves.add(newMove);
                    newMove = new ChessMove(myPosition,new ChessPosition(curPos[0],curPos[1]+1),PieceType.ROOK);
                    pieceMoves.add(newMove);
                    newMove = new ChessMove(myPosition,new ChessPosition(curPos[0],curPos[1]+1),PieceType.BISHOP);
                    pieceMoves.add(newMove);
                    newMove = new ChessMove(myPosition,new ChessPosition(curPos[0],curPos[1]+1),PieceType.KNIGHT);
                    pieceMoves.add(newMove);
                }else {
                    newMove = new ChessMove(myPosition, new ChessPosition(curPos[0], curPos[1] + 1));
                    pieceMoves.add(newMove);
                }
            }
            if(board.getPiece(newPos) == null){
                if(curPos[0]==8){
                    newMove = new ChessMove(myPosition,new ChessPosition(curPos[0],curPos[1]),PieceType.QUEEN);
                    pieceMoves.add(newMove);
                    newMove = new ChessMove(myPosition,new ChessPosition(curPos[0],curPos[1]),PieceType.ROOK);
                    pieceMoves.add(newMove);
                    newMove = new ChessMove(myPosition,new ChessPosition(curPos[0],curPos[1]),PieceType.BISHOP);
                    pieceMoves.add(newMove);
                    newMove = new ChessMove(myPosition,new ChessPosition(curPos[0],curPos[1]),PieceType.KNIGHT);
                    pieceMoves.add(newMove);
                }else {
                    newMove = new ChessMove(myPosition, newPos);
                    pieceMoves.add(newMove);
                    if (startPos[0] == 2) {
                        curPos[0]++;
                        newPos = new ChessPosition(curPos[0], curPos[1]);
                        if (board.getPiece(newPos) == null) {
                            newMove = new ChessMove(myPosition, newPos);
                            pieceMoves.add(newMove);
                        }
                    }
                }
            }
        }
        if(board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.BLACK){
            curPos[0]--;
            ChessPosition newPos = new ChessPosition(curPos[0],curPos[1]);
            ChessMove newMove;
            if((board.getPiece(new ChessPosition(curPos[0],curPos[1]-1))!= null) && (board.getPiece(new ChessPosition(curPos[0],curPos[1]-1)).getTeamColor() != board.getPiece(myPosition).getTeamColor())){
                if(curPos[0]==1){
                    newMove = new ChessMove(myPosition,new ChessPosition(curPos[0],curPos[1]-1),PieceType.QUEEN);
                    pieceMoves.add(newMove);
                    newMove = new ChessMove(myPosition,new ChessPosition(curPos[0],curPos[1]-1),PieceType.ROOK);
                    pieceMoves.add(newMove);
                    newMove = new ChessMove(myPosition,new ChessPosition(curPos[0],curPos[1]-1),PieceType.BISHOP);
                    pieceMoves.add(newMove);
                    newMove = new ChessMove(myPosition,new ChessPosition(curPos[0],curPos[1]-1),PieceType.KNIGHT);
                    pieceMoves.add(newMove);
                }else {
                    newMove = new ChessMove(myPosition, new ChessPosition(curPos[0], curPos[1] - 1));
                    pieceMoves.add(newMove);
                }
            }
            if((board.getPiece(new ChessPosition(curPos[0],curPos[1]+1))!= null) && (board.getPiece(new ChessPosition(curPos[0],curPos[1]+1)).getTeamColor() != board.getPiece(myPosition).getTeamColor())){
                if(curPos[0]==1){
                    newMove = new ChessMove(myPosition,new ChessPosition(curPos[0],curPos[1]+1),PieceType.QUEEN);
                    pieceMoves.add(newMove);
                    newMove = new ChessMove(myPosition,new ChessPosition(curPos[0],curPos[1]+1),PieceType.ROOK);
                    pieceMoves.add(newMove);
                    newMove = new ChessMove(myPosition,new ChessPosition(curPos[0],curPos[1]+1),PieceType.BISHOP);
                    pieceMoves.add(newMove);
                    newMove = new ChessMove(myPosition,new ChessPosition(curPos[0],curPos[1]+1),PieceType.KNIGHT);
                    pieceMoves.add(newMove);
                }else {
                    newMove = new ChessMove(myPosition, new ChessPosition(curPos[0], curPos[1] + 1));
                    pieceMoves.add(newMove);
                }
            }
            if(board.getPiece(newPos) == null){
                if(curPos[0]==1){
                    newMove = new ChessMove(myPosition,new ChessPosition(curPos[0],curPos[1]),PieceType.QUEEN);
                    pieceMoves.add(newMove);
                    newMove = new ChessMove(myPosition,new ChessPosition(curPos[0],curPos[1]),PieceType.ROOK);
                    pieceMoves.add(newMove);
                    newMove = new ChessMove(myPosition,new ChessPosition(curPos[0],curPos[1]),PieceType.BISHOP);
                    pieceMoves.add(newMove);
                    newMove = new ChessMove(myPosition,new ChessPosition(curPos[0],curPos[1]),PieceType.KNIGHT);
                    pieceMoves.add(newMove);
                }else {
                    newMove = new ChessMove(myPosition, newPos);
                    pieceMoves.add(newMove);
                    if (startPos[0] == 7) {
                        curPos[0]--;
                        newPos = new ChessPosition(curPos[0], curPos[1]);
                        if (board.getPiece(newPos) == null) {
                            newMove = new ChessMove(myPosition, newPos);
                            pieceMoves.add(newMove);
                        }
                    }
                }
            }
        }
    }
}
