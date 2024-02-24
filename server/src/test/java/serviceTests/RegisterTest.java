package serviceTests;

import Model.Auth;
import Model.User;
import chess.ChessGame;
import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import dataAccess.memoryDataAccess;
import org.junit.jupiter.api.*;
import passoffTests.testClasses.TestException;
import passoffTests.testClasses.TestModels;
import service.UserService;

import static org.junit.jupiter.api.Assertions.*;
public class RegisterTest {
    private static User existingUser;

    private static User newUser;

    private static TestModels.TestCreateRequest createRequest;
    private static DataAccess testDatabase;
    private static DataAccess actualDatabase;
    private static UserService register;
    @BeforeAll
    public static void init() throws TestException {
        //MemoryDatabase
        testDatabase = new memoryDataAccess();
        actualDatabase = new memoryDataAccess();
        existingUser = new User("ExistingUser","existingUserPassword","eu@mail.com");
        newUser = new User("newUser","newPassword","new@Email.com");
    }
    @BeforeEach
    public void setUp() throws TestException {
        testDatabase.clear();
        actualDatabase.clear();
        actualDatabase.createUser(existingUser);
        testDatabase.createUser(existingUser);
        register = new UserService(testDatabase);
    }
    @Test
    @Order(1)
    @DisplayName("Register User Test")
    public void registerUser() throws TestException, DataAccessException {
        //register test
        Auth authToken = register.registerUser(newUser);
        actualDatabase.createUser(newUser);
        //check if the user has been registered
        //user is in the /user database
        Assertions.assertEquals(actualDatabase.getUser(newUser.getUsername()),testDatabase.getUser(newUser.getUsername()),"User not register in database.");
        //authToken is in the /auth database
        Assertions.assertEquals(authToken, testDatabase.getAuth(authToken.getAuthToken()), "authToken not registered in database.");
    }

    @Test
    @Order(2)
    @DisplayName("Register More than Once Test")
    public void registerTwice() throws TestException, DataAccessException {
        //register test
        Auth authToken = register.registerUser(newUser);
        actualDatabase.createUser(newUser);
        //check if the user has been registered
        //user is in the /user database
        Assertions.assertEquals(actualDatabase.getUser(newUser.getUsername()),testDatabase.getUser(newUser.getUsername()),"User not register in database.");
        //authToken is in the /auth database
        Assertions.assertEquals(authToken, testDatabase.getAuth(authToken.getAuthToken()), "authToken not registered in database.");
        //attempt to register again
        Assertions.assertThrows(DataAccessException.class, () -> register.registerUser(newUser),"Register not throwing exception!");
    }
}
