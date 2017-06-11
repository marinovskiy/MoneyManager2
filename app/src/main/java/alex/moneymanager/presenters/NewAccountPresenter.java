package alex.moneymanager.presenters;

import alex.moneymanager.entities.network.NetworkAccount;
import alex.moneymanager.views.NewAccountView;

public interface NewAccountPresenter extends BasePresenter<NewAccountView> {

    void loadCurrencies();

    void addNewUserAccount(NetworkAccount account);

    void addNewOrganizationAccount(int organizationId, NetworkAccount account);

    void loadAccountDb(int accountId);

//    void editAccount(int accountId, NetworkAccount account);

    void editOrganizationAccount(int organizationId, int accountId, NetworkAccount account);

}