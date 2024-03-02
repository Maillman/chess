package dataAccess;

import Model.Game;

import com.google.gson.Gson;

import java.util.List;

import java.util.ArrayList;
import java.util.Collection;
import java.sql.*;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class SQLGameDAO implements GameDAO{
    @Override
    public List<Game> listGames() {
        return null;
    }

    @Override
    public Game getGame(int gameID) {
        return null;
    }

    @Override
    public Game createGame(Game game) {
        return null;
    }

    @Override
    public Game updateGame(String username, Integer gameID, String playerColor, Game upGame) {
        return null;
    }

    @Override
    public void clear() {

    }
}
