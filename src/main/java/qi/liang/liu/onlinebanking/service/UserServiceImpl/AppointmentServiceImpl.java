package qi.liang.liu.onlinebanking.service.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qi.liang.liu.onlinebanking.dao.AppointmentDao;
import qi.liang.liu.onlinebanking.domain.Appointment;
import qi.liang.liu.onlinebanking.service.AppointmentService;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    AppointmentDao appointmentDao;
    @Override
    public Appointment createAppointment(Appointment appointment) {
        return appointmentDao.save(appointment);
    }

    @Override
    public List<Appointment> findAll() {
        return appointmentDao.findAll();
    }

    @Override
    public Appointment findAppointment(Long id) {
        return appointmentDao.findOne(id);
    }

    @Override
    public void confirmAppointment(Long id) {
        Appointment appointment = findAppointment(id);
        appointment.setConfirmed(true);
        appointmentDao.save(appointment);
    }
}
