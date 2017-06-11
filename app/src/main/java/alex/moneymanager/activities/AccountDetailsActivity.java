package alex.moneymanager.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import alex.moneymanager.R;
import alex.moneymanager.adapters.OperationsAdapter;
import alex.moneymanager.application.MoneyManagerApplication;
import alex.moneymanager.dialogs.ErrorDialogFragment;
import alex.moneymanager.dialogs.MyDialogFragment;
import alex.moneymanager.entities.db.Account;
import alex.moneymanager.presenters.AccountDetailsPresenter;
import alex.moneymanager.utils.SystemUtils;
import alex.moneymanager.views.AccountDetailsView;
import butterknife.BindView;
import butterknife.OnClick;

public class AccountDetailsActivity extends BaseActivity implements AccountDetailsView {

    public static final String INTENT_KEY_ORGANIZATION_ID = "intent_key_organization_id";
    public static final String INTENT_KEY_ACCOUNT_ID = "intent_key_account_id";

    public static final int REQUEST_CODE_NEW_OPERATION = 4535;
    public static final int REQUEST_CODE_EDIT_OPERATION = 4536;

    public static final int ERROR_CASE_ACCOUNT = 0;
    public static final int ERROR_CASE_NEW_OPERATION = 1;
    public static final int ERROR_CASE_EDIT_OPERATION = 2;
    public static final int ERROR_CASE_DELETE_OPERATION = 3;

    @BindView(R.id.toolbar_account_details)
    Toolbar toolbar;
    @BindView(R.id.tv_account_description)
    TextView tvAccountDescription;
    @BindView(R.id.tv_account_balance)
    TextView tvAccountBalance;
    @BindView(R.id.rv_operations)
    RecyclerView rvOperations;

    @Inject
    SystemUtils systemUtils;
    @Inject
    AccountDetailsPresenter presenter;

    private ProgressDialog progressDialog;
    private OperationsAdapter rvOperationsAdapter;

    private int organizationId;
    private int accountId;
    private Account account;
    private Integer lastOperationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);

        ((MoneyManagerApplication) getApplication()).component().inject(this);
        presenter.attachView(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
//            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        organizationId = getIntent().getIntExtra(INTENT_KEY_ORGANIZATION_ID, 0);
        accountId = getIntent().getIntExtra(INTENT_KEY_ACCOUNT_ID, 0);
        if (organizationId != 0 && accountId != 0) {
            presenter.loadAccount(organizationId, accountId);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void onBackPressed() {
        finish();
//        super.onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_NEW_OPERATION:
                case REQUEST_CODE_EDIT_OPERATION:
                    if (account != null) {
                        setResult(RESULT_OK);

                        presenter.loadAccount(organizationId, accountId);
                    }
                    break;
            }
        }
    }

    @OnClick(R.id.fab_new_operation)
    public void onViewClicked() {
        Intent intent = new Intent(this, NewOperationActivity.class);
        intent.putExtra(NewOperationActivity.INTENT_KEY_ORGANIZATION_ID, organizationId);
        intent.putExtra(NewOperationActivity.INTENT_KEY_ACCOUNT_ID, accountId);
        startActivityForResult(intent, REQUEST_CODE_NEW_OPERATION);
    }

    @Override
    public void setAccount(Account account) {
        if (account != null) {
            this.account = account;

            if (this.account.getDescription() != null) {
                tvAccountDescription.setVisibility(View.VISIBLE);
                tvAccountDescription.setText(account.getDescription());

            } else {
                tvAccountDescription.setVisibility(View.GONE);
            }

            if (account.getBalance() < 0) {
                tvAccountBalance.setBackgroundResource(R.drawable.tv_balance_bg_negative);
            } else {
                tvAccountBalance.setBackgroundResource(R.drawable.tv_balance_bg_positive);
            }
            tvAccountBalance.setText(
                    String.format(
                            "Поточний баланс: %s %s",
                            this.account.getBalance(),
                            this.account.getCurrency().getSymbol()
                    )
            );

            if (this.account.getOperations() != null) {

                if (rvOperationsAdapter == null) {
                    rvOperationsAdapter = new OperationsAdapter(this.account.getOperations());
                    rvOperationsAdapter.setOnItemClickListener((view, position) -> {
                        if (this.account.getOperations() != null) {
                            showOperationPopupMenu(
                                    view,
                                    this.account.getOperations().get(position).getId()
                            );
                        }
                    });

                    rvOperations.setLayoutManager(new LinearLayoutManager(this));
                    rvOperations.setAdapter(rvOperationsAdapter);
                } else {
                    rvOperationsAdapter.updateOperations(this.account.getOperations());
                }

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
    public void showErrorDialog(int errorCase) {
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
                        switch (errorCase) {
                            case ERROR_CASE_ACCOUNT:
                                presenter.loadAccount(organizationId, accountId);
                                break;
                            case ERROR_CASE_NEW_OPERATION:

                                break;
                            case ERROR_CASE_EDIT_OPERATION:
                                editOperation();
                                break;
                            case ERROR_CASE_DELETE_OPERATION:
                                deleteOperation();
                                break;
                        }
                    }
                }
        );

        fragment.show(getSupportFragmentManager(), ErrorDialogFragment.TAG);
    }

    private void showOperationPopupMenu(View view, int operationId) {
        lastOperationId = operationId;

        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.popup_menu_edit_delete);

        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_edit:
                    editOperation();
                    return true;
                case R.id.action_delete:
                    deleteOperation();
                    return true;
                default:
                    return false;
            }
        });

        popupMenu.show();
    }

    private void editOperation() {
        Intent intent = new Intent(this, NewOperationActivity.class);
        intent.putExtra(NewOperationActivity.INTENT_KEY_ORGANIZATION_ID, organizationId);
        intent.putExtra(NewOperationActivity.INTENT_KEY_ACCOUNT_ID, accountId);
        intent.putExtra(NewOperationActivity.INTENT_KEY_OPERATION_ID, lastOperationId);
        startActivityForResult(intent, REQUEST_CODE_NEW_OPERATION);
    }

    private void deleteOperation() {
        MyDialogFragment fragment = MyDialogFragment.newInstance(
                "Ви впевнені, що хочете видалити дану операцію?",
                "Так",
                "Ні",
                new MyDialogFragment.OnClickListener() {
                    @Override
                    public void onPositiveButtonClick() {
                        if (systemUtils.isConnected()) {
                            if (lastOperationId != null) {
                                presenter.deleteOperation(organizationId, accountId, lastOperationId);
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.msg_error_no_internet, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onNegativeButtonClick() {
                    }
                });
        fragment.show(getSupportFragmentManager(), MyDialogFragment.TAG);
    }
}