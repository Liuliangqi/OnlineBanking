package qi.liang.liu.onlinebanking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import qi.liang.liu.onlinebanking.dao.RoleDao;
import qi.liang.liu.onlinebanking.domain.PrimaryAccount;
import qi.liang.liu.onlinebanking.domain.SavingsAccount;
import qi.liang.liu.onlinebanking.domain.User;
import qi.liang.liu.onlinebanking.domain.security.UserRole;
import qi.liang.liu.onlinebanking.service.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;


@Controller
public class HomeController {
    @Autowired
    private UserService userService;

    /**
     * Directly use dao here is not good
     */
    @Autowired
    private RoleDao roleDao;

    @RequestMapping("/")
    public String home(){
        return "redirect:/index";
    }


    /**
     * Because we have the dependency of thymeleaf
     * Whenever we return a string, Spring boot will automatically
     * looking for the template using the string as the name,
     * the extension will be html
     * @return
     */
    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String sugnup(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signupPost(@ModelAttribute("user") User user, Model model){
        // check the whether the user is already exists
        // and then check email and username separately in order to set the value of the error information
        if(userService.checkUserExists(user.getUsername(),user.getEmail())){
            if(userService.checkEmailExists(user.getEmail())){
                model.addAttribute("emailExists", true);
            }
            if(userService.checkUsernameExists(user.getUsername())){
                model.addAttribute("usernameExists", true);
            }
            return "signup";
        }else{
            Set<UserRole> userRoles = new HashSet<>();
            userRoles.add(new UserRole(user, roleDao.findByName("ROLE_USER")));
            userService.create(user, userRoles);
            return "redirect:/";
        }
    }

    @RequestMapping("/userFront")
    public String userFront(Principal principal, Model model){
        User user = userService.findByUsername(principal.getName());
        PrimaryAccount primaryAccount = user.getPrimaryAccount();
        SavingsAccount savingsAccount = user.getSavingsAccount();

        model.addAttribute("primaryAccount", primaryAccount);
        model.addAttribute("savingsAccount", savingsAccount);

        return "userFront";
    }
}
