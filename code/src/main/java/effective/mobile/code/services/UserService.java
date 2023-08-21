package effective.mobile.code.services;

import effective.mobile.code.entities.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    User getUser(String login);
    List<User> getAll();
}
