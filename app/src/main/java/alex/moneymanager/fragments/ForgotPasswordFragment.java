package alex.moneymanager.fragments;

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
import alex.moneymanager.application.MoneyManagerApplication;
import alex.moneymanager.utils.PreferenceUtil;
import alex.moneymanager.utils.SystemUtils;
import butterknife.BindView;
import butterknife.OnClick;

public class ForgotPasswordFragment extends BaseFragment {

    public static final String TAG = "ForgotPasswordFragment";

    @BindView(R.id.et_email)
    EditText etEmail;

    @Inject
    SystemUtils systemUtils;
    @Inject
    PreferenceUtil preferenceUtil;

    private String validationErrorMessage;

    public ForgotPasswordFragment() {
    }

    public static ForgotPasswordFragment newInstance() {
        return new ForgotPasswordFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forgot_password, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AuthActivity) getActivity()).updateMenuByFragment(TAG);

        ((MoneyManagerApplication) getActivity().getApplication()).component().inject(this);
    }

    @OnClick(R.id.btn_send)
    public void onViewClicked() {
        send();
    }

    private void send() {
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