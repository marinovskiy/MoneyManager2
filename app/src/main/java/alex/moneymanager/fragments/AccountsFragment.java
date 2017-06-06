package alex.moneymanager.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import alex.moneymanager.R;
import alex.moneymanager.activities.AuthActivity;
import alex.moneymanager.activities.MainActivity;
import alex.moneymanager.adapters.AccountsAdapter;
import alex.moneymanager.application.MoneyManagerApplication;
import alex.moneymanager.entities.network.Account;
import alex.moneymanager.entities.network.Currency;
import alex.moneymanager.utils.PreferenceUtil;
import alex.moneymanager.utils.SystemUtils;
import butterknife.BindView;

public class AccountsFragment extends BaseFragment {

    public static final String TAG = "AccountsFragment";

    @BindView(R.id.rv_accounts)
    RecyclerView rvAccounts;

    @Inject
    SystemUtils systemUtils;
    @Inject
    PreferenceUtil preferenceUtil;

    private LinearLayoutManager linearLayoutManager;
    private AccountsAdapter rvAccountsAdapter;

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
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        linearLayoutManager = new LinearLayoutManager(getContext());
        rvAccountsAdapter = new AccountsAdapter(generateAccounts());

        rvAccounts.setLayoutManager(linearLayoutManager);
        rvAccounts.setAdapter(rvAccountsAdapter);

        rvAccountsAdapter.setOnItemClickListener(position -> {

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

//    @Override
//    public void showErrorDialog() {
//        String errorMessage;
//        if (systemUtils.isConnected()) {
//            errorMessage = "Error while loading data";
//        } else {
//            errorMessage = "No internet connection";
//        }
//
//        ErrorDialogFragment fragment = ErrorDialogFragment.newInstance(
//                errorMessage,
//                "Ok",
//                "Try again",
//                new ErrorDialogFragment.OnClickListener() {
//                    @Override
//                    public void onPositiveButtonClick() {
//                    }
//
//                    @Override
//                    public void onNegativeButtonClick() {
//
//                    }
//                }
//        );
//
//        fragment.show(getChildFragmentManager(), ErrorDialogFragment.TAG);
//    }

    private List<Account> generateAccounts() {
        List<Account> accounts = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            accounts.add(new Account(++i, "test" + i, "sfsafsaf", new Currency(i, "Dollar USD", "$"), 45.454f, null, null, null));
        }

        return accounts;
    }
}