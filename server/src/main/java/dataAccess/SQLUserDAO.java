package dataAccess;

import com.google.gson.Gson;

import Model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.sql.*;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;
public class SQLUserDAO implements UserDAO{


    @Override
    public User getUser(String username) {
        return null;
    }

    @Override
    public void createUser(User user) {

    }

    @Override
    public void clear() {

    }
}
