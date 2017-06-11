package alex.moneymanager.views;

import alex.moneymanager.entities.db.Account;

public interface AccountDetailsView extends BaseView {

    void setAccount(Account account);

    void showProgressDialog();

    void dismissProgressDialog();

    void showErrorDialog(int errorCase);

}