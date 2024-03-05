package DAOTests;

import Model.Auth;
import Model.User;
import dataAccess.*;
import org.junit.jupiter.api.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import passoffTests.testClasses.TestException;
@SuppressWarnings("unused")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthDAOTest {
    protected static AuthDAO testAuthDAO;
    protected static AuthDAO actualAuthDAO;
    protected static Auth existingAuth;
    protected static Auth newAuth;

    @BeforeAll
    public static void init() throws TestException {
        //SQLDatabase
        testAuthDAO = new SQLAuthDAO();
        existingAuth = new Auth("myAuthToken","myUsername");
    }

    @Test
    @Order(1)
    @DisplayName("Insert Auth Test")
    public void insert() throws TestException, DataAccessException {
        //Inserting an auth into the database.
        testAuthDAO.createAuth(existingAuth.getAuthToken(),existingAuth.getUsername());
    }

    @Test
    @Order(2)
    @DisplayName("Get Auth Test")
    public void get() throws TestException, DataAccessException {
        //check if existing user can be found in database.
        testAuthDAO.getAuth(existingAuth.getAuthToken());
    }

    @Test
    @Order(3)
    @DisplayName("Delete Auth Test")
    public void delete() throws TestException, DataAccessException {
        //delete the existing user in the database.
        testAuthDAO.deleteAuth(existingAuth.getAuthToken());
        //check if users database cleared by running a negative test.
        Assertions.assertThrows(DataAccessException.class, () -> testAuthDAO.getAuth(existingAuth.getAuthToken()),"Auth wasn't properly deleted!");
    }
    @Test
    @Order(4)
    @DisplayName("Clear and Invalid Get Test")
    public void clear() throws TestException, DataAccessException {
        //clear the database
        testAuthDAO.clear();
        //check if users database cleared by running a negative test.
        Assertions.assertThrows(DataAccessException.class, () -> testAuthDAO.getAuth(existingAuth.getAuthToken()),"Clear not throwing exception!");
    }
}
