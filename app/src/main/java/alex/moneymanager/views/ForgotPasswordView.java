package alex.moneymanager.views;

public interface ForgotPasswordView extends BaseView {

    void forgotPasswordSuccess();

    void forgotPasswordFailed();

    void showProgressDialog();

    void dismissProgressDialog();

    void showErrorDialog();

}