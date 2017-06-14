package alex.moneymanager.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import alex.moneymanager.R;
import alex.moneymanager.adapters.CommentsAdapter;
import alex.moneymanager.application.MoneyManagerApplication;
import alex.moneymanager.dialogs.ErrorDialogFragment;
import alex.moneymanager.entities.db.News;
import alex.moneymanager.presenters.NewsDetailsPresenter;
import alex.moneymanager.utils.SystemUtils;
import alex.moneymanager.views.NewsDetailsView;
import butterknife.BindView;
import butterknife.OnClick;

public class NewsDetailsActivity extends BaseActivity implements NewsDetailsView {

    public static final String INTENT_KEY_NEWS_ID = "intent_key_news_id";

    @BindView(R.id.tv_toolbar_title_news_details)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbar_news_details)
    Toolbar toolbar;
    @BindView(R.id.swipe_to_refresh_news_list)
    SwipeRefreshLayout swipeToRefresh;
    @BindView(R.id.tv_news_text)
    TextView tvNewsText;
    @BindView(R.id.tv_news_date)
    TextView tvNewsDate;
    @BindView(R.id.tv_news_comments_number)
    TextView tvNewsCommentsNumber;
    @BindView(R.id.rv_comments)
    RecyclerView rvComments;
    @BindView(R.id.et_comment)
    EditText etComment;

    @Inject
    NewsDetailsPresenter presenter;
    @Inject
    SystemUtils systemUtils;

    private int newsId;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        ((MoneyManagerApplication) getApplication()).component().inject(this);
        presenter.attachView(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        newsId = getIntent().getIntExtra(INTENT_KEY_NEWS_ID, 0);

        rvComments.setLayoutManager(new LinearLayoutManager(this));

        swipeToRefresh.setColorSchemeResources(R.color.colorPositive, R.color.colorNegative);
        swipeToRefresh.setOnRefreshListener(() -> {
//            if (!swipeToRefresh.isRefreshing()) {

            swipeToRefresh.setRefreshing(true);
            presenter.loadNews(newsId);
//            }
        });

        presenter.loadNews(newsId);
//        News news= generateNews();
//        tvToolbarTitle.setText(news.getTitle());
//        tvNewsText.setText(news.getText());
//        tvNewsDate.setText(news.getUpdatedAt());
//        rvComments.setLayoutManager(new LinearLayoutManager(this));
//        rvComments.setAdapter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.btn_add_comment)
    public void onViewClicked() {
        if (!getComment().isEmpty()) {
            if (systemUtils.isConnected()) {
                presenter.addComment(newsId, getComment());
            } else {
                Toast.makeText(this, R.string.msg_error_no_internet, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Коментар не може бути пустим", Toast.LENGTH_SHORT).show();
        }
    }

    private String getComment() {
        return etComment.getText().toString();
    }

    @Override
    public void setNews(News news) {
        if (news != null) {
            tvToolbarTitle.setText(news.getTitle());
            tvNewsText.setText(news.getText());
            tvNewsDate.setText(news.getUpdatedAt());
//            tvNewsCommentsNumber.setText(news.getComments().size());

            CommentsAdapter commentsAdapter = new CommentsAdapter(news.getComments());
            rvComments.setAdapter(commentsAdapter);
        }

        if (swipeToRefresh.isRefreshing()) {
            swipeToRefresh.setRefreshing(false);
        }
    }

    @Override
    public void commentAddedSuccess() {
        etComment.setText("");
        presenter.loadNews(newsId);
    }

    @Override
    public void commentFailed() {
        Toast.makeText(this, R.string.msg_error_while_making_request, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
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
//                        if (!swipeToRefresh.isRefreshing()) {

                        swipeToRefresh.setRefreshing(true);
                        presenter.loadNews(newsId);
//                        }
                    }
                }
        );

        fragment.show(getSupportFragmentManager(), ErrorDialogFragment.TAG);
    }
}