package serviceTests;

import Model.Auth;
import Model.User;
import chess.ChessGame;
import dataAccess.DataAccess;
import dataAccess.memoryDataAccess;
import org.junit.jupiter.api.*;
import passoffTests.testClasses.TestException;
import passoffTests.testClasses.TestModels;
import service.ClearService;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("unused")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClearTest {
    private static User existingUser;

    private static User newUser;

    private static TestModels.TestCreateRequest createRequest;
    private static DataAccess testDatabase;
    private static DataAccess actualDatabase;
    @BeforeAll
    public static void init() throws TestException {
        //MemoryDatabase
        testDatabase = new memoryDataAccess();
        actualDatabase = new memoryDataAccess();
    }
    @BeforeEach
    public void setUp() throws TestException {
        testDatabase.clear();
        actualDatabase.clear();
    }
    @Test
    @Order(1)
    @DisplayName("Clear Service Test")
    public void clearData() throws TestException {
        ClearService clear = new ClearService(testDatabase);
        //create filler users
        newUser = new User("newUser","newPassword","new@Email.com");
        testDatabase.createUser(newUser);
        newUser = new User("Maillman","MyBelovedPearl","WhitakerFamily@TheWorld.com");
        testDatabase.createUser(newUser);
        newUser = new User("PhoenixFke","MyBelovedPearl","WhitakerFamily@TheWorld.com");
        testDatabase.createUser(newUser);
        newUser = new User("Phoenix_Gamer","...21FDS-=_game","Leverage@TheWorld.com");
        testDatabase.createUser(newUser);
        //check if the database is filled
        Assertions.assertNotEquals(actualDatabase, testDatabase, "The database did not get filled");
        //clear the database
        clear.clear();
        //check if the database cleared
        Assertions.assertEquals(actualDatabase,testDatabase,"The database did not clear!");
    }

    @Test
    @Order(2)
    @DisplayName("Multiple Clears")
    public void clearManyData() throws TestException {
        //clear an empty database
        ClearService clear = new ClearService(testDatabase);
        clear.clear();
        //create filler users
        newUser = new User("newUser","newPassword","new@Email.com");
        testDatabase.createUser(newUser);
        newUser = new User("Maillman","MyBelovedPearl","WhitakerFamily@TheWorld.com");
        testDatabase.createUser(newUser);
        //clear the database
        clear.clear();
        Assertions.assertEquals(actualDatabase,testDatabase,"The database did not clear!");
        //create more filler users
        newUser = new User("Phoenix_Gamer","...21FDS-=_game","Leverage@TheWorld.com");
        testDatabase.createUser(newUser);
        actualDatabase.createUser(newUser);
        //check if the database is filled correctly post-clear
        Assertions.assertEquals(actualDatabase,testDatabase,"The database did not clear correctly!");
    }
}
