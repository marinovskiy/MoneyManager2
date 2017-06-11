package alex.moneymanager.views;

import java.util.List;

import alex.moneymanager.entities.db.Currency;

public interface NewAccountView extends BaseView {

    void setCurrencies(List<Currency> currencies);

    void accountAddedSuccess();

    void showProgressDialog();

    void dismissProgressDialog();

    void showErrorDialog(int errorCase);

}