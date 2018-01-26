package qi.liang.liu.onlinebanking.dao;

import org.springframework.data.repository.CrudRepository;
import qi.liang.liu.onlinebanking.domain.Appointment;

import java.util.List;

public interface AppointmentDao extends CrudRepository<Appointment, Long>{
    List<Appointment> findAll();
}
