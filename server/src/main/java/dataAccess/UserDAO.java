package dataAccess;

import Model.User;

public interface UserDAO {
    User getUser(String username);

    void createUser(User user);

    void clear();
}
