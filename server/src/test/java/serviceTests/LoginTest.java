package serviceTests;

import Model.Auth;
import dataAccess.DataAccessException;
import org.junit.jupiter.api.*;
import passoffTests.testClasses.TestException;

@SuppressWarnings("unused")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginTest extends BaseTest {

    @Test
    @Order(1)
    @DisplayName("Login Existing User Test!")
    public void loginUser() throws TestException, DataAccessException {
        //login test
        Auth auth = login.login(existingUser);
        //check if the user has been logged-in
        //user is in the /user database
        Assertions.assertEquals(actualUserDAO.getUser(existingUser.getUsername()),testUserDAO.getUser(existingUser.getUsername()),"User not register in database.");
        //authToken is in the /auth database
        Assertions.assertEquals(auth, testAuthDAO.getAuth(auth.getAuthToken()), "authToken not registered in database.");
    }

    @Test
    @Order(2)
    @DisplayName("Login New User Test!")
    public void loginNewUser() throws TestException {
        //attempt to log-in existing user
        Assertions.assertThrows(DataAccessException.class, () -> login.login(newUser),"Login not throwing exception!");
    }
}
