import java.util.Scanner;

import ui.*;

public class Client {
    private String authToken = null;

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
                System.out.println("That is not a valid command!");
                evaluate("help");
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
