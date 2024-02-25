package serviceTests;

import Model.Auth;
import Model.Game;
import dataAccess.DataAccessException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Order;
import passoffTests.testClasses.TestException;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CreateListTest extends BaseTest{
    protected static List<Game> expectedGames = new ArrayList<>();
    protected static List<Game> resultedGames = new ArrayList<>();
    protected static Auth authToken;
    protected static Game newGame;
    @BeforeEach
    public void SetupGame() throws TestException, DataAccessException {
        expectedGames.clear();
        resultedGames.clear();
        //The game
        newGame = new Game(1,"","","newGame",null);
        //create game with authorized user
        authToken = register.login(existingUser);
        create.createGame(authToken.getAuthToken(),newGame);
        //getting list of games to compare
        expectedGames.add(newGame);
        //check if game is created
        Assertions.assertEquals(expectedGames,create.listGames(authToken.getAuthToken()),"The game was not created correctly!");
    }
    @Test
    @Order(1)
    @DisplayName("Create and List Several Games Test!")
    public void create_listGames() throws TestException, DataAccessException {
        //The games
        Game anotherGame = new Game(2,"","","ssehC",null);
        Game aNewGame = new Game(3,"","","newGame",null);
        //create games with authorized user
        authToken = register.login(existingUser);
        create.createGame(authToken.getAuthToken(),anotherGame);
        create.createGame(authToken.getAuthToken(),aNewGame);
        //getting list of games to compare
        expectedGames.add(anotherGame);
        expectedGames.add(aNewGame);
        //check if games were created
        Assertions.assertEquals(expectedGames,create.listGames(authToken.getAuthToken()),"The game was not created correctly!");
    }
    @Test
    @Order(2)
    @DisplayName("Unauthorized Create Game!")
    public void unCreateGame() throws TestException, DataAccessException {
        //The game
        Game newGame = new Game(1,"","","newGame",null);
        //create game with unauthorized user
        Assertions.assertThrows(DataAccessException.class, () -> create.createGame("UNLAWFUL CRIMINAL",newGame),"CreateGame not throwing exception!");
    }
}
