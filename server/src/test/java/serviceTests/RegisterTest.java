package serviceTests;

import Model.Auth;
import dataAccess.DataAccessException;
import org.junit.jupiter.api.*;
import passoffTests.testClasses.TestException;

@SuppressWarnings("unused")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RegisterTest extends BaseTest {

    @Test
    @Order(1)
    @DisplayName("Register User Test")
    public void registerUser() throws TestException, DataAccessException {
        //register test
        Auth authToken = register.registerUser(newUser);
        actualUserDAO.createUser(newUser);
        //check if the user has been registered
        //user is in the /user database
        Assertions.assertEquals(actualUserDAO.getUser(newUser.getUsername()),testUserDAO.getUser(newUser.getUsername()),"User not register in database.");
        //authToken is in the /auth database
        Assertions.assertEquals(authToken, testAuthDAO.getAuth(authToken.getAuthToken()), "authToken not registered in database.");
    }

    @Test
    @Order(2)
    @DisplayName("Register More than Once Test")
    public void registerTwice() throws TestException, DataAccessException {
        //register test
        Auth authToken = register.registerUser(newUser);
        actualUserDAO.createUser(newUser);
        //check if the user has been registered
        //user is in the /user database
        Assertions.assertEquals(actualUserDAO.getUser(newUser.getUsername()),testUserDAO.getUser(newUser.getUsername()),"User not register in database.");
        //authToken is in the /auth database
        Assertions.assertEquals(authToken, testAuthDAO.getAuth(authToken.getAuthToken()), "authToken not registered in database.");
        //attempt to register again
        Assertions.assertThrows(DataAccessException.class, () -> register.registerUser(newUser),"Register not throwing exception!");
    }
}
