package dataAccess;

import Model.Auth;

public interface AuthDAO {
    Auth createAuth(String authToken, String username);

    Auth getAuth(String authToken);

    void deleteAuth(String authToken);

    void clear();
}
