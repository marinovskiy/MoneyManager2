package alex.moneymanager.views;

public interface WelcomeView extends BaseView {

    void dataLoadedSuccess();

    void showProgressDialog();

    void dismissProgressDialog();

    void showErrorDialog();

}