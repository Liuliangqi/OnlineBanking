package qi.liang.liu.onlinebanking.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import qi.liang.liu.onlinebanking.dao.RecipientDao;
import qi.liang.liu.onlinebanking.domain.PrimaryAccount;
import qi.liang.liu.onlinebanking.domain.Recipient;
import qi.liang.liu.onlinebanking.domain.SavingsAccount;
import qi.liang.liu.onlinebanking.domain.User;
import qi.liang.liu.onlinebanking.service.TransactionService;
import qi.liang.liu.onlinebanking.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping(value = "/transfer")
public class TransferController {
    @Autowired
    UserService userService;
    @Autowired
    TransactionService transactionService;

    @RequestMapping(value = "/betweenAccounts", method = RequestMethod.GET)
    public String transferBetweenAccounts(Model model){
        model.addAttribute("transferFrom", "");
        model.addAttribute("transferTo", "");
        model.addAttribute("amount", "");

        return "betweenAccounts";
    }

    @RequestMapping(value = "/betweenAccounts", method = RequestMethod.POST)
    public String transferBetweenAccountsPOST(@ModelAttribute("transferFrom") String transferFrom,
                                              @ModelAttribute("transferTo") String transferTo,
                                              @ModelAttribute("amount") String amount,
                                              Principal principal) throws Exception{
        User user = userService.findByUsername(principal.getName());
        PrimaryAccount primaryAccount = user.getPrimaryAccount();
        SavingsAccount savingsAccount = user.getSavingsAccount();

        transactionService.betweenAccountsTransfer(transferFrom, transferTo, amount, primaryAccount, savingsAccount);
        return "redirect:/userFront";
    }

    @RequestMapping(value = "/recipient", method = RequestMethod.GET)
    public String recipient(Model model, Principal principal){
        List<Recipient> recipientList = transactionService.findRecipientList(principal);
        Recipient recipient = new Recipient();
        model.addAttribute("recipientList", recipientList);
        model.addAttribute("recipient", recipient);
        return "recipient";
    }

    @RequestMapping(value = "/recipient/save", method = RequestMethod.POST)
    public String recipientSave(@ModelAttribute("recipient") Recipient recipient, Principal principal){
        User user = userService.findByUsername(principal.getName());
        recipient.setUser(user);
        transactionService.saveRecipient(recipient);
        return "redirect:/transfer/recipient";
    }

    @RequestMapping(value = "/recipient/edit", method = RequestMethod.GET)
    public String recipientEdit(@RequestParam(value = "recipientName") String recipientName, Model model, Principal principal){
        Recipient recipient = transactionService.findRecipientByName(recipientName);
        List<Recipient> recipientList = transactionService.findRecipientList(principal);
        model.addAttribute("recipient", recipient);
        model.addAttribute("recipientList", recipientList);

        return "recipient";
    }

    @RequestMapping(value = "/recipient/delete", method = RequestMethod.GET)
    public String recipientDelete(@RequestParam("recipientName") String recipientName, Model model, Principal principal){
        transactionService.deleteRecipientByName(recipientName);
        List<Recipient> recipientList = transactionService.findRecipientList(principal);
        Recipient recipient = new Recipient();
        model.addAttribute("recipientList", recipientList);
        model.addAttribute("recipient", recipient);

        return "recipient";
    }

    @RequestMapping(value = "/toSomeoneElse", method = RequestMethod.GET)
    public String toSomeoneElse(Model model, Principal principal){
        List<Recipient> recipientList = transactionService.findRecipientList(principal);
        model.addAttribute("recipientList", recipientList);
        model.addAttribute("accountType", "");

        return "toSomeoneElse";
    }

    @RequestMapping(value = "/toSomeoneElse", method = RequestMethod.POST)
    public String toSomeoneElsePOST(@ModelAttribute("recipientName") String recipientName,
                                    @ModelAttribute("amount") String amount,
                                    @ModelAttribute("accountType") String accountType,
                                    Principal principal){
        Recipient recipient = transactionService.findRecipientByName(recipientName);
        User user = userService.findByUsername(principal.getName());
        transactionService.toSomeoneElseTransfer(recipient, accountType, amount, user.getPrimaryAccount(), user.getSavingsAccount());
        return "redirect:/userFront";
    }

}
