package qi.liang.liu.onlinebanking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import qi.liang.liu.onlinebanking.domain.Appointment;
import qi.liang.liu.onlinebanking.domain.User;
import qi.liang.liu.onlinebanking.service.AppointmentService;
import qi.liang.liu.onlinebanking.service.UserService;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {
    @Autowired
    UserService userService;

    @Autowired
    AppointmentService appointmentService;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createAppointment(Model model){
        Appointment appointment = new Appointment();
        model.addAttribute("appointment", appointment);
        model.addAttribute("dateString", "");
        return "appointment";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createAppointmentPOST(@ModelAttribute("appointment") Appointment appointment,
                                        @ModelAttribute("dateString") String dateString,
                                        Principal principal,
                                        Model model) throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date date = format.parse(dateString);
        appointment.setDate(date);

        User user = userService.findByUsername(principal.getName());
        appointment.setUser(user);
        appointmentService.createAppointment(appointment);
        return "redirect:/userFront";
    }
}
