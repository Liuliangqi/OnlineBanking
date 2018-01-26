package qi.liang.liu.onlinebanking.service;

import qi.liang.liu.onlinebanking.domain.Appointment;

import java.util.List;

public interface AppointmentService {
    Appointment createAppointment(Appointment appointment);

    List<Appointment> findAll();

    Appointment findAppointment(Long id);

    void confirmAppointment(Long id);
}
