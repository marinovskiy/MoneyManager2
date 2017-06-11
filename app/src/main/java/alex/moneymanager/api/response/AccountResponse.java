package alex.moneymanager.api.response;

import alex.moneymanager.entities.db.Account;

public class AccountResponse {

    private Account account;

    public AccountResponse() {
    }

    public AccountResponse(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}