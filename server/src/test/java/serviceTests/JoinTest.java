package serviceTests;

import Model.Auth;
import Model.Join;
import Model.Game;
import dataAccess.DataAccessException;
import org.junit.jupiter.api.*;
import passoffTests.testClasses.TestException;

@SuppressWarnings("unused")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JoinTest extends CreateListTest {
    @Test
    @Order(1)
    @DisplayName("Create, Join, and List a Game Test!")
    public void createJoinListGame() throws TestException, DataAccessException {
        //authorized user joins as White
        create.joinGame(authToken.getAuthToken(),new Join("White",1));
        //getting list of games to compare
        expectedGames.clear();
        newGame = new Game(1, existingUser.getUsername(), "","newGame",null);
        expectedGames.add(newGame);
        //check if game updated
        Assertions.assertEquals(expectedGames,create.listGames(authToken.getAuthToken()),"The game was not updated correctly!");
    }
    @Test
    @Order(1)
    @DisplayName("Two Joins as White Test!")
    public void doubleWhiteGame() throws TestException, DataAccessException {
        //authorized user joins as White
        create.joinGame(authToken.getAuthToken(),new Join("White",1));
        //register test
        Auth newAuthToken = register.registerUser(newUser);
        actualDatabase.createUser(newUser);
        //check if the user has been registered
        //user is in the /user database
        Assertions.assertEquals(actualDatabase.getUser(newUser.getUsername()),testDatabase.getUser(newUser.getUsername()),"User not register in database.");
        //authToken is in the /auth database
        Assertions.assertEquals(newAuthToken, testDatabase.getAuth(newAuthToken.getAuthToken()), "authToken not registered in database.");
        //new authorized user also attempts to join as White;
        Assertions.assertThrows(DataAccessException.class, () -> create.joinGame(newAuthToken.getAuthToken(),new Join("White",1)),"JoinGame not throwing exception (Attempted to join as white when already taken)!");
    }
}
