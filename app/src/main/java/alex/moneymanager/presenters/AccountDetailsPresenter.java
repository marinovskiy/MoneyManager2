package alex.moneymanager.presenters;

import alex.moneymanager.entities.network.NetworkOperation;
import alex.moneymanager.views.AccountDetailsView;
import alex.moneymanager.views.AccountsView;

public interface AccountDetailsPresenter extends BasePresenter<AccountDetailsView> {

    void loadAccount(int organizationId, int accountId);

    void editOperation(int organizationId, int accountId, int operationId, NetworkOperation operation);

    void deleteOperation(int organizationId, int accountId, int operationId);

}