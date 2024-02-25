package serviceTests;

import Model.Auth;
import Model.User;
import Model.Game;
import chess.ChessGame;
import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import dataAccess.memoryDataAccess;
import org.junit.jupiter.api.*;
import passoffTests.testClasses.TestException;
import passoffTests.testClasses.TestModels;
import service.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SuppressWarnings("unused")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CreateGameTest extends BaseTest{
    private static List<Game> expectedGames = new ArrayList<>();
    private static List<Game> resultedGames = new ArrayList<>();
    @Test
    @Order(1)
    @DisplayName("Create and List Game Test!")
    public void create_listGame() throws TestException, DataAccessException {
        //The game
        Game newGame = new Game(1,"","","newGame",null);
        //create game with authorized user
        Auth authToken = register.login(existingUser);
        create.createGame(authToken.getAuthToken(),newGame);
        actualDatabase.createGame(newGame);
        //getting list of games to compare
        expectedGames.add(newGame);
        //check if game is created
        Assertions.assertEquals(expectedGames,create.listGames(authToken.getAuthToken()),"The game was not created correctly!");
    }
}
