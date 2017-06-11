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
import alex.moneymanager.adapters.NewsAdapter;
import alex.moneymanager.adapters.OperationsAdapter;
import alex.moneymanager.application.MoneyManagerApplication;
import alex.moneymanager.entities.db.Category;
import alex.moneymanager.entities.db.News;
import alex.moneymanager.entities.db.Operation;
import alex.moneymanager.entities.enums.Type;
import alex.moneymanager.utils.PreferenceUtil;
import alex.moneymanager.utils.SystemUtils;
import butterknife.BindView;

public class HomeFragment extends BaseFragment {

    public static final String TAG = "HomeFragment";

    @BindView(R.id.rv_news)
    RecyclerView rvNews;

    @Inject
    SystemUtils systemUtils;
    @Inject
    PreferenceUtil preferenceUtil;

    private LinearLayoutManager linearLayoutManager;
    private NewsAdapter rvNewsAdapter;

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
        rvNewsAdapter = new NewsAdapter(generateNews());

        rvNews.setLayoutManager(linearLayoutManager);
        rvNews.setAdapter(rvNewsAdapter);
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

    private List<News> generateNews() {
        List<News> news = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            news.add(new News(
                    i+1,
                    "hello",
                    "dksfkdfl kslfkdfl f;ll s;lfs ;fkl;ds",
                    null,
                    null,
                    null,
                    null
            ));
        }

        return news;
    }
}