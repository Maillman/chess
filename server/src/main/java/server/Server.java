package server;

import Model.User;
import com.google.gson.Gson;
import dataAccess.DataAccess;
import service.UserService;
import spark.*;

import javax.xml.crypto.Data;

public class Server {
    private final UserService userService;
    public Server(){
        final DataAccess defdataAccess = null;
        userService = new UserService(defdataAccess);
    }
    public Server(DataAccess dataAccess) {
        userService = new UserService(dataAccess);
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.get("/", (req, res) -> "Hello, Chess Server!");
        //TODO: Start here!!!
        Spark.post("/user", this::handleRequest);
        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private Object handleRequest(Request req, Response res) {
        var user = new Gson().fromJson(req.body(), User.class);
        user = userService.registerUser(user);
        return new Gson().toJson(user);
    }
}
