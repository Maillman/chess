import chess.*;

import java.net.http.*;
import java.net.URI;
import java.time.Duration;

public class Main {

    public static void main(String[] args) {
        String port;
        if(args.length>0) {
            port = args[0];
        }else{
            port = "http://localhost:8080";
        }
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
        int maxRetries = 10;
        int retryInterval = 1000; // 1 second

        // Poll the server until it start up
        boolean serverStarted = false;
        for(int i = 0; i < maxRetries; i++){
            try{
                Thread.sleep(retryInterval);
                if(isServerUp(port)){
                    serverStarted = true;
                    break;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(serverStarted) {
            Client chessClient = new Client(port);
            chessClient.run();
        }else{
            System.out.print("Could not connect to server... Please start it first.");
        }
    }

    private static boolean isServerUp(String port) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(new URI(port)).timeout(Duration.ofMillis(1000)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return (response.statusCode() == 200);
    }
}