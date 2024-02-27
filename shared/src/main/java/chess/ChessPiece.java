package chess;

import java.util.ArrayList;
import java.util.Collection;
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
        ArrayList<ChessMove> pieceMoves = new ArrayList<>();
        switch (board.getPiece(myPosition).getPieceType()) {
            case BISHOP -> {
                directional("UpLeft",board,myPosition,pieceMoves);
                directional("UpRight",board,myPosition,pieceMoves);
                directional("DownLeft",board,myPosition,pieceMoves);
                directional("DownRight",board,myPosition,pieceMoves);
            }
            case ROOK -> {
                //Checked Improved Code
                directional("Up",board,myPosition,pieceMoves);
                directional("Down",board,myPosition,pieceMoves);
                directional("Left",board,myPosition,pieceMoves);
                directional("Right",board,myPosition,pieceMoves);

            }
            case QUEEN -> {
                directional("UpLeft",board,myPosition,pieceMoves);
                directional("UpRight",board,myPosition,pieceMoves);
                directional("DownLeft",board,myPosition,pieceMoves);
                directional("DownRight",board,myPosition,pieceMoves);
                directional("Up",board,myPosition,pieceMoves);
                directional("Down",board,myPosition,pieceMoves);
                directional("Left",board,myPosition,pieceMoves);
                directional("Right",board,myPosition,pieceMoves);
            }
            case KNIGHT -> {
                //System.out.println("This is a Knight!");
                //Start with the top L's
                knightL("Up",board,myPosition,pieceMoves);
                //Now with the bottom L's
                knightL("Down",board,myPosition,pieceMoves);
                //Now with the left L's
                knightL("Left",board,myPosition,pieceMoves);
                //Now with the right L's
                knightL("Right",board,myPosition,pieceMoves);
            }
            case PAWN -> pawn(board,myPosition,pieceMoves);
            case KING -> king(board,myPosition,pieceMoves);
            default -> System.out.println("What is this piece?");
        }
        return pieceMoves;
    }
    private void knightL(String Direction, ChessBoard board, ChessPosition myPosition, Collection<ChessMove> pieceMoves){
        final int[] startPos = {myPosition.getRow(), myPosition.getColumn()};
        int[] curPos = startPos.clone();
        if(Objects.equals(Direction, "Up")){
            if(!(curPos[0]+2 >= 9)){
                curPos[0] = curPos[0] + 2;
                if(!(curPos[1]-1 <= 0)){
                    curPos[1]--;
                    ChessPosition newPos = new ChessPosition(curPos[0],curPos[1]);
                    if(!((board.getPiece(newPos) != null) && (board.getPiece(newPos).getTeamColor() == board.getPiece(myPosition).getTeamColor()))){
                        ChessMove newMove = new ChessMove(myPosition, newPos);
                        pieceMoves.add(newMove);
                    }
                }
                curPos[1] = startPos[1];
                if(!(curPos[1]+1 >= 9)){
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
                if(!(curPos[1]-1 <= 0)){
                    curPos[1]--;
                    ChessPosition newPos = new ChessPosition(curPos[0],curPos[1]);
                    if(!((board.getPiece(newPos) != null) && (board.getPiece(newPos).getTeamColor() == board.getPiece(myPosition).getTeamColor()))){
                        ChessMove newMove = new ChessMove(myPosition, newPos);
                        pieceMoves.add(newMove);
                    }
                }
                curPos[1] = startPos[1];
                if(!(curPos[1]+1 >= 9)){
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
                curPos[0] = startPos[0];
                if(!(curPos[0]+1 >= 9)){
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
                curPos[0] = startPos[0];
                if(!(curPos[0]+1 >= 9)){
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
            //Checking if the pawn can capture left-side
            if((curPos[1]-1!=0)&&(board.getPiece(new ChessPosition(curPos[0],curPos[1]-1))!= null) && (board.getPiece(new ChessPosition(curPos[0],curPos[1]-1)).getTeamColor() != board.getPiece(myPosition).getTeamColor())){
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
            //Checking if the pawn can capture right-side
            if((curPos[1]+1!=9)&&(board.getPiece(new ChessPosition(curPos[0],curPos[1]+1))!= null) && (board.getPiece(new ChessPosition(curPos[0],curPos[1]+1)).getTeamColor() != board.getPiece(myPosition).getTeamColor())){
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
            if((curPos[1]-1!=0)&&(board.getPiece(new ChessPosition(curPos[0],curPos[1]-1))!= null) && (board.getPiece(new ChessPosition(curPos[0],curPos[1]-1)).getTeamColor() != board.getPiece(myPosition).getTeamColor())){
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
            if((curPos[1]+1!=9)&&(board.getPiece(new ChessPosition(curPos[0],curPos[1]+1))!= null) && (board.getPiece(new ChessPosition(curPos[0],curPos[1]+1)).getTeamColor() != board.getPiece(myPosition).getTeamColor())){
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
    private void king(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> pieceMoves){
        final int[] startPos = {myPosition.getRow(), myPosition.getColumn()};
        int[] curPos = startPos.clone();
        ChessPosition newPos;
        //Start top
        if(curPos[0]+1 != 9){
            curPos[0]++;
            //Top-Middle
            newPos = new ChessPosition(curPos[0],curPos[1]);
            if(!((board.getPiece(newPos) != null) && (board.getPiece(newPos).getTeamColor() == board.getPiece(myPosition).getTeamColor()))){
                ChessMove newMove = new ChessMove(myPosition, newPos);
                pieceMoves.add(newMove);
            }
            //Top-Left
            if(curPos[1]-1 != 0) {
                curPos[1]--;
                newPos = new ChessPosition(curPos[0],curPos[1]);
                if(!((board.getPiece(newPos) != null) && (board.getPiece(newPos).getTeamColor() == board.getPiece(myPosition).getTeamColor()))){
                    ChessMove newMove = new ChessMove(myPosition, newPos);
                    pieceMoves.add(newMove);
                }
            }
            curPos[1]=startPos[1];
            //Top-Right
            if(curPos[1]+1 != 9) {
                curPos[1]++;
                newPos = new ChessPosition(curPos[0],curPos[1]);
                if(!((board.getPiece(newPos) != null) && (board.getPiece(newPos).getTeamColor() == board.getPiece(myPosition).getTeamColor()))){
                    ChessMove newMove = new ChessMove(myPosition, newPos);
                    pieceMoves.add(newMove);
                }
            }
        }
        //Now bottom
        curPos = startPos.clone();
        if(curPos[0]-1 != 0){
            curPos[0]--;
            //Bottom-Middle
            newPos = new ChessPosition(curPos[0],curPos[1]);
            if(!((board.getPiece(newPos) != null) && (board.getPiece(newPos).getTeamColor() == board.getPiece(myPosition).getTeamColor()))){
                ChessMove newMove = new ChessMove(myPosition, newPos);
                pieceMoves.add(newMove);
            }
            //Bottom-Left
            if(curPos[1]-1 != 0) {
                curPos[1]--;
                newPos = new ChessPosition(curPos[0],curPos[1]);
                if(!((board.getPiece(newPos) != null) && (board.getPiece(newPos).getTeamColor() == board.getPiece(myPosition).getTeamColor()))){
                    ChessMove newMove = new ChessMove(myPosition, newPos);
                    pieceMoves.add(newMove);
                }
            }
            curPos[1]=startPos[1];
            //Bottom-Right
            if(curPos[1]+1 != 9) {
                curPos[1]++;
                newPos = new ChessPosition(curPos[0],curPos[1]);
                if(!((board.getPiece(newPos) != null) && (board.getPiece(newPos).getTeamColor() == board.getPiece(myPosition).getTeamColor()))){
                    ChessMove newMove = new ChessMove(myPosition, newPos);
                    pieceMoves.add(newMove);
                }
            }
        }
        //Left
        curPos = startPos.clone();
        if(curPos[1]-1 != 0) {
            curPos[1]--;
            newPos = new ChessPosition(curPos[0],curPos[1]);
            if(!((board.getPiece(newPos) != null) && (board.getPiece(newPos).getTeamColor() == board.getPiece(myPosition).getTeamColor()))){
                ChessMove newMove = new ChessMove(myPosition, newPos);
                pieceMoves.add(newMove);
            }
        }
        //Right
        curPos = startPos.clone();
        if(curPos[1]+1 != 9) {
            curPos[1]++;
            newPos = new ChessPosition(curPos[0],curPos[1]);
            if(!((board.getPiece(newPos) != null) && (board.getPiece(newPos).getTeamColor() == board.getPiece(myPosition).getTeamColor()))){
                ChessMove newMove = new ChessMove(myPosition, newPos);
                pieceMoves.add(newMove);
            }
        }
    }

    private void directional(String direction, ChessBoard board, ChessPosition myPosition, Collection<ChessMove> pieceMoves){
        final int[] startPos = {myPosition.getRow(),myPosition.getColumn()};
        int[] curPos = startPos.clone();
        ChessPosition newPos;
        do{
            switch (direction) {
                case "Up" -> curPos[1]++;
                case "Down" -> curPos[1]--;
                case "Left" -> curPos[0]--;
                case "Right" -> curPos[0]++;
                case "UpLeft" -> {
                    curPos[1]++;
                    curPos[0]--;
                }
                case "UpRight" -> {
                    curPos[1]++;
                    curPos[0]++;
                }
                case "DownLeft" -> {
                    curPos[1]--;
                    curPos[0]--;
                }
                case "DownRight" -> {
                    curPos[1]--;
                    curPos[0]++;
                }
            }
            if((curPos[0]==0)||(curPos[0]==9)||(curPos[1]==0)||(curPos[1]==9)){
                break;
            }
            newPos = new ChessPosition(curPos[0],curPos[1]);
            if(((board.getPiece(newPos)!=null)&&(board.getPiece(newPos).getTeamColor()==board.getPiece(myPosition).getTeamColor()))){
                break;
            }
            pieceMoves.add(new ChessMove(myPosition,newPos));
        }while(!((board.getPiece(newPos)!=null)&&(board.getPiece(newPos).getTeamColor()!=board.getPiece(myPosition).getTeamColor())));
    }
}
