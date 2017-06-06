package alex.moneymanager.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import alex.moneymanager.R;
import alex.moneymanager.activities.MainActivity;
import alex.moneymanager.adapters.OperationsAdapter;
import alex.moneymanager.application.MoneyManagerApplication;
import alex.moneymanager.entities.enums.Type;
import alex.moneymanager.entities.network.Category;
import alex.moneymanager.entities.network.Operation;
import alex.moneymanager.utils.PreferenceUtil;
import alex.moneymanager.utils.SystemUtils;
import butterknife.BindView;

public class HomeFragment extends BaseFragment {

    public static final String TAG = "HomeFragment";

    @BindView(R.id.tv_account_balance)
    TextView tvAccountBalance;
    @BindView(R.id.rv_operations)
    RecyclerView rvOperations;

    @Inject
    SystemUtils systemUtils;
    @Inject
    PreferenceUtil preferenceUtil;

    private LinearLayoutManager linearLayoutManager;
    private OperationsAdapter rvOperationsAdapter;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
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
        rvOperationsAdapter = new OperationsAdapter(generateOperations());

        rvOperations.setLayoutManager(linearLayoutManager);
        rvOperations.setAdapter(rvOperationsAdapter);
    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }

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

    private List<Operation> generateOperations() {
        List<Operation> operations = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            operations.add(new Operation(
                    i + 1,
                    i % 2 == 0 ? Type.INCOME : Type.EXPENSE,
                    "slkhfsyfius",
                    new Random().nextFloat(),
                    new Category(1, "qwert,y", i % 2 == 0 ? Type.INCOME : Type.EXPENSE),
                    0,
                    null,
                    null
            ));
        }

        return operations;
    }
}