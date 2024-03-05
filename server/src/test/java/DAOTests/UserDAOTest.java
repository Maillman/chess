package DAOTests;

import Model.User;
import dataAccess.*;
import org.junit.jupiter.api.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import passoffTests.testClasses.TestException;
public class UserDAOTest {
    protected static UserDAO testUserDAO;
    protected static UserDAO actualUserDAO;
    protected static User existingUser;
    protected static User newUser;
    @BeforeAll
    public static void init() throws TestException {
        //MemoryDatabase
        testUserDAO = new SQLUserDAO();
        existingUser = new User("ExistingUser","existingUserPassword","eu@mail.com");
    }

    @Test
    @Order(1)
    @DisplayName("Insert User Test")
    public void insert() throws TestException, DataAccessException {
        //Inserting a user into the database.
        testUserDAO.createUser(existingUser);
    }

    @Test
    @Order(2)
    @DisplayName("Get User Test")
    public void get() throws TestException, DataAccessException {
        //check if existing user can be found in database.
        Assertions.assertEquals(testUserDAO.getUser(existingUser.getUsername()),existingUser,"The User has not been inserted correctly!");
    }

    @Test
    @Order(3)
    @DisplayName("Clear and Invalid Get Test")
    public void clear() throws TestException, DataAccessException {
        //clear the database
        testUserDAO.clear();
        //check if users database cleared by running a negative test.
        Assertions.assertThrows(DataAccessException.class, () -> testUserDAO.getUser(existingUser.getUsername()),"Clear not throwing exception!");
    }
}
