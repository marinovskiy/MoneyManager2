package alex.moneymanager.presenters;

import alex.moneymanager.entities.network.NetworkAccount;
import alex.moneymanager.views.NewAccountView;

public interface NewAccountPresenter extends BasePresenter<NewAccountView> {

    void loadCurrencies();

    void addNewUserAccount(NetworkAccount account);

}