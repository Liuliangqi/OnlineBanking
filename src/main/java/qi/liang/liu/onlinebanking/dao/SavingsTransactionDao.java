package qi.liang.liu.onlinebanking.dao;

import org.springframework.data.repository.CrudRepository;
import qi.liang.liu.onlinebanking.domain.SavingsTransaction;

import java.util.Set;

public interface SavingsTransactionDao extends CrudRepository<SavingsTransaction, Long>{
    Set<SavingsTransaction> findAll();
}
