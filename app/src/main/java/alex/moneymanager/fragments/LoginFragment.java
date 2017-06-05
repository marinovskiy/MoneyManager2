package alex.moneymanager.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import javax.inject.Inject;

import alex.moneymanager.R;
import alex.moneymanager.activities.AuthActivity;
import alex.moneymanager.activities.MainActivity;
import alex.moneymanager.application.MoneyManagerApplication;
import alex.moneymanager.dialogs.ErrorDialogFragment;
import alex.moneymanager.entities.network.PlainPassword;
import alex.moneymanager.entities.network.User;
import alex.moneymanager.presenters.LoginPresenter;
import alex.moneymanager.utils.SystemUtils;
import alex.moneymanager.views.LoginView;
import butterknife.BindView;
import butterknife.OnClick;

public class LoginFragment extends BaseFragment implements LoginView {

    public static final String TAG = "LoginFragment";

    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassword;

    @Inject
    SystemUtils systemUtils;
    @Inject
    LoginPresenter presenter;

    private OnFragmentInteractionListener listener;

    private ProgressDialog progressDialog;

    private String validationErrorMessage;

    public LoginFragment() {
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AuthActivity) getActivity()).updateMenuByFragment(TAG);

        ((MoneyManagerApplication) getActivity().getApplication()).component().inject(this);
        presenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @OnClick({R.id.tv_forgot_password, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_forgot_password:
                if (listener != null) {
                    listener.showForgotPasswordScreen();
                }
                break;
            case R.id.btn_login:
                login();
                break;
        }
    }

    @Override
    public void loginSuccess() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void loginFailed() {
        Toast.makeText(getContext(), "Login failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getContext());
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

        fragment.show(getChildFragmentManager(), ErrorDialogFragment.TAG);
    }

    private void login() {
        if (systemUtils.isConnected()) {
            if (isValid()) {
                presenter.loginUser(new User(
                        getEmail(),
                        new PlainPassword(getPassword(), getPassword())
                ));
            } else {
                Toast.makeText(getContext(), validationErrorMessage, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "No internet connection", Toast.LENGTH_SHORT).show();
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

    public interface OnFragmentInteractionListener {

        void showForgotPasswordScreen();
    }
}