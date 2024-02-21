package dataAccess;
import Model.Auth;
import Model.User;

public interface DataAccess {
    User getUser(String username);

    void createUser(User user);
    Auth createAuth(String username);

    Auth getAuth(String authToken);

    void deleteAuth(String authToken);

    void clear();
}
