package alex.moneymanager.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import alex.moneymanager.R;
import alex.moneymanager.activities.MainActivity;
import alex.moneymanager.activities.NewsDetailsActivity;
import alex.moneymanager.adapters.NewsAdapter;
import alex.moneymanager.application.MoneyManagerApplication;
import alex.moneymanager.dialogs.ErrorDialogFragment;
import alex.moneymanager.entities.db.News;
import alex.moneymanager.presenters.HomePresenter;
import alex.moneymanager.utils.PreferenceUtil;
import alex.moneymanager.utils.SystemUtils;
import alex.moneymanager.views.HomeView;
import butterknife.BindView;

public class HomeFragment extends BaseFragment implements HomeView {

    public static final String TAG = "HomeFragment";

    @BindView(R.id.swipe_to_refresh_news_list)
    SwipeRefreshLayout swipeToRefresh;

    @BindView(R.id.rv_news)
    RecyclerView rvNews;

    @Inject
    SystemUtils systemUtils;
    @Inject
    PreferenceUtil preferenceUtil;
    @Inject
    HomePresenter presenter;

    private LinearLayoutManager linearLayoutManager;
    private NewsAdapter rvNewsAdapter;

    private ProgressDialog progressDialog;

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
        presenter.attachView(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        linearLayoutManager = new LinearLayoutManager(getContext());

        rvNews.setLayoutManager(linearLayoutManager);

        swipeToRefresh.setColorSchemeResources(R.color.colorPositive, R.color.colorNegative);
        swipeToRefresh.setOnRefreshListener(() -> {
//            if (!swipeToRefresh.isRefreshing()) {

                swipeToRefresh.setRefreshing(true);
                presenter.loadNewsList();
//            }
        });

        presenter.loadNewsList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @Override
    public void setNews(List<News> newsList) {
        if (newsList != null) {
            rvNewsAdapter = new NewsAdapter(newsList);
            rvNews.setAdapter(rvNewsAdapter);
            rvNewsAdapter.setOnItemClickListener(position -> {
                Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
                intent.putExtra(NewsDetailsActivity.INTENT_KEY_NEWS_ID, newsList.get(position).getId());
                startActivity(intent);
            });
        }

        if (swipeToRefresh.isRefreshing()) {
            swipeToRefresh.setRefreshing(false);
        }
    }

    @Override
    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Завантаження...");
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
        if (swipeToRefresh.isRefreshing()) {
            swipeToRefresh.setRefreshing(false);
        }

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
                        if (!swipeToRefresh.isRefreshing()) {

                            swipeToRefresh.setRefreshing(true);
                            presenter.loadNewsList();
                        }
                    }
                }
        );

        fragment.show(getChildFragmentManager(), ErrorDialogFragment.TAG);
    }
}