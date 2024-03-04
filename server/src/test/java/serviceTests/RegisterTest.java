package serviceTests;

import Model.Auth;
import dataAccess.DataAccessException;
import org.junit.jupiter.api.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import passoffTests.testClasses.TestException;

@SuppressWarnings("unused")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RegisterTest extends BaseTest {
    @Test
    @Order(1)
    @DisplayName("Register User Test")
    public void registerUser() throws TestException, DataAccessException {
        //register test
        Auth auth = register.registerUser(newUser);
        actualUserDAO.createUser(hashedNewUser);
        //check if the user has been registered
        //user is in the /user database
        Assertions.assertEquals(actualUserDAO.getUser(newUser.getUsername()).getUsername(),testUserDAO.getUser(newUser.getUsername()).getUsername(),"User not register in database.");
        Assertions.assertTrue(encoder.matches(newUser.getPassword(), actualUserDAO.getUser(newUser.getUsername()).getPassword()),"Password not entered correctly");
        Assertions.assertEquals(actualUserDAO.getUser(newUser.getUsername()).getEmail(),testUserDAO.getUser(newUser.getUsername()).getEmail(),"Email not register in database.");
        //authToken is in the /auth database
        Assertions.assertEquals(auth, testAuthDAO.getAuth(auth.getAuthToken()), "authToken not registered in database.");
    }

    @Test
    @Order(2)
    @DisplayName("Register More than Once Test")
    public void registerTwice() throws TestException, DataAccessException {
        //register test
        Auth auth = register.registerUser(newUser);
        actualUserDAO.createUser(hashedNewUser);
        //check if the user has been registered
        //user is in the /user database
        Assertions.assertEquals(actualUserDAO.getUser(newUser.getUsername()).getUsername(),testUserDAO.getUser(newUser.getUsername()).getUsername(),"User not register in database.");
        Assertions.assertTrue(encoder.matches(newUser.getPassword(), actualUserDAO.getUser(newUser.getUsername()).getPassword()),"Password not entered correctly");
        Assertions.assertEquals(actualUserDAO.getUser(newUser.getUsername()).getEmail(),testUserDAO.getUser(newUser.getUsername()).getEmail(),"Email not register in database.");
        //authToken is in the /auth database
        Assertions.assertEquals(auth, testAuthDAO.getAuth(auth.getAuthToken()), "authToken not registered in database.");
        //attempt to register again
        Assertions.assertThrows(DataAccessException.class, () -> register.registerUser(newUser),"Register not throwing exception!");
    }
}
