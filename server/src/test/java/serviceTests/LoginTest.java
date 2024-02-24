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
@SuppressWarnings("unused")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginTest extends BaseTest {

    @Test
    @Order(1)
    @DisplayName("Login Existing User Test!")
    public void loginUser() throws TestException, DataAccessException {
        //login test
        Auth authToken = login.login(existingUser);
        //check if the user has been logged-in
        //user is in the /user database
        Assertions.assertEquals(actualDatabase.getUser(existingUser.getUsername()),testDatabase.getUser(existingUser.getUsername()),"User not register in database.");
        //authToken is in the /auth database
        Assertions.assertEquals(authToken, testDatabase.getAuth(authToken.getAuthToken()), "authToken not registered in database.");
    }

    @Test
    @Order(2)
    @DisplayName("Login New User Test!")
    public void loginNewUser() throws TestException {
        //attempt to log-in existing user
        Assertions.assertThrows(DataAccessException.class, () -> login.login(newUser),"Login not throwing exception!");
    }
}
