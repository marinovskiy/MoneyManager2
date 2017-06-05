package alex.moneymanager.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import javax.inject.Inject;

import alex.moneymanager.R;
import alex.moneymanager.application.MoneyManagerApplication;
import alex.moneymanager.dialogs.ErrorDialogFragment;
import alex.moneymanager.utils.PreferenceUtil;
import alex.moneymanager.utils.SystemUtils;
import alex.moneymanager.views.WelcomeView;
import butterknife.OnClick;

public class WelcomeActivity extends BaseActivity implements WelcomeView {

    @Inject
    SystemUtils systemUtils;
    @Inject
    PreferenceUtil preferenceUtil;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ((MoneyManagerApplication) getApplication()).component().inject(this);
//        presenter

        if (!preferenceUtil.isInitialDataLoaded()) {

        }
    }

    @OnClick({R.id.btn_login, R.id.btn_registration})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.btn_registration:
                startActivity(new Intent(this, RegistrationActivity.class));
                break;
        }
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
                        finish();
                    }

                    @Override
                    public void onNegativeButtonClick() {
//                        presenter.loadInitialData();
                    }
                }
        );

        fragment.show(getSupportFragmentManager(), ErrorDialogFragment.TAG);
    }
}