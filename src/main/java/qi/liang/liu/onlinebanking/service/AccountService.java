package qi.liang.liu.onlinebanking.service;

import qi.liang.liu.onlinebanking.domain.PrimaryAccount;
import qi.liang.liu.onlinebanking.domain.SavingsAccount;

import java.security.Principal;

public interface AccountService {
    PrimaryAccount createPrimaryAccount();
    SavingsAccount createSavingsAccount();
    void deposit(String accountType, double amount, Principal principal);
    void withdraw(String accountType, double amount, Principal principal);

}
