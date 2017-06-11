package alex.moneymanager.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import alex.moneymanager.R;
import alex.moneymanager.activities.AuthActivity;
import alex.moneymanager.application.MoneyManagerApplication;
import alex.moneymanager.dialogs.ErrorDialogFragment;
import alex.moneymanager.presenters.WelcomePresenter;
import alex.moneymanager.utils.PreferenceUtil;
import alex.moneymanager.utils.SystemUtils;
import alex.moneymanager.views.WelcomeView;
import butterknife.OnClick;

public class WelcomeFragment extends BaseFragment implements WelcomeView {

    public static final String TAG = "WelcomeFragment";

    @Inject
    SystemUtils systemUtils;
    @Inject
    PreferenceUtil preferenceUtil;
    @Inject
    WelcomePresenter presenter;

    private OnFragmentInteractionListener listener;

    private ProgressDialog progressDialog;

    public WelcomeFragment() {
    }

    public static WelcomeFragment newInstance() {
        return new WelcomeFragment();
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
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AuthActivity) getActivity()).updateMenuByFragment(TAG);

        ((MoneyManagerApplication) getActivity().getApplication()).component().inject(this);
        presenter.attachView(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (!preferenceUtil.isInitialDataLoaded()) {
            presenter.loadInitialData();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @OnClick({R.id.btn_login, R.id.btn_registration})
    public void onViewClicked(View view) {
        if (listener != null) {
            switch (view.getId()) {
                case R.id.btn_login:
                    listener.showLoginScreen();
                    break;
                case R.id.btn_registration:
                    listener.showRegistrationScreen();
                    break;
            }
        }
    }

    @Override
    public void dataLoadedSuccess() {
        // ignore
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
                        getActivity().finish();
                    }

                    @Override
                    public void onNegativeButtonClick() {
                        presenter.loadInitialData();
                    }
                }
        );

        fragment.setCancelable(false);

        fragment.show(getChildFragmentManager(), ErrorDialogFragment.TAG);
    }

    public interface OnFragmentInteractionListener {

        void showLoginScreen();

        void showRegistrationScreen();
    }
}