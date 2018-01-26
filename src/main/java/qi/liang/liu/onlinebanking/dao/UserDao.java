package qi.liang.liu.onlinebanking.dao;

import org.springframework.data.repository.CrudRepository;
import qi.liang.liu.onlinebanking.domain.User;

public interface UserDao extends CrudRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
}
