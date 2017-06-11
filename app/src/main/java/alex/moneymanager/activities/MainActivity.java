package alex.moneymanager.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import alex.moneymanager.R;
import alex.moneymanager.adapters.AccountsDrawerAdapter;
import alex.moneymanager.adapters.OrganizationsDrawerAdapter;
import alex.moneymanager.adapters.SpinnerAccountsAdapter;
import alex.moneymanager.application.MoneyManagerApplication;
import alex.moneymanager.dialogs.ErrorDialogFragment;
import alex.moneymanager.entities.db.Account;
import alex.moneymanager.entities.db.Organization;
import alex.moneymanager.fragments.AccountsFragment;
import alex.moneymanager.fragments.HomeFragment;
import alex.moneymanager.fragments.OrganizationsFragment;
import alex.moneymanager.fragments.ProfileFragment;
import alex.moneymanager.fragments.ReportsFragment;
import alex.moneymanager.presenters.MainPresenter;
import alex.moneymanager.utils.PreferenceUtil;
import alex.moneymanager.utils.SystemUtils;
import alex.moneymanager.views.MainView;
import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainView {

    public static final String TAG = MainActivity.class.getSimpleName();

    private static final int REQUEST_CODE_NEW_OPERATION = 12345;
    private static final int REQUEST_CODE_NEW_USER_ACCOUNT = 12346;
    private static final int REQUEST_CODE_NEW_ORGANIZATION = 12347;
    private static final int REQUEST_CODE_EDIT_ORGANIZATION = 12348;
    private static final int REQUEST_CODE_NEW_ORGANIZATION_ACCOUNT = 12349;

    @BindView(R.id.drawer_layout_main)
    DrawerLayout drawerLayout;
    @BindView(R.id.toolbar_main)
    Toolbar toolbar;
    //    @BindView(R.id.spinner_accounts)
//    Spinner spinnerPeriod;
    @BindView(R.id.tv_toolbar_title_main)
    TextView tvToolbarTitle;

    @BindView(R.id.tv_header_nav_menu_right)
    TextView tvHeaderNavMenuRight;
    @BindView(R.id.tv_empty_view_nav_menu_right)
    TextView tvEmptyViewNavMenuRight;
    @BindView(R.id.btn_add_nav_menu_right)
    Button btnAddNavMenuRight;
    @BindView(R.id.rv_drawer)
    RecyclerView rvDrawer;
    @BindView(R.id.fab_new_operation)
    FloatingActionButton fabNewOperation;

    @Inject
    SystemUtils systemUtils;
    @Inject
    PreferenceUtil preferenceUtil;
    @Inject
    MainPresenter presenter;

    private MenuItem menuItemEdit;

    private ProgressDialog progressDialog;

    private SpinnerAccountsAdapter<String> spinnerAccountsAdapter;
    private AccountsDrawerAdapter rvAccountsDrawerAdapter;
    private OrganizationsDrawerAdapter rvOrganizationsDrawerAdapter;

    private String lastFragmentTag;
    private HomeFragment homeFragment;
    private AccountsFragment accountsFragment;
    private OrganizationsFragment organizationsFragment;
    private ProfileFragment profileFragment;
    private ReportsFragment reportsFragment;

    private List<String> accountsTest = Arrays.asList("By day", "By month", "By year");

    private List<Account> accounts;
    private List<Organization> organizations;

    private Account selectedAccount;
    private Organization selectedOrganization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((MoneyManagerApplication) getApplication()).component().inject(this);
        presenter.attachView(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_drawer);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

//        drawerLayout.setDrawerLockMode(
//                DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
//                findViewById(R.id.nav_menu_right)
//        );
        fabNewOperation.setVisibility(View.GONE);

        rvDrawer.setLayoutManager(new LinearLayoutManager(this));

        spinnerAccountsAdapter = new SpinnerAccountsAdapter<>(
                this,
                R.layout.spinner_item_account,
                R.id.tv_account_name,
                accountsTest
        );
        spinnerAccountsAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item_account);

//        spinnerPeriod.setAdapter(spinnerAccountsAdapter);
//        spinnerPeriod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(), "selected account = " + accountsTest.get(position), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                Toast.makeText(getApplicationContext(), "nothing selected", Toast.LENGTH_SHORT).show();
//            }
//        });

        homeFragment = HomeFragment.newInstance();
        accountsFragment = AccountsFragment.newInstance();
        accountsFragment = AccountsFragment.newInstance();
        organizationsFragment = OrganizationsFragment.newInstance();
        profileFragment = ProfileFragment.newInstance();
        reportsFragment = ReportsFragment.newInstance();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fl_container_main, homeFragment, HomeFragment.TAG)
                    .commit();

            lastFragmentTag = HomeFragment.TAG;
        }

        loadMainData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_NEW_OPERATION:
                    if (selectedAccount != null) {
                        selectAccount(selectedAccount);
                    }
                    break;
                case REQUEST_CODE_NEW_ORGANIZATION_ACCOUNT:
                case REQUEST_CODE_EDIT_ORGANIZATION:
                    if (selectedOrganization != null) {
                        selectOrganization(selectedOrganization);
                    }
                    break;
                case REQUEST_CODE_NEW_USER_ACCOUNT:
                case REQUEST_CODE_NEW_ORGANIZATION:
                    loadMainData();
                    break;
                case AccountsFragment.REQUEST_CODE_EDIT_OPERATION:
                    if (lastFragmentTag.equals(AccountsFragment.TAG) && accountsFragment != null) {
                        accountsFragment.onActivityResult(requestCode, resultCode, data);
                    }
                    break;
            }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        menuItemEdit = menu.findItem(R.id.action_edit);
        menuItemEdit.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_edit:
                Intent intent = new Intent(this, NewOrganizationActivity.class);
                intent.putExtra(
                        NewOrganizationActivity.INTENT_KEY_ORGANIZATION_ID,
                        selectedOrganization.getId()
                );
                startActivityForResult(intent, REQUEST_CODE_NEW_ORGANIZATION);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick({R.id.ll_action_nav_home, R.id.ll_action_nav_accounts, R.id.ll_action_nav_organizations,
            R.id.ll_action_nav_profile, R.id.ll_action_nav_reports, R.id.ll_action_nav_settings,
            R.id.ll_action_nav_logout, R.id.btn_add_nav_menu_right})
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

//                    drawerLayout.setDrawerLockMode(
//                            DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
//                            findViewById(R.id.nav_menu_right)
//                    );
                    fabNewOperation.setVisibility(View.GONE);
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

                    tvHeaderNavMenuRight.setText("Оберіть аккаунт:");
                    tvEmptyViewNavMenuRight.setText("У Вас ще не має аккаунтів");
                    btnAddNavMenuRight.setText("Додати аккаунт");
                    if (rvAccountsDrawerAdapter != null) {
                        tvEmptyViewNavMenuRight.setVisibility(View.GONE);
                        rvDrawer.setVisibility(View.VISIBLE);

                        if (!accounts.isEmpty()) {
                            selectAccount(accounts.get(0));
                        }

                        rvDrawer.setAdapter(rvAccountsDrawerAdapter);

                        drawerLayout.setDrawerLockMode(
                                DrawerLayout.LOCK_MODE_UNLOCKED,
                                findViewById(R.id.nav_menu_right)
                        );
                    } else {
                        rvDrawer.setVisibility(View.GONE);
                        tvEmptyViewNavMenuRight.setVisibility(View.VISIBLE);
                    }
                    fabNewOperation.setVisibility(View.VISIBLE);
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

                    tvHeaderNavMenuRight.setText("Оберіть групу:");
                    tvEmptyViewNavMenuRight.setText("У Вас ще не має груп");
                    btnAddNavMenuRight.setText("Додати групу");
                    if (rvOrganizationsDrawerAdapter != null) {
                        tvEmptyViewNavMenuRight.setVisibility(View.GONE);
                        rvDrawer.setVisibility(View.VISIBLE);

                        if (!organizations.isEmpty()) {
                            selectOrganization(organizations.get(0));
                        }

                        rvDrawer.setAdapter(rvOrganizationsDrawerAdapter);

                        drawerLayout.setDrawerLockMode(
                                DrawerLayout.LOCK_MODE_UNLOCKED,
                                findViewById(R.id.nav_menu_right)
                        );
                    } else {
                        rvDrawer.setVisibility(View.GONE);
                        tvEmptyViewNavMenuRight.setVisibility(View.VISIBLE);
                    }
                    fabNewOperation.setVisibility(View.VISIBLE);
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

