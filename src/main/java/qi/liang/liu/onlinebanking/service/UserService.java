package qi.liang.liu.onlinebanking.service;

import qi.liang.liu.onlinebanking.domain.User;
import qi.liang.liu.onlinebanking.domain.security.UserRole;

import java.util.Set;

public interface UserService {
    void saveUser(User user);
    User findByUsername(String username);
    User findByEmail(String email);
    boolean checkUserExists(String username, String email);
    boolean checkEmailExists(String email);
    boolean checkUsernameExists(String username);

    User create(User user, Set<UserRole> userRoles);
}
