package qi.liang.liu.onlinebanking.service.UserServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import qi.liang.liu.onlinebanking.dao.RoleDao;
import qi.liang.liu.onlinebanking.dao.UserDao;
import qi.liang.liu.onlinebanking.domain.User;
import qi.liang.liu.onlinebanking.domain.security.UserRole;
import qi.liang.liu.onlinebanking.service.AccountService;
import qi.liang.liu.onlinebanking.service.UserService;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private AccountService accountService;

    @Override
    public void saveUser(User user) {
        userDao.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public boolean checkUserExists(String username, String email) {
        if(checkUsernameExists(username) || checkEmailExists(email))
            return true;
        return false;
    }

    @Override
    public boolean checkEmailExists(String email) {
        if(userDao.findByEmail(email) != null)
            return true;
        return false;
    }

    @Override
    public boolean checkUsernameExists(String username) {
        if(userDao.findByUsername(username) != null)
            return true;
        return false;
    }

    @Override
    public User create(User user, Set<UserRole> userRoles) {
        User localUser = userDao.findByUsername(user.getUsername());
        if(localUser == null){
            // first encrypt password
            String encryptedPassword = passwordEncoder.encode(user.getPassword());
            // change the literal password
            user.setPassword(encryptedPassword);
            for(UserRole ur : userRoles){
                roleDao.save(ur.getRole());
            }
            user.getUserRoles().addAll(userRoles);
            user.setPrimaryAccount(accountService.createPrimaryAccount());
            user.setSavingsAccount(accountService.createSavingsAccount());

            localUser = userDao.save(user);
        }else{
            LOG.info("User with username {} already exist. Nothing will be done. ", user.getUsername());
        }

        return localUser;
    }
}
