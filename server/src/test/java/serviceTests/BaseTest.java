package serviceTests;

import Model.Game;
import Model.User;
import dataAccess.DataAccess;
import dataAccess.memoryDataAccess;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import passoffTests.testClasses.TestException;
import passoffTests.testClasses.TestModels;
import service.ClearService;
import service.GameService;
import service.UserService;

public abstract class BaseTest {
    protected static User existingUser;

    protected static User newUser;

    protected static TestModels.TestCreateRequest createRequest;
    protected static DataAccess testDatabase;
    protected static DataAccess actualDatabase;
    protected static UserService register;
    protected static UserService login;
    protected static GameService create;
    protected static ClearService clear;
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
        login = new UserService(testDatabase);
        create = new GameService(testDatabase);
        clear = new ClearService(testDatabase);
    }
}
