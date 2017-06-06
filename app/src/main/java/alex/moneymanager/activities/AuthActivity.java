package alex.moneymanager.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import alex.moneymanager.R;
import alex.moneymanager.fragments.ForgotPasswordFragment;
import alex.moneymanager.fragments.LoginFragment;
import alex.moneymanager.fragments.RegistrationFragment;
import alex.moneymanager.fragments.WelcomeFragment;
import butterknife.BindView;

public class AuthActivity extends BaseActivity
        implements WelcomeFragment.OnFragmentInteractionListener,
        LoginFragment.OnFragmentInteractionListener {

    @BindView(R.id.toolbar_auth)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        setSupportActionBar(toolbar);
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowTitleEnabled(false);
//        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fl_container_auth, WelcomeFragment.newInstance(), WelcomeFragment.TAG)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getSupportFragmentManager().findFragmentById(R.id.fl_container_auth) instanceof WelcomeFragment) {
            updateMenuByFragment(WelcomeFragment.TAG);
        }
    }

    @Override
    public void showLoginScreen() {
        getSupportFragmentManager().beginTransaction()
                .hide(getSupportFragmentManager().findFragmentByTag(WelcomeFragment.TAG))
                .add(R.id.fl_container_auth, LoginFragment.newInstance(), LoginFragment.TAG)
                .addToBackStack(LoginFragment.TAG)
                .commit();
    }

    @Override
    public void showRegistrationScreen() {
        getSupportFragmentManager().beginTransaction()
                .hide(getSupportFragmentManager().findFragmentByTag(WelcomeFragment.TAG))
                .add(R.id.fl_container_auth, RegistrationFragment.newInstance(), RegistrationFragment.TAG)
                .addToBackStack(RegistrationFragment.TAG)
                .commit();
    }

    @Override
    public void showForgotPasswordScreen() {
        getSupportFragmentManager().beginTransaction()
                .hide(getSupportFragmentManager().findFragmentByTag(WelcomeFragment.TAG))
                .add(R.id.fl_container_auth, ForgotPasswordFragment.newInstance(), ForgotPasswordFragment.TAG)
                .addToBackStack(ForgotPasswordFragment.TAG)
                .commit();
    }

    public void updateMenuByFragment(String fragmentTag) {
        switch (fragmentTag) {
            case WelcomeFragment.TAG:
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    getSupportActionBar().setDisplayShowTitleEnabled(false);
                }
                break;
            case LoginFragment.TAG:
            case RegistrationFragment.TAG:
            case ForgotPasswordFragment.TAG:
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setDisplayShowTitleEnabled(false);
                }
                break;
        }
    }
}