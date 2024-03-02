package dataAccess;

import com.google.gson.Gson;

import Model.Auth;

import java.util.ArrayList;
import java.util.Collection;
import java.sql.*;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class SQLAuthDAO implements AuthDAO{
    @Override
    public Auth createAuth(String authToken, String username) {
        return null;
    }

    @Override
    public Auth getAuth(String authToken) {
        return null;
    }

    @Override
    public void deleteAuth(String authToken) {

    }

    @Override
    public void clear() {

    }
}
