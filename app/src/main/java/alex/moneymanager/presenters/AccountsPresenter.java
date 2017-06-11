package alex.moneymanager.presenters;

import alex.moneymanager.views.AccountsView;

public interface AccountsPresenter extends BasePresenter<AccountsView> {

    void loadAccount(int accountId);

}