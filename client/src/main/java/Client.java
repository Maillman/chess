import java.util.Scanner;

import Model.User;
import ui.*;

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
                result = line.toLowerCase();
                evaluate(result);
            } catch (Throwable e){
                var msg = e.toString();
                System.out.println(msg);
            }
        }
    }

    private void evaluate(String result) {
        switch (result) {
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
                    evaluate("help");
                }
            }
        }
    }
    private boolean evalPreLogin(String result) {
        try {
            String[] resEval = result.split(" ");
            switch (resEval[0]) {
                case "register" -> {
                    if (resEval.length >= 4) {
                        authToken = server.register(new User(resEval[1], resEval[2], resEval[3])).getAuthToken();
                        System.out.println("You have successfully logged in!");
                    } else {
                        System.out.println("Not enough arguments where expected (Expected 4, Got " + resEval.length + ").");
                        System.out.println("Register <USERNAME> <PASSWORD> <EMAIL>");
                    }
                    return true;
                }
                default -> {
                    return false;
                }
            }
        }catch(ResponseException re){
            System.out.println(re.getMessage());
            evaluate("help");
            return true;
        }
    }
    private boolean evalPostLogin(String result){
        switch (result){
            default -> {
                return false;
            }
        }
    }

    private void postLoginUI() {
    }

    private void preLoginUI() {
        System.out.println("Execute any of the commands below!");
        helpPreLogin();
    }
    private void helpPreLogin(){
        System.out.print(EscapeSequences.SET_TEXT_COLOR_BLUE);
        System.out.println();
        System.out.println("Help: List possible commands.");
        System.out.println("Register <USERNAME> <PASSWORD> <EMAIL>: Creates an account to interact with the server.");
        System.out.println("Login <USERNAME> <PASSWORD>: Allows the ability to create, join, and list Chess Games in the server.");
        System.out.println("Quit: Exit the program.");
        System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE);
    }
    private void printPrompt() {
        System.out.print("\n" + EscapeSequences.SET_TEXT_COLOR_GREEN + " >>> " + EscapeSequences.SET_TEXT_COLOR_WHITE);
    }
}
