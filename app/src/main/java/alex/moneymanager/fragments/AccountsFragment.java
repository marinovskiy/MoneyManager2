package alex.moneymanager.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import alex.moneymanager.R;
import alex.moneymanager.activities.MainActivity;
import alex.moneymanager.adapters.OperationsAdapter;
import alex.moneymanager.application.MoneyManagerApplication;
import alex.moneymanager.dialogs.ErrorDialogFragment;
import alex.moneymanager.entities.db.Account;
import alex.moneymanager.presenters.AccountsPresenter;
import alex.moneymanager.utils.PreferenceUtil;
import alex.moneymanager.utils.SystemUtils;
import alex.moneymanager.views.AccountsView;
import butterknife.BindView;

public class AccountsFragment extends BaseFragment implements AccountsView {

    public static final String TAG = "AccountsFragment";

    public static final int REQUEST_CODE_NEW_OPERATION = 1122;

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
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_NEW_OPERATION:
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

            if (account.getBalance() < 0) {
                tvAccountBalance.setBackgroundResource(R.drawable.tv_balance_bg_negative);
            } else {
                tvAccountBalance.setBackgroundResource(R.drawable.tv_balance_bg_positive);
            }
            tvAccountBalance.setText(
                    String.format(
                            "Поточний баланс: %s %s",
                            account.getBalance(),
                            account.getCurrency().getSymbol()
                    )
            );

            if (account.getOperations() != null) {

                if (rvOperationsAdapter == null) {
                    rvOperationsAdapter = new OperationsAdapter(account.getOperations());
                    rvOperationsAdapter.setOnItemClickListener(position -> {
                        Toast.makeText(getContext(), "TODO popup for edit delete operation", Toast.LENGTH_SHORT).show();
                    });

                    rvOperations.setAdapter(rvOperationsAdapter);
                } else {
                    rvOperationsAdapter.updateOperations(account.getOperations());
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
                        presenter.loadAccount(lastAccountId);
                    }
                }
        );

        fragment.show(getChildFragmentManager(), ErrorDialogFragment.TAG);
    }

    public void switchAccount(int accountId) {
        lastAccountId = accountId;

        presenter.loadAccount(accountId);
    }
}