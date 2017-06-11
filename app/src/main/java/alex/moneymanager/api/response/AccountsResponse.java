package alex.moneymanager.api.response;

import java.util.List;

import alex.moneymanager.entities.db.Account;

public class AccountsResponse {

    private List<Account> accounts;

    public AccountsResponse() {
    }

    public AccountsResponse(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}