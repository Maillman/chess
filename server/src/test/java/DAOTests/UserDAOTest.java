package DAOTests;

import Model.User;
import dataAccess.*;
import org.junit.jupiter.api.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import passoffTests.testClasses.TestException;

@SuppressWarnings("unused")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserDAOTest {
    protected static UserDAO testUserDAO;
    protected static User existingUser;
    protected static User newUser;
    @BeforeAll
    public static void init() throws TestException {
        //SQLDatabase
        testUserDAO = new SQLUserDAO();
        existingUser = new User("ExistingUser","existingUserPassword","eu@mail.com");
        newUser = new User("NewUser","newUserPassword","nu@mail.com");
    }

    @BeforeEach
    public void setUp() throws TestException, DataAccessException {
        testUserDAO.clear();
        testUserDAO.createUser(existingUser);
    }

    @Test
    @Order(1)
    @DisplayName("Insert User Test")
    public void insert() throws TestException, DataAccessException {
        //Inserting a user into the database.
        testUserDAO.createUser(newUser);
        //check if user can be found in database.
        Assertions.assertEquals(testUserDAO.getUser(newUser.getUsername()),newUser,"The User has not been inserted correctly!");
    }
    @Test
    @Order(2)
    @DisplayName("Insert Multiple Users Test")
    public void insertMultiple() throws TestException, DataAccessException {
        //Inserting users into the database.
        testUserDAO.createUser(newUser);
        User anotherUser = new User("AnotherUser","myPassword","anu@mail.com");
        testUserDAO.createUser(anotherUser);
        //check if users can be found in database.
        Assertions.assertEquals(testUserDAO.getUser(anotherUser.getUsername()),anotherUser,"The User has not been inserted correctly!");
        Assertions.assertEquals(testUserDAO.getUser(newUser.getUsername()),newUser,"The User has not been inserted correctly!");
    }
    @Test
    @Order(3)
    @DisplayName("Get Test")
    public void get() throws TestException, DataAccessException {
        //check if user can be found in database.
        Assertions.assertEquals(testUserDAO.getUser(existingUser.getUsername()),existingUser,"Get not working correctly!");
    }
    @Test
    @Order(4)
    @DisplayName("Invalid Get Test")
    public void invalidGet() throws TestException, DataAccessException {
        //trying to get a user not registered in database.
        Assertions.assertNull(testUserDAO.getUser(newUser.getUsername()), "User not null");
    }
    @Test
    @Order(5)
    @DisplayName("Clear Test")
    public void clear() throws TestException, DataAccessException {
        //clear the database
        testUserDAO.clear();
        //check if users database cleared by running a negative test.
        Assertions.assertNull(testUserDAO.getUser(existingUser.getUsername()), "User not cleared");
    }
}
