package alex.moneymanager.activities;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import alex.moneymanager.R;
import alex.moneymanager.fragments.AccountsFragment;
import alex.moneymanager.fragments.HomeFragment;
import alex.moneymanager.fragments.OrganizationsFragment;
import alex.moneymanager.fragments.ProfileFragment;
import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.drawer_layout_main)
    DrawerLayout drawerLayout;
    @BindView(R.id.toolbar_main)
    Toolbar toolbar;

    private String lastFragmentTag;
    private HomeFragment homeFragment;
    private AccountsFragment accountsFragment;
    private OrganizationsFragment organizationsFragment;
    private ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_drawer);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        homeFragment = HomeFragment.newInstance();
        accountsFragment = AccountsFragment.newInstance();
        organizationsFragment = OrganizationsFragment.newInstance();
        profileFragment = ProfileFragment.newInstance();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fl_container_main, homeFragment, HomeFragment.TAG)
                    .commit();

            lastFragmentTag = HomeFragment.TAG;
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick({R.id.ll_action_nav_home, R.id.ll_action_nav_accounts, R.id.ll_action_nav_organizations,
            R.id.ll_action_nav_profile, R.id.ll_action_nav_settings, R.id.ll_action_nav_logout})
    public void onDrawerViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_action_nav_home:
                if (!HomeFragment.TAG.equals(lastFragmentTag)) {
                    if (getSupportFragmentManager().findFragmentByTag(HomeFragment.TAG) == null) {
                        getSupportFragmentManager().beginTransaction()
                                .hide(getSupportFragmentManager().findFragmentByTag(lastFragmentTag))
                                .add(R.id.fl_container_main, homeFragment, HomeFragment.TAG)
                                .commit();
                    } else {
                        getSupportFragmentManager().beginTransaction()
                                .hide(getSupportFragmentManager().findFragmentByTag(lastFragmentTag))
                                .show(homeFragment)
                                .commit();
                    }
                }
                break;
            case R.id.ll_action_nav_accounts:
                if (!AccountsFragment.TAG.equals(lastFragmentTag)) {
                    if (getSupportFragmentManager().findFragmentByTag(AccountsFragment.TAG) == null) {
                        getSupportFragmentManager().beginTransaction()
                                .hide(getSupportFragmentManager().findFragmentByTag(lastFragmentTag))
                                .add(R.id.fl_container_main, accountsFragment, AccountsFragment.TAG)
                                .commit();
                    } else {
                        getSupportFragmentManager().beginTransaction()
                                .hide(getSupportFragmentManager().findFragmentByTag(lastFragmentTag))
                                .show(accountsFragment)
                                .commit();
                    }
                }
                break;
            case R.id.ll_action_nav_organizations:
                if (!OrganizationsFragment.TAG.equals(lastFragmentTag)) {
                    if (getSupportFragmentManager().findFragmentByTag(OrganizationsFragment.TAG) == null) {
                        getSupportFragmentManager().beginTransaction()
                                .hide(getSupportFragmentManager().findFragmentByTag(lastFragmentTag))
                                .add(R.id.fl_container_main, organizationsFragment, OrganizationsFragment.TAG)
                                .commit();
                    } else {
                        getSupportFragmentManager().beginTransaction()
                                .hide(getSupportFragmentManager().findFragmentByTag(lastFragmentTag))
                                .show(organizationsFragment)
                                .commit();
                    }
                }
                break;
            case R.id.ll_action_nav_profile:
                if (!ProfileFragment.TAG.equals(lastFragmentTag)) {
                    if (getSupportFragmentManager().findFragmentByTag(ProfileFragment.TAG) == null) {
                        getSupportFragmentManager().beginTransaction()
                                .hide(getSupportFragmentManager().findFragmentByTag(lastFragmentTag))
                                .add(R.id.fl_container_main, profileFragment, ProfileFragment.TAG)
                                .commit();
                    } else {
                        getSupportFragmentManager().beginTransaction()
                                .hide(getSupportFragmentManager().findFragmentByTag(lastFragmentTag))
                                .show(profileFragment)
                                .commit();
                    }
                }
                break;
            case R.id.ll_action_nav_settings:
                Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_action_nav_logout:
                Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
    }
}