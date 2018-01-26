package qi.liang.liu.onlinebanking.dao;

import org.springframework.data.repository.CrudRepository;
import qi.liang.liu.onlinebanking.domain.PrimaryAccount;

public interface PrimaryAccountDao extends CrudRepository<PrimaryAccount, Long>{
    PrimaryAccount findByAccountNumber(int number);
}
