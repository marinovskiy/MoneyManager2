package alex.moneymanager.presenters;

import alex.moneymanager.entities.network.NetworkOperation;
import alex.moneymanager.views.AccountsView;

public interface AccountsPresenter extends BasePresenter<AccountsView> {

    void loadAccount(int accountId);

    void editOperation(int accountId, int operationId, NetworkOperation operation);

    void deleteOperation(int accountId, int operationId);

}