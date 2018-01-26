package qi.liang.liu.onlinebanking.dao;

import org.springframework.data.repository.CrudRepository;
import qi.liang.liu.onlinebanking.domain.security.Role;

public interface RoleDao extends CrudRepository<Role, Integer> {
    Role findByName(String name);
}
