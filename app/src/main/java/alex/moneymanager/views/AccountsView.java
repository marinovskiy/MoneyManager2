package alex.moneymanager.views;

import java.util.List;

import alex.moneymanager.entities.db.Account;
import alex.moneymanager.entities.db.Operation;

public interface AccountsView extends BaseView {

    void setAccount(Account account);

    void showProgressDialog();

    void dismissProgressDialog();

    void showErrorDialog(int errorCase);

}