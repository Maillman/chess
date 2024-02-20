package dataAccess;
import Model.User;

public interface DataAccess {
    User getUser(String username);
}
