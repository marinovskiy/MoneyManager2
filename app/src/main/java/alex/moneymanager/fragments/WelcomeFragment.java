package alex.moneymanager.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import alex.moneymanager.R;
import alex.moneymanager.activities.AuthActivity;
import butterknife.OnClick;

public class WelcomeFragment extends BaseFragment {

    public static final String TAG = "WelcomeFragment";

    private OnFragmentInteractionListener listener;

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

    public interface OnFragmentInteractionListener {

        void showLoginScreen();

        void showRegistrationScreen();
    }
}