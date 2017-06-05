package alex.moneymanager.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import javax.inject.Inject;

import alex.moneymanager.R;
import alex.moneymanager.application.MoneyManagerApplication;
import alex.moneymanager.dialogs.ErrorDialogFragment;
import alex.moneymanager.entities.network.PlainPassword;
import alex.moneymanager.entities.network.User;
import alex.moneymanager.presenters.LoginPresenter;
import alex.moneymanager.utils.PreferenceUtil;
import alex.moneymanager.utils.SystemUtils;
import alex.moneymanager.views.LoginView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginView {

    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassword;

    @Inject
    SystemUtils systemUtils;
    @Inject
    LoginPresenter presenter;

    private ProgressDialog progressDialog;

    private String validationErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        ((MoneyManagerApplication) getApplication()).component().inject(this);
        presenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        login();
    }

    @Override
    public void loginSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginFailed() {
        Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void showErrorDialog() {
        String errorMessage;
        if (systemUtils.isConnected()) {
            errorMessage = "Error while loading data";
        } else {
            errorMessage = "No internet connection";
        }

        ErrorDialogFragment fragment = ErrorDialogFragment.newInstance(
                errorMessage,
                "Ok",
                "Try again",
                new ErrorDialogFragment.OnClickListener() {
                    @Override
                    public void onPositiveButtonClick() {
                    }

                    @Override
                    public void onNegativeButtonClick() {
                        login();
                    }
                }
        );

        fragment.show(getSupportFragmentManager(), ErrorDialogFragment.TAG);
    }

    private void login() {
        if (systemUtils.isConnected()) {
            if (isValid()) {
                presenter.loginUser(new User(
                        getEmail(),
                        new PlainPassword(getPassword(), getPassword())
                ));
            } else {
                Toast.makeText(this, validationErrorMessage, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValid() {
        if (getEmail().isEmpty()) {
            validationErrorMessage = "No email";
            return false;
        }

        if (getPassword().isEmpty()) {
            validationErrorMessage = "No password";
            return false;
        }

        return true;
    }

    private String getEmail() {
        return etEmail.getText().toString();
    }

    private String getPassword() {
        return etPassword.getText().toString();
    }
}