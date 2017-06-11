package alex.moneymanager.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import alex.moneymanager.R;
import alex.moneymanager.activities.MainActivity;
import alex.moneymanager.adapters.AccountsAdapter;
import alex.moneymanager.application.MoneyManagerApplication;
import alex.moneymanager.utils.PreferenceUtil;
import alex.moneymanager.utils.SystemUtils;

public class ReportsFragment extends BaseFragment {

    public static final String TAG = "ReportsFragment";

//    @BindView(R.id.rv_accounts)
//    RecyclerView rvAccounts;
//
//    @Inject
//    SystemUtils systemUtils;
//    @Inject
//    PreferenceUtil preferenceUtil;
//
//    private LinearLayoutManager linearLayoutManager;
//    private AccountsAdapter rvAccountsAdapter;

    public ReportsFragment() {
    }

    public static ReportsFragment newInstance() {
        return new ReportsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reports, container, false);
    }

//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        ((MainActivity) getActivity()).updateMenuByFragment(TAG);
//
//        ((MoneyManagerApplication) getActivity().getApplication()).component().inject(this);
//    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);

//        linearLayoutManager = new LinearLayoutManager(getContext());
//        rvAccountsAdapter = new AccountsAdapter(generateAccounts());
//
//        rvAccounts.setLayoutManager(linearLayoutManager);
//        rvAccounts.setAdapter(rvAccountsAdapter);
//
//        rvAccountsAdapter.setOnItemClickListener(position -> {
//
//        });
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//    }
}