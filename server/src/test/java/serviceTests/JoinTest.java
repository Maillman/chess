package serviceTests;

import Model.Auth;
import Model.Join;
import Model.Game;
import chess.ChessGame;
import dataAccess.DataAccessException;
import org.junit.jupiter.api.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import passoffTests.testClasses.TestException;

@SuppressWarnings("unused")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JoinTest extends CreateListTest {
    @Test
    @Order(1)
    @DisplayName("Create, Join, and List a Game Test!")
    public void createJoinListGame() throws TestException, DataAccessException {
        //authorized user joins as White
        create.joinGame(auth.getAuthToken(),new Join("WHITE",1));
        //getting list of games to compare
        expectedGames.clear();
        newGame = new Game(1, existingUser.getUsername(), "","newGame",new ChessGame());
        expectedGames.add(newGame);
        //check if game updated
        Assertions.assertEquals(expectedGames,create.listGames(auth.getAuthToken()),"The game was not updated correctly!");
    }
    @Test
    @Order(1)
    @DisplayName("Two Joins as White Test!")
    public void doubleWhiteGame() throws TestException, DataAccessException {
        //authorized user joins as White
        create.joinGame(auth.getAuthToken(),new Join("WHITE",1));
        //register test
        Auth newAuthToken = register.registerUser(newUser);
        actualUserDAO.createUser(hashedNewUser);
        //check if the user has been registered
        //user is in the /user database
        Assertions.assertEquals(actualUserDAO.getUser(newUser.getUsername()).getUsername(),testUserDAO.getUser(newUser.getUsername()).getUsername(),"User not register in database.");
        Assertions.assertTrue(encoder.matches(newUser.getPassword(), actualUserDAO.getUser(newUser.getUsername()).getPassword()),"Password not entered correctly");
        Assertions.assertEquals(actualUserDAO.getUser(newUser.getUsername()).getEmail(),testUserDAO.getUser(newUser.getUsername()).getEmail(),"Email not register in database.");
        //authToken is in the /auth database
        Assertions.assertEquals(newAuthToken, testAuthDAO.getAuth(newAuthToken.getAuthToken()), "authToken not registered in database.");
        //new authorized user also attempts to join as White;
        Assertions.assertThrows(DataAccessException.class, () -> create.joinGame(newAuthToken.getAuthToken(),new Join("WHITE",1)),"JoinGame not throwing exception (Attempted to join as white when already taken)!");
    }
}
