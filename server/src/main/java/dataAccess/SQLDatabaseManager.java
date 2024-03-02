package dataAccess;

import java.sql.SQLException;

public class SQLDatabaseManager {

    public void initializeDatabase() throws DataAccessException {
        configureDatabase();
    }
    private final String stateInit = "use chess;";
    private final String stateUser =
            """
                    create table IF NOT EXISTS users (
                    	username VARCHAR(255) NOT NULL,
                    	password VARCHAR(255) NOT NULL,
                        email VARCHAR(255) NOT NULL,
                        primary key (username)
                    );
            """;
    private final String stateAuth =
            """
                    create table IF NOT EXISTS auths (
                    	token VARCHAR(255) NOT NULL,
                    	username VARCHAR(255) NOT NULL,
                        primary key (token)
                    );
            """;
    private final String stateGame =
            """
                    create table IF NOT EXISTS games (
                    	id INT NOT NULL AUTO_INCREMENT,
                        whiteusername VARCHAR(255),
                        blackusername VARCHAR(255),
                    	gamename VARCHAR(255),
                        chessgame longtext NOT NULL,
                        primary key (id)
                    );
            """;
    private final String[] createStatements = {
            stateInit,
            stateUser,
            stateAuth,
            stateGame
    };
    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException("SQLException!");
        }
    }
}
