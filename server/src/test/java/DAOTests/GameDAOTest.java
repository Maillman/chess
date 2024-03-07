package DAOTests;


import Model.Game;

import chess.ChessGame;
import dataAccess.*;
import org.junit.jupiter.api.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import passoffTests.testClasses.TestException;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GameDAOTest {
    protected static GameDAO testGameDAO;
    protected static Game existingGame;
    protected static Game newGame;
    protected static List<Game> expectedGames = new ArrayList<>();
    protected static List<Game> resultedGames = new ArrayList<>();
    @BeforeAll
    public static void init() throws TestException {
        testGameDAO = new SQLGameDAO();
        existingGame = new Game(1,null,null,"aGameName",new ChessGame());
        newGame = new Game(2,null,null,"WOW!",new ChessGame());
    }

    @BeforeEach
    public void setUp() throws TestException, DataAccessException {
        testGameDAO.clear();
        testGameDAO.createGame(existingGame);
    }

    @Test
    @Order(1)
    @DisplayName("Create Game Test")
    public void create() throws TestException, DataAccessException {
        //create and put a game into the database.
        testGameDAO.createGame(newGame);
        //assert the game is inserted.
        existingGame = testGameDAO.getGame(newGame.getGameID());
        Assertions.assertEquals(newGame,testGameDAO.getGame(newGame.getGameID()),"Game not inserted properly!");
    }

    @Test
    @Order(2)
    @DisplayName("List Games Test")
    public void list() throws TestException, DataAccessException {
        //create and put a game into the database.
        testGameDAO.createGame(newGame);
        //getting list of games to compare
        expectedGames.add(existingGame);
        expectedGames.add(newGame);
        //check if games were created
        Assertions.assertEquals(expectedGames,testGameDAO.listGames(),"The game was not created correctly!");
    }
}
