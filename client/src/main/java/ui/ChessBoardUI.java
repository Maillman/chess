package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static ui.EscapeSequences.*;

public class ChessBoardUI {
    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int LINE_WIDTH_IN_CHARS = 1;
    private static final String EMPTY = "   ";
    private static final String[] LETTERS = new String[]{"a","b","c","d","e","f","g","h"};

    private enum Perspective {
        WHITE,
        BLACK,
        WATCH
    }

    public static void main(String[] args) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);
        ChessGame newGame = new ChessGame();
        ChessBoard initBoard = new ChessBoard();
        initBoard.resetBoard();
        newGame.setBoard(initBoard);
        out.println("White's Perspective:");
        drawChessBoard(out, newGame, Perspective.WHITE);
        out.println();
        out.println("Black's Perspective:");
        drawChessBoard(out, newGame, Perspective.BLACK);
        out.println();
        out.println("Watcher's Perspective:");
        drawChessBoard(out, newGame, Perspective.WATCH);
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void drawChessBoard(PrintStream out, ChessGame theGame, Perspective perspective) {
        int boardRow, boardColumn;
        if (perspective== Perspective.BLACK) {
            boardRow = 0;
            boardColumn = 9;
        }else{
            boardRow = 9;
            boardColumn = 0;
        }
        while(boardRow>=0&&boardRow<BOARD_SIZE_IN_SQUARES+2){
            while(boardColumn>=0&&boardColumn<BOARD_SIZE_IN_SQUARES+2){
                if(boardRow==0||boardRow==9){
                    //Letters
                    setOuter(out);
                    if(boardColumn==0||boardColumn==9){
                        //The corners
                        out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
                    }else{
                        //The letters
                        out.print(" " + LETTERS[boardColumn-1] + " ");
                    }
                }else{
                    //Main stuff
                    if(boardColumn==0||boardColumn==9){
                        //The numbers
                        setOuter(out);
                        out.print(" " + boardRow + " ");
                    }else{
                        //Main board!
                        ChessPiece piece = theGame.getBoard().getPiece(new ChessPosition(boardRow,boardColumn));
                        if(boardRow%2==0 ^ boardColumn%2==0){
                            setWhiteSquare(out);
                        }else{
                            setBlackSquare(out);
                        }
                        if(piece!=null) {
                            switch (piece.getTeamColor()){
                                case WHITE -> setWhitePiece(out);
                                case BLACK -> setBlackPiece(out);
                            }
                            switch (piece.getPieceType()) {
                                case BISHOP -> out.print(" B ");
                                case ROOK -> out.print(" R ");
                                case QUEEN -> out.print(" Q ");
                                case KNIGHT -> out.print(" N ");
                                case KING -> out.print(" K ");
                                case PAWN -> out.print(" P ");
                            }
                        }else{
                            out.print("   ");
                        }
                    }
                }
                //Ending statement
                if(perspective==Perspective.BLACK){
                    boardColumn--;
                }else{
                    boardColumn++;
                }
            }
            out.print(SET_BG_COLOR_DARK_GREY);
            out.println();
            //Ending statement
            if(perspective==Perspective.BLACK){
                boardRow++;
                boardColumn = 9;
            }else{
                boardRow--;
                boardColumn = 0;
            }
        }
    }

    private static void setOuter(PrintStream out) {
        out.print(SET_BG_COLOR_DARK_GREEN);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void setBlackSquare(PrintStream out) {
        out.print(SET_BG_COLOR_DARK_GREY);
    }

    private static void setWhiteSquare(PrintStream out) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
    }

    private static void setBlackPiece(PrintStream out) {
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private static void setWhitePiece(PrintStream out) {
        out.print(SET_TEXT_COLOR_WHITE);
    }
}
