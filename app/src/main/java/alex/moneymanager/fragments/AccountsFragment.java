package alex.moneymanager.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import alex.moneymanager.R;
import alex.moneymanager.activities.MainActivity;
import alex.moneymanager.activities.NewOperationActivity;
import alex.moneymanager.adapters.OperationsAdapter;
import alex.moneymanager.application.MoneyManagerApplication;
import alex.moneymanager.dialogs.ErrorDialogFragment;
import alex.moneymanager.dialogs.MyDialogFragment;
import alex.moneymanager.entities.db.Account;
import alex.moneymanager.presenters.AccountsPresenter;
import alex.moneymanager.utils.PreferenceUtil;
import alex.moneymanager.utils.SystemUtils;
import alex.moneymanager.views.AccountsView;
import butterknife.BindView;

public class AccountsFragment extends BaseFragment implements AccountsView {

    public static final int ERROR_CASE_ACCOUNT = 0;
    public static final int ERROR_CASE_EDIT_OPERATION = 1;
    public static final int ERROR_CASE_DELETE_OPERATION = 2;

    public static final String TAG = "AccountsFragment";

    public static final int REQUEST_CODE_EDIT_OPERATION = 1122;

    @BindView(R.id.tv_account_description)
    TextView tvAccountDescription;
    @BindView(R.id.tv_account_balance)
    TextView tvAccountBalance;
    @BindView(R.id.rv_operations)
    RecyclerView rvOperations;

    @Inject
    SystemUtils systemUtils;
    @Inject
    PreferenceUtil preferenceUtil;
    @Inject
    AccountsPresenter presenter;

    private ProgressDialog progressDialog;

    private LinearLayoutManager linearLayoutManager;
    private OperationsAdapter rvOperationsAdapter;

    private int lastAccountId;
    private Account account;
    private Integer lastOperationId;

    public AccountsFragment() {
    }

    public static AccountsFragment newInstance() {
        return new AccountsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_accounts, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity) getActivity()).updateMenuByFragment(TAG);

        ((MoneyManagerApplication) getActivity().getApplication()).component().inject(this);
        presenter.attachView(this);

        linearLayoutManager = new LinearLayoutManager(getContext());
        rvOperations.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        switchAccount(lastAccountId);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_EDIT_OPERATION:
                    if (account != null) {
                        presenter.loadAccount(account.getId());
                    }
                    break;
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
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
                                presenter.loadAccount(lastAccountId);
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

        fragment.show(getChildFragmentManager(), ErrorDialogFragment.TAG);
    }

    private void showOperationPopupMenu(View view, int operationId) {
        lastOperationId = operationId;

        PopupMenu popupMenu = new PopupMenu(getContext(), view);
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
        Intent intent = new Intent(getActivity(), NewOperationActivity.class);
        intent.putExtra(NewOperationActivity.INTENT_KEY_ACCOUNT_ID, lastAccountId);
        intent.putExtra(NewOperationActivity.INTENT_KEY_OPERATION_ID, lastOperationId);
        startActivityForResult(intent, REQUEST_CODE_EDIT_OPERATION);
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
                                presenter.deleteOperation(lastAccountId, lastOperationId);
                            }
                        } else {
                            Toast.makeText(getContext(), R.string.msg_error_no_internet, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onNegativeButtonClick() {
                    }
                });
        fragment.show(getChildFragmentManager(), MyDialogFragment.TAG);
    }

    public void switchAccount(int accountId) {
        lastAccountId = accountId;

        if (presenter != null) {
            presenter.loadAccount(accountId);
        }
    }
}