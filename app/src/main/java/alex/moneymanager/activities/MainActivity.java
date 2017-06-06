package alex.moneymanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import alex.moneymanager.R;
import alex.moneymanager.adapters.AccountsDrawerAdapter;
import alex.moneymanager.adapters.SpinnerAccountsAdapter;
import alex.moneymanager.entities.network.Account;
import alex.moneymanager.entities.network.Currency;
import alex.moneymanager.fragments.AccountsFragment;
import alex.moneymanager.fragments.HomeFragment;
import alex.moneymanager.fragments.OrganizationsFragment;
import alex.moneymanager.fragments.ProfileFragment;
import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    private static final int REQUEST_CODE_NEW_OPERATION = 12345;

    @BindView(R.id.drawer_layout_main)
    DrawerLayout drawerLayout;
    @BindView(R.id.toolbar_main)
    Toolbar toolbar;
    @BindView(R.id.spinner_accounts)
    Spinner spinnerPeriod;
    @BindView(R.id.tv_toolbar_title_main)
    TextView tvToolbarTitle;
    @BindView(R.id.rv_accounts)
    RecyclerView rvAccountsDrawer;

    private SpinnerAccountsAdapter<String> spinnerAccountsAdapter;
    private AccountsDrawerAdapter rvAccountsDrawerAdapter;

    private String lastFragmentTag;
    private HomeFragment homeFragment;
    private AccountsFragment accountsFragment;
    private OrganizationsFragment organizationsFragment;
    private ProfileFragment profileFragment;

    private List<String> accountsTest = Arrays.asList("By day", "By month", "By year");
    private List<Account> accounts;

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

        spinnerAccountsAdapter = new SpinnerAccountsAdapter<>(
                this,
                R.layout.spinner_item_account,
                R.id.tv_account_name,
                accountsTest
        );
        spinnerAccountsAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item_account);

        spinnerPeriod.setAdapter(spinnerAccountsAdapter);
        spinnerPeriod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "selected account = " + accountsTest.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "nothing selected", Toast.LENGTH_SHORT).show();
            }
        });

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

        accounts = generateAccounts();
        rvAccountsDrawerAdapter = new AccountsDrawerAdapter(accounts);
        rvAccountsDrawer.setLayoutManager(new LinearLayoutManager(this));
        rvAccountsDrawer.setAdapter(rvAccountsDrawerAdapter);
        rvAccountsDrawerAdapter.setOnItemClickListener(position -> {
            Toast.makeText(this, "selected acc = " + accounts.get(position), Toast.LENGTH_SHORT).show();
        });
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

                    lastFragmentTag = HomeFragment.TAG;
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

                    lastFragmentTag = AccountsFragment.TAG;
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

                    lastFragmentTag = OrganizationsFragment.TAG;
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

                    lastFragmentTag = ProfileFragment.TAG;
                }
                break;
            case R.id.ll_action_nav_settings:
                Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_action_nav_logout:
                Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
                break;
        }

        updateMenuByFragment(lastFragmentTag);

        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @OnClick(R.id.fab_new_operation)
    public void onFabClick() {
        startActivityForResult(
                new Intent(this, NewOperationActivity.class),
                REQUEST_CODE_NEW_OPERATION
        );
    }

    public void updateMenuByFragment(String fragmentTag) {
        switch (fragmentTag) {
            case HomeFragment.TAG:
                tvToolbarTitle.setVisibility(View.GONE);
                spinnerPeriod.setVisibility(View.VISIBLE);
                break;
            case AccountsFragment.TAG:
                tvToolbarTitle.setText("Your accounts");

                spinnerPeriod.setVisibility(View.GONE);
                tvToolbarTitle.setVisibility(View.VISIBLE);
                break;
            case OrganizationsFragment.TAG:
                tvToolbarTitle.setText("Your organizations");

                spinnerPeriod.setVisibility(View.GONE);
                tvToolbarTitle.setVisibility(View.VISIBLE);
                break;
            case ProfileFragment.TAG:
                tvToolbarTitle.setText("Your profile");

                spinnerPeriod.setVisibility(View.GONE);
                tvToolbarTitle.setVisibility(View.VISIBLE);
                break;
        }
    }

    private List<Account> generateAccounts() {
        List<Account> accounts = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            accounts.add(new Account(++i, "test" + i, "sfsafsaf", new Currency(i, "Dollar USD", "$"), 45.454f, null, null, null));
        }

        return accounts;
    }
}