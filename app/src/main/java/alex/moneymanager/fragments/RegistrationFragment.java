package alex.moneymanager.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Patterns;
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
import alex.moneymanager.entities.enums.Gender;
import alex.moneymanager.entities.db.PlainPassword;
import alex.moneymanager.entities.db.User;
import alex.moneymanager.presenters.RegistrationPresenter;
import alex.moneymanager.utils.PreferenceUtil;
import alex.moneymanager.utils.SystemUtils;
import alex.moneymanager.views.RegistrationView;
import butterknife.BindView;
import butterknife.OnClick;

public class RegistrationFragment extends BaseFragment implements RegistrationView {

    public static final String TAG = "RegistrationFragment";

    @BindView(R.id.et_first_name)
    EditText etFirstName;
    @BindView(R.id.et_last_name)
    EditText etLastName;
    @BindView(R.id.et_email)
    EditText etEmail;
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
    @Inject
    RegistrationPresenter presenter;

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
        presenter.attachView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @OnClick(R.id.btn_register)
    public void onViewClicked() {
        register();
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
        Toast.makeText(getContext(), "Помилка", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage(getString(R.string.progress_bar_registration));
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
            errorMessage = getString(R.string.msg_error_while_making_request);
        } else {
            errorMessage = getString(R.string.msg_error_no_internet);
        }

        ErrorDialogFragment fragment = ErrorDialogFragment.newInstance(
                errorMessage,
                getString(R.string.dialog_btn_ok),
                getString(R.string.dialog_btn_retry),
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
                presenter.registerUser(
                        new User(
                                getEmail(),
                                getFirstName(),
                                getLastName(),
                                new PlainPassword(getPassword(), getConfirmPassword()),
                                getGender()
                        )
                );
            } else {
                Toast.makeText(getContext(), validationErrorMessage, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), R.string.msg_error_no_internet, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValid() {
        if (getFirstName().isEmpty()) {
            validationErrorMessage = "Будь-ласка, введіть Ваше ім'я";
            return false;
        }

        if (getLastName().isEmpty()) {
            validationErrorMessage = "Будь-ласка, введіть Ваше прізвище";
            return false;
        }

        if (getEmail().isEmpty()) {
            validationErrorMessage = "Будь-ласка, введіть Вашу електронну пошту";
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches()) {
            validationErrorMessage = "Введена Вами електронна пошта не дійсна";
            return false;
        }

        if (getPassword().isEmpty()) {
            validationErrorMessage = "Будь-ласка, введіть пароль";
            return false;
        }

        if (getPassword().length() < 6) {
            validationErrorMessage = "Введений Вами пароль закороткой (мінумум 6 символів)";
            return false;
        }

        if (getPassword().contains(" ")) {
            validationErrorMessage = "Пароль не може містит пробілів";
            return false;
        }

        if (getConfirmPassword().isEmpty()) {
            validationErrorMessage = "Будь-ласка, підтвердіть пароль";
            return false;
        }

        if (!getPassword().equals(getConfirmPassword())) {
            validationErrorMessage = "Паролі не співпадають";
            return false;
        }

        return true;
    }

    private String getFirstName() {
        return etFirstName.getText().toString();
    }

    private String getLastName() {
        return etLastName.getText().toString();
    }

    private String getEmail() {
        return etEmail.getText().toString();
    }

    private String getPassword() {
        return etPassword.getText().toString();
    }

    private String getConfirmPassword() {
        return etPasswordConfirm.getText().toString();
    }

    private String getGender() {
        return Gender.MALE;
//        return etEmail.getText().toString();
    }
}