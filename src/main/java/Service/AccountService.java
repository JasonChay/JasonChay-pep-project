package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    public AccountDAO accountDAO;

    public AccountService(){
        this.accountDAO = new AccountDAO();
    }

    // Constructor when provided an AccountDAO for use in mock testing
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account registerAccount(Account account) {
        // To register a new account, it must have a username that is not blank, a password of at least 4 characters, and its username must not already exist
        if (account.getUsername().equals("")) {
            return null;
        }
        if (account.getPassword().length() < 4) {
            return null;
        }
        if (accountDAO.getAccountByUsername(account.getUsername()) != null) {
            return null;
        }
        return accountDAO.registerAccount(account);
    }

    public Account processLogin(Account account) {
        // will return null if there is no matching account
        return accountDAO.processLogin(account);
    }
}


