package qi.liang.liu.onlinebanking.dao;

import org.springframework.data.repository.CrudRepository;
import qi.liang.liu.onlinebanking.domain.PrimaryTransaction;

import java.util.Set;

public interface PrimaryTransactionDao extends CrudRepository<PrimaryTransaction, Long> {
    Set<PrimaryTransaction> findAll();
}
