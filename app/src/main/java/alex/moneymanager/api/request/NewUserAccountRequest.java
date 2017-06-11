package alex.moneymanager.api.request;

import alex.moneymanager.entities.network.NetworkAccount;

public class NewUserAccountRequest {

    private NetworkAccount account;

    public NewUserAccountRequest() {
    }

    public NewUserAccountRequest(NetworkAccount account) {
        this.account = account;
    }

    public NetworkAccount getAccount() {
        return account;
    }

    public void setAccount(NetworkAccount account) {
        this.account = account;
    }
}