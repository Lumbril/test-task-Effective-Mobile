package effective.mobile.code.services;

import effective.mobile.code.entities.Role;
import effective.mobile.code.entities.User;

import java.util.List;

public interface UserServices {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String login, String roleName);
    User getUser(String login);
    List<User> getAll();
}
