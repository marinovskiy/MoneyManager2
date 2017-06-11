package alex.moneymanager.views;

import java.util.List;

import alex.moneymanager.entities.db.Account;
import alex.moneymanager.entities.db.Organization;

public interface MainView extends BaseView {

    void setData(List<Account> accounts, List<Organization> organizations);

    void showProgressDialog();

    void dismissProgressDialog();

    void showErrorDialog();

}