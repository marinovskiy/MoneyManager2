package alex.moneymanager.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import javax.inject.Inject;

import alex.moneymanager.R;
import alex.moneymanager.activities.AuthActivity;
import alex.moneymanager.activities.MainActivity;
import alex.moneymanager.application.MoneyManagerApplication;
import alex.moneymanager.dialogs.ErrorDialogFragment;
import alex.moneymanager.utils.PreferenceUtil;
import alex.moneymanager.utils.SystemUtils;
import alex.moneymanager.views.ForgotPasswordView;
import alex.moneymanager.views.RegistrationView;
import butterknife.BindView;
import butterknife.OnClick;

public class RegistrationFragment extends BaseFragment implements RegistrationView {

    public static final String TAG = "RegistrationFragment";

    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_first_name)
    EditText etFirstName;
    @BindView(R.id.et_last_name)
    EditText etLastName;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_password_confirm)
    EditText etPasswordConfirm;
    @BindView(R.id.switch_gender)
    Switch switchGender;

    @Inject
    SystemUtils systemUtils;
    @Inject
    PreferenceUtil preferenceUtil;

    private ProgressDialog progressDialog;

    private String validationErrorMessage;

    public RegistrationFragment() {
    }

    public static RegistrationFragment newInstance() {
        return new RegistrationFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AuthActivity) getActivity()).updateMenuByFragment(TAG);

        ((MoneyManagerApplication) getActivity().getApplication()).component().inject(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick(R.id.btn_register)
    public void onViewClicked() {

    }

    @Override
    public void registrationSuccess() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void registrationFailed() {
        Toast.makeText(getContext(), "Registration failed", Toast.LENGTH_SHORT).show();
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

                    }
                }
        );

        fragment.show(getChildFragmentManager(), ErrorDialogFragment.TAG);
    }

    private void register() {
        if (systemUtils.isConnected()) {
            if (isValid()) {
//                presenter
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

        return true;
    }

    private String getEmail() {
        return etEmail.getText().toString();
    }
}