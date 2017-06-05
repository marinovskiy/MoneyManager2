package alex.moneymanager.views;

public interface LoginView extends BaseView {

    void loginSuccess();

    void loginFailed();

    void showProgressDialog();

    void dismissProgressDialog();

    void showErrorDialog();

}