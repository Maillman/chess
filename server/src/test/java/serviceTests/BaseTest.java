package serviceTests;

import Model.User;
import dataAccess.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import passoffTests.testClasses.TestException;
import service.ClearService;
import service.GameService;
import service.UserService;

public abstract class BaseTest {
    protected static User existingUser;

    protected static User newUser;
    protected static UserDAO testUserDAO;
    protected static AuthDAO testAuthDAO;
    protected static GameDAO testGameDAO;
    protected static UserDAO actualUserDAO;
    protected static AuthDAO actualAuthDAO;
    protected static GameDAO actualGameDAO;
    protected static UserService register;
    protected static UserService login;
    protected static GameService create;
    protected static ClearService clear;
    @BeforeAll
    public static void init() throws TestException {
        //MemoryDatabase
        testUserDAO = new memoryUserDAO();
        testAuthDAO = new memoryAuthDAO();
        testGameDAO = new memoryGameDAO();
        actualUserDAO = new memoryUserDAO();
        actualAuthDAO = new memoryAuthDAO();
        actualGameDAO = new memoryGameDAO();
        existingUser = new User("ExistingUser","existingUserPassword","eu@mail.com");
        newUser = new User("newUser","newPassword","new@Email.com");
    }
    @BeforeEach
    public void setUp() throws TestException {
        testUserDAO.clear();
        testAuthDAO.clear();
        testGameDAO.clear();
        actualUserDAO.clear();
        actualAuthDAO.clear();
        actualGameDAO.clear();
        actualUserDAO.createUser(existingUser);
        testUserDAO.createUser(existingUser);
        register = new UserService(testUserDAO,testAuthDAO);
        login = new UserService(testUserDAO, testAuthDAO);
        create = new GameService(testAuthDAO, testGameDAO);
        clear = new ClearService(testUserDAO, testAuthDAO, testGameDAO);
    }
}
