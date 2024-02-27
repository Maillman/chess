package server;

import Model.*;
import com.google.gson.Gson;
import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import dataAccess.memoryDataAccess;
import service.ClearService;
import service.UserService;
import service.GameService;
import spark.*;

import javax.xml.crypto.Data;
import java.awt.image.DataBuffer;

public class Server {
    private final UserService userService;
    private final ClearService clearService;
    private final GameService gameService;
    public Server(){
        DataAccess dataAccess = new memoryDataAccess();
        userService = new UserService(dataAccess);
        clearService = new ClearService(dataAccess);
        gameService = new GameService(dataAccess);
    }
    public Server(DataAccess dataAccess) {
        userService = new UserService(dataAccess);
        clearService = new ClearService(dataAccess);
        gameService = new GameService(dataAccess);
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.get("/", (req, res) -> "Hello, Chess Server!");
        //TODO: Start here!!!
        Spark.delete("/db", this::clear);
        Spark.post("/user", this::register);
        Spark.post("/session", this::login);
        Spark.delete("/session", this::logout);
        Spark.get("/game", this::list);
        Spark.post("/game", this::create);
        Spark.put("/game", this::join);
        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
    private Object clear(Request req, Response res) {
        clearService.clear();
        return "{}";
    }
    private Object register(Request req, Response res) {
        try {
            var user = new Gson().fromJson(req.body(), User.class);
            var auth = userService.registerUser(user);
            return new Gson().toJson(auth);
        }catch(DataAccessException dae){
            return DataAccessException(req, res, dae);
        }
    }

    private Object login(Request req, Response res) {
        try {
            var user = new Gson().fromJson(req.body(), User.class);
            var auth = userService.login(user);
            return new Gson().toJson(auth);
        }catch(DataAccessException dae){
            return DataAccessException(req, res, dae);
        }
    }

    private Object logout(Request req, Response res) {
        try{
        userService.logout(req.headers("authorization"));
        return "{}";
        }catch(DataAccessException dae){
            return DataAccessException(req, res, dae);
        }
    }
    private Object list(Request req, Response res){
        try{
            var listGames = gameService.listGames(req.headers("authorization"));
            return new Gson().toJson(new Games(listGames));
        }catch(DataAccessException dae){
            return DataAccessException(req, res, dae);
        }
    }
    private Object create(Request req, Response res) {
        try{
            var game = new Gson().fromJson(req.body(), Game.class);
            game = gameService.createGame(req.headers("authorization"),game);
            return new Gson().toJson(game);
        }catch(DataAccessException dae){
            return DataAccessException(req, res, dae);
        }
    }
    private Object join(Request req, Response res) {
        try{
            var joinGame = new Gson().fromJson(req.body(), Join.class);
            gameService.joinGame(req.headers("authorization"),joinGame);
            return "{}";
        }catch(DataAccessException dae){
            return DataAccessException(req, res, dae);
        }
    }
    private String DataAccessException(Request req, Response res, DataAccessException dae){
        switch (dae.getMessage()) {
            case "Bad Request!" -> {
                res.status(400);
                return "{ \"message\": \"Error: bad request\" }";
            }
            case "Unauthorized!" -> {
                res.status(401);
                return "{ \"message\": \"Error: unauthorized\" }";
            }
            case "Already Taken!" -> {
                res.status(403);
                return "{ \"message\": \"Error: already taken\" }";
            }
            default -> {
                res.status(500);
                return "{ \"message\": \"Error: description\" }";
            }
        }
    }
}
