package dataAccess;

import Model.Game;

import chess.ChessGame;
import com.google.gson.Gson;

import java.util.List;

import java.util.ArrayList;
import java.util.Collection;
import java.sql.*;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class SQLGameDAO implements GameDAO{
    @Override
    public List<Game> listGames() throws DataAccessException{
        return null;
    }

    @Override
    public Game getGame(int gameID) throws DataAccessException{
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("select id, whiteusername, blackusername, gamename, chessgame from games where id=?", RETURN_GENERATED_KEYS)) {
                preparedStatement.setInt(1, gameID);
                try (var rs = preparedStatement.executeQuery()) {
                    rs.next();
                    String retWhiteUsername = rs.getString("whiteusername");
                    String retBlackUsername = rs.getString("blackusername");
                    String gameName = rs.getString("gamename");
                    ChessGame theGame = new Gson().fromJson(rs.getString("chessgame"),ChessGame.class);
                    return new Game(gameID,retWhiteUsername,retBlackUsername,gameName,theGame);
                }
            }
        }catch(SQLException se){
            throw new DataAccessException("SQL error!");
        }
    }

    @Override
    public Game createGame(Game game) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("insert into games (whiteusername, blackusername, gamename, chessgame) values (?, ?, ?, ?)", RETURN_GENERATED_KEYS)) {
                var json = new Gson().toJson(game.getGame());
                preparedStatement.setString(1,game.getWhiteUsername());
                preparedStatement.setString(2,game.getBlackUsername());
                preparedStatement.setString(3,game.getGameName());
                preparedStatement.setString(4,json);

                if(preparedStatement.executeUpdate()==1){
                    try(ResultSet generatedKeys = preparedStatement.getGeneratedKeys()){
                        generatedKeys.next();
                        int id = generatedKeys.getInt(1);
                        return new Game(id,game.getWhiteUsername(),game.getBlackUsername(),game.getGameName(),game.getGame());
                    }
                }else{
                    throw new SQLException("Couldn't execute SQL update!");
                }
            }
        }catch(SQLException se){
            throw new DataAccessException("SQL error!");
        }
    }

    @Override
    public Game updateGame(String username, Integer gameID, String playerColor, Game upGame) throws DataAccessException{
        return null;
    }

    @Override
    public void clear() throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("truncate Games")) {
                preparedStatement.executeUpdate();
            }
        }catch (SQLException se) {
            throw new DataAccessException("SQL error!");
        }
    }
}
