package qi.liang.liu.onlinebanking.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import qi.liang.liu.onlinebanking.domain.*;
import qi.liang.liu.onlinebanking.service.AccountService;
import qi.liang.liu.onlinebanking.service.UserService;

import java.security.Principal;
import java.util.Set;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    UserService userService;

    @Autowired
    AccountService accountService;

    @RequestMapping("/primaryAccount")
    public String primaryAccount(Model model, Principal principal){
        User user = userService.findByUsername(principal.getName());

        PrimaryAccount primaryAccount = user.getPrimaryAccount();

        Set<PrimaryTransaction> primaryTransactionList = primaryAccount.getPrimaryTransactionList();

        model.addAttribute("primaryAccount", primaryAccount);
        model.addAttribute("primaryTransactionList", primaryTransactionList);

        return "primaryAccount";
    }

    @RequestMapping("/savingsAccount")
    public String savingsAccount(Model model, Principal principal){
        User user = userService.findByUsername(principal.getName());

        SavingsAccount savingsAccount = user.getSavingsAccount();
        Set<SavingsTransaction> savingsTransactionList = savingsAccount.getSavingsTransactionList();

        model.addAttribute("savingsAccount", savingsAccount);
        model.addAttribute("savingsTransactionList", savingsTransactionList);

        return "savingsAccount";
    }

    @RequestMapping(value = "/deposit", method = RequestMethod.GET)
    public String deposit(Model model){
        model.addAttribute("accountType", "");
        model.addAttribute("amount", "");

        return "deposit";
    }

    @RequestMapping(value = "/deposit", method = RequestMethod.POST)
    public String depositePOST(@ModelAttribute("amount") String amount, @ModelAttribute("accountType") String accountType, Principal principal){
        accountService.deposit(accountType, Double.parseDouble(amount), principal);
        return "redirect:/userFront";
    }

    @RequestMapping(value = "/withdraw", method = RequestMethod.GET)
    public String withdraw(Model model){
        model.addAttribute("accountType", "");
        model.addAttribute("amount", "");
        return "withdraw";
    }

    @RequestMapping(value = "/withdraw", method = RequestMethod.POST)
    public String withdrawPOST(@ModelAttribute("accountType") String accountType, @ModelAttribute("amount") String amount, Principal principal){
        accountService.withdraw(accountType, Double.parseDouble(amount), principal);
        return "redirect:/userFront";
    }
}