//                    drawerLayout.setDrawerLockMode(
//                            DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
//                            findViewById(R.id.nav_menu_right)
//                    );
                    fabNewOperation.setVisibility(View.GONE);
                }
                break;
            case R.id.ll_action_nav_reports:
                if (!ReportsFragment.TAG.equals(lastFragmentTag)) {
                    if (getSupportFragmentManager().findFragmentByTag(ReportsFragment.TAG) == null) {
                        getSupportFragmentManager().beginTransaction()
                                .hide(getSupportFragmentManager().findFragmentByTag(lastFragmentTag))
                                .add(R.id.fl_container_main, reportsFragment, ReportsFragment.TAG)
                                .commit();
                    } else {
                        getSupportFragmentManager().beginTransaction()
                                .hide(getSupportFragmentManager().findFragmentByTag(lastFragmentTag))
                                .show(reportsFragment)
                                .commit();
                    }

                    lastFragmentTag = ReportsFragment.TAG;

//                    drawerLayout.setDrawerLockMode(
//                            DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
//                            findViewById(R.id.nav_menu_right)
//                    );
                    fabNewOperation.setVisibility(View.GONE);
                }
                break;
            case R.id.ll_action_nav_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.ll_action_nav_logout:
                logout();
                break;
            case R.id.btn_add_nav_menu_right:
                switch (lastFragmentTag) {
                    case AccountsFragment.TAG:
                        startActivityForResult(
                                new Intent(this, NewAccountActivity.class),
                                REQUEST_CODE_NEW_USER_ACCOUNT
                        );
                        break;
                    case OrganizationsFragment.TAG:
                        startActivityForResult(
                                new Intent(this, NewOrganizationActivity.class),
                                REQUEST_CODE_NEW_ORGANIZATION
                        );
                        break;
                }
                break;
        }

        updateMenuByFragment(lastFragmentTag);

        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @OnClick(R.id.fab_new_operation)
    public void onFabClick() {
        if (lastFragmentTag.equals(AccountsFragment.TAG)) {
            Intent intent = new Intent(this, NewOperationActivity.class);
            intent.putExtra(NewOperationActivity.INTENT_KEY_ACCOUNT_ID, selectedAccount.getId());
            startActivityForResult(intent, REQUEST_CODE_NEW_OPERATION);
        } else if (lastFragmentTag.equals(OrganizationsFragment.TAG)) {
            Intent intent = new Intent(this, NewAccountActivity.class);
            intent.putExtra(
                    NewAccountActivity.INTENT_KEY_ORGANIZATION_ID,
                    selectedOrganization.getId()
            );
            startActivityForResult(intent, REQUEST_CODE_NEW_ORGANIZATION_ACCOUNT);
        }
    }

    @Override
    public void setData(List<Account> accounts, List<Organization> organizations) {
        Log.d(TAG, "setData: " + accounts);
        Log.d(TAG, "setData: " + organizations);


        if (accounts != null) {
            this.accounts = accounts;

            if (rvAccountsDrawerAdapter == null) {
                rvAccountsDrawerAdapter = new AccountsDrawerAdapter(accounts);
                rvAccountsDrawerAdapter.setOnItemClickListener(position -> {
                    if (!accounts.isEmpty()) {
                        selectAccount(accounts.get(position));
                    }
                });
            } else {
                rvAccountsDrawerAdapter.updateAccounts(accounts);
            }
        }
        if (organizations != null) {
            this.organizations = organizations;

            if (rvOrganizationsDrawerAdapter == null) {
                rvOrganizationsDrawerAdapter = new OrganizationsDrawerAdapter(organizations);
                rvOrganizationsDrawerAdapter.setOnItemClickListener(position -> {
                    if (!organizations.isEmpty()) {
                        selectOrganization(organizations.get(position));
                    }
                });
            } else {
                rvOrganizationsDrawerAdapter.updateOrganizations(organizations);
            }
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
        ErrorDialogFragment fragment = ErrorDialogFragment.newInstance(
                getString(R.string.msg_error_while_making_request),
                getString(R.string.dialog_btn_ok),
                getString(R.string.dialog_btn_retry),
                new ErrorDialogFragment.OnClickListener() {
                    @Override
                    public void onPositiveButtonClick() {
                    }

                    @Override
                    public void onNegativeButtonClick() {
                        loadMainData();
                    }
                }
        );

        fragment.show(getSupportFragmentManager(), ErrorDialogFragment.TAG);
    }

    private void loadMainData() {
        if (systemUtils.isConnected()) {
            presenter.loadMainDataApi();
        } else {
            presenter.loadMainDataDb();
        }
    }

    private void selectAccount(Account account) {
        if (lastFragmentTag.equals(AccountsFragment.TAG) && accountsFragment != null) {
            selectedAccount = account;

            accountsFragment.switchAccount(account.getId());

            drawerLayout.closeDrawer(GravityCompat.END);
        }
    }

    private void selectOrganization(Organization organization) {
        if (lastFragmentTag.equals(OrganizationsFragment.TAG) && organizationsFragment != null) {
            selectedOrganization = organization;

            organizationsFragment.switchOrganization(organization.getId());

            drawerLayout.closeDrawer(GravityCompat.END);
        }
    }

    private void logout() {
        preferenceUtil.setUser(null);
        preferenceUtil.setApiKey(null);

        Intent intent = new Intent(this, AuthActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void updateMenuByFragment(String fragmentTag) {
        if (menuItemEdit != null) {
            switch (fragmentTag) {
                case HomeFragment.TAG:
                    menuItemEdit.setVisible(false);
                    break;
                case AccountsFragment.TAG:
                    menuItemEdit.setVisible(false);
                    break;
                case OrganizationsFragment.TAG:
                    menuItemEdit.setVisible(true);
                    break;
                case ProfileFragment.TAG:
                    menuItemEdit.setVisible(false);
                    break;
                case ReportsFragment.TAG:
                    menuItemEdit.setVisible(false);
                    break;
            }
        }
    }
}