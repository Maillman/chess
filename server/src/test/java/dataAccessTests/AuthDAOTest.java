package dataAccessTests;

import Model.Auth;

import dataAccess.*;
import org.junit.jupiter.api.*;
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
        newAuth = new Auth("newAuthToken","newUsername");
    }

    @BeforeEach
    public void setUp() throws TestException, DataAccessException {
        testAuthDAO.clear();
        testAuthDAO.createAuth(existingAuth.getAuthToken(),existingAuth.getUsername());
    }

    @Test
    @Order(1)
    @DisplayName("Insert Auth Test")
    public void insert() throws TestException, DataAccessException {
        //Inserting an auth into the database.
        testAuthDAO.createAuth(newAuth.getAuthToken(),newAuth.getUsername());
        //check if user can be found in database.
        Assertions.assertEquals(newAuth, testAuthDAO.getAuth(newAuth.getAuthToken()),"Auth wasn't properly inserted into database");
    }

    @Test
    @Order(2)
    @DisplayName("Get Auth Test")
    public void get() throws TestException, DataAccessException {
        //check if existing user can be found in database.
        Assertions.assertEquals(existingAuth, testAuthDAO.getAuth(existingAuth.getAuthToken()),"Get didn't return user properly");
    }

    @Test
    @Order(3)
    @DisplayName("Invalid Get Auth Test")
    public void invalidGet() throws TestException, DataAccessException {
        //check if auth can be found in database.
        Assertions.assertNull(testAuthDAO.getAuth(newAuth.getAuthToken()), "Auth not null");
    }

    @Test
    @Order(4)
    @DisplayName("Delete Auth Test")
    public void delete() throws TestException, DataAccessException {
        //delete the existing user in the database.
        testAuthDAO.deleteAuth(existingAuth.getAuthToken());
        //check if users database cleared by running a negative test.
        Assertions.assertNull(testAuthDAO.getAuth(existingAuth.getAuthToken()), "Auth not properly deleted!");
    }

    @Test
    @Order(4)
    @DisplayName("Invalid Delete Auth Test")
    public void invalidDelete() throws TestException, DataAccessException {
        //delete the existing user in the database.
        testAuthDAO.deleteAuth(newAuth.getAuthToken());
        //check if users database cleared by running a negative test.
        Assertions.assertEquals(existingAuth, testAuthDAO.getAuth(existingAuth.getAuthToken()), "Auth deleted when shouldn't!");
        Assertions.assertNull(testAuthDAO.getAuth(newAuth.getAuthToken()), "Auth not properly deleted!");
    }
    @Test
    @Order(5)
    @DisplayName("Clear and Invalid Get Test")
    public void clear() throws TestException, DataAccessException {
        //assert database is filled.
        Assertions.assertEquals(existingAuth, testAuthDAO.getAuth(existingAuth.getAuthToken()),"Get didn't return user properly");
        //clear the database.
        testAuthDAO.clear();
        //check if users database cleared by running a negative test.
        Assertions.assertNull(testAuthDAO.getAuth(existingAuth.getAuthToken()), "Auth not null");
    }
}
