package qi.liang.liu.onlinebanking.dao;

import org.springframework.data.repository.CrudRepository;
import qi.liang.liu.onlinebanking.domain.Recipient;

import java.util.Set;

public interface RecipientDao extends CrudRepository<Recipient, Long>{
    Set<Recipient> findAll();
    Recipient findByName(String recipientName);
    void deleteByName(String recipientName);
}
