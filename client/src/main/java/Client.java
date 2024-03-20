import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import Model.*;
import chess.ChessGame;
import ui.*;

import static ui.ChessBoardUI.Perspective.*;

public class Client {
    private final ServerFacade server;
    private String authToken = null;

    public Client(String serverURL){
        server = new ServerFacade(serverURL);
    }

    public void run(){
        Scanner scanner = new Scanner(System.in);
        var result = "";
        System.out.println();
        System.out.println("Welcome to the chess client! ♘");
        preLoginUI();
        while(!result.equals("quit")){
            printPrompt();
            String line = scanner.nextLine();
            try {
                String[] splitResult = line.split(" ");
                splitResult[0] = splitResult[0].toLowerCase();
                evaluate(splitResult);
                result = splitResult[0];
            } catch (Throwable e){
                var msg = e.toString();
                System.out.println(msg);
            }
        }
    }

    private void evaluate(String[] result) {
        switch (result[0]) {
            case "quit" -> {
                System.out.println("You have exited the program!");
            }
            case "help" -> {
                if(authToken==null) {
                    preLoginUI();
                }else{
                    postLoginUI();
                }
            }
            default -> {
                boolean validCMD;
                if(authToken==null){
                    validCMD = evalPreLogin(result);
                }else{
                    validCMD = evalPostLogin(result);
                }
                if(!validCMD){
                    System.out.println("That is not a valid command!");
                    evaluate(new String[]{"help"});
                }
            }
        }
    }
    private boolean evalPreLogin(String[] result) {
        try {
            boolean isSuccessful = true;
            switch (result[0]) {
                case "register" -> {
                    if (result.length >= 4) {
                        authToken = server.register(new User(result[1], result[2], result[3])).getAuthToken();
                        System.out.println("You have successfully registered and logged in!");
                        postLoginUI();
                    } else {
                        System.out.println("Not enough arguments where expected (Expected 4, Got " + result.length + ").");
                        System.out.println("Register <USERNAME> <PASSWORD> <EMAIL>");
                    }
                }
                case "login" -> {
                    if (result.length >= 3) {
                        authToken = server.login(new User(result[1], result[2], null)).getAuthToken();
                        System.out.println("You have successfully logged in!");
                        postLoginUI();
                    } else {
                        System.out.println("Not enough arguments where expected (Expected 3, Got " + result.length + ").");
                        System.out.println("Login <USERNAME> <PASSWORD>");
                    }
                }
                default -> {
                    isSuccessful = false;
                }
            }
            return isSuccessful;
        }catch(ResponseException re){
            System.out.println(re.getMessage());
            evaluate(new String[]{"help"});
            return true;
        }
    }
    private boolean evalPostLogin(String[] result){
        try {
            boolean isSuccessful = true;
            switch (result[0]) {
                case "logout" -> {
                    server.logout(authToken);
                    authToken = null;
                    System.out.println("You have successfully logged out!");
                    preLoginUI();
                }
                case "list" -> {
                    List<Game> games = server.list(authToken).getGames();
                    System.out.println(games.toString());
                }
                case "create" -> {
                    if (result.length >= 2) {
                        Game game = server.create(new Game(null,null,null,result[1],new ChessGame()),authToken);
                        System.out.println("The Game " + result[1] + " was created! It's ID is " + game.getGameID() + ".");
                    } else {
                        System.out.println("Not enough arguments where expected (Expected 2, Got " + result.length + ").");
                        System.out.println("Create <ID>");
                    }
                }
                case "join" -> {
                    if (result.length >= 3) {
                        joinObserve(result);
                    }else{
                        System.out.println("Not enough arguments where expected (Expected 3, Got " + result.length + ").");
                        System.out.println("Join <ID> [WHITE|BLACK|<empty>]");
                    }
                }
                case "observe" -> {
                    if(result.length >= 2) {
                        joinObserve(result);
                    }else{
                        System.out.println("Not enough arguments where expected (Expected 2, Got " + result.length + ").");
                        System.out.println("Observe <ID>");
                    }
                }
                default -> {
                    isSuccessful = false;
                }
            }
            return isSuccessful;
        }catch(ResponseException re){
            System.out.println(re.getMessage());
            evaluate(new String[]{"help"});
            return true;
        }
    }

    private void joinObserve(String[] result) throws ResponseException {
        Join join;
        if(Objects.equals(result[0], "join")){
            join = new Join(result[2],Integer.parseInt(result[1]));
            System.out.println("You have successfully joined the game!");
        }else{
            join = new Join("WATCH",Integer.parseInt(result[1]));
            System.out.println("You are observing the game");
        }
        Game game = server.join(join,authToken);
        assert join != null;
        clientDrawChessBoard(game, join);
    }

    private void clientDrawChessBoard(Game game, Join join) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        ChessBoardUI.Perspective persp;
        if(Objects.equals(join.getPlayerColor(), "WHITE")){
            persp = WHITE;
        }else if(Objects.equals(join.getPlayerColor(), "BLACK")){
            persp = BLACK;
        }else{
            persp = WATCH;
        }
        ChessBoardUI.drawChessBoard(out,game.getGame(),persp);
    }

    private void postLoginUI() {
        uiPreHelper();
        helpPostLogin();
        uiPostHelper();
    }

    private void preLoginUI() {
        uiPreHelper();
        helpPreLogin();
        uiPostHelper();
    }
    private void uiPreHelper(){
        System.out.println("Execute any of the commands below!");
        System.out.print(EscapeSequences.SET_TEXT_COLOR_BLUE);
        System.out.println();
    }
    private void uiPostHelper(){
        System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE);
    }
    private void helpPreLogin(){
        System.out.println("Help: List possible commands.");
        System.out.println("Register <USERNAME> <PASSWORD> <EMAIL>: Creates an account to interact with the server.");
        System.out.println("Login <USERNAME> <PASSWORD>: Allows the ability to create, join, and list Chess Games in the server.");
        System.out.println("Quit: Exit the program.");
    }
    private void helpPostLogin(){
        System.out.println("Help: List possible commands.");
        System.out.println("Logout: Log out of the server.");
        System.out.println("List: Lists all of the games that currently exist in the server.");
        System.out.println("Create: <GAMENAME>: Creates a game of chess to join and play.");
        System.out.println("Join <ID> [WHITE|BLACK|<empty>]: Allows the ability to play (or observe if no color is specified) the specified game of chess.");
        System.out.println("Observer <ID>: Allows the ability to observe the specified game of chess.");
        System.out.println("Quit: Exit the program.");
    }
    private void printPrompt() {
        System.out.print("\n" + EscapeSequences.SET_TEXT_COLOR_GREEN + " >>> " + EscapeSequences.SET_TEXT_COLOR_WHITE);
    }
}
