package alex.moneymanager.views;

public interface RegistrationView extends BaseView {

    void registrationSuccess();

    void registrationFailed();

    void showProgressDialog();

    void dismissProgressDialog();

    void showErrorDialog();

}