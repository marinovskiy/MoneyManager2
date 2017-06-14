package alex.moneymanager.presenters;

import alex.moneymanager.models.NewsModel;
import alex.moneymanager.utils.SystemUtils;
import alex.moneymanager.views.NewsDetailsView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NewsDetailsPresenterImpl extends AbstractPresenter<NewsDetailsView>
        implements NewsDetailsPresenter {

    private NewsModel newsModel;
    private SystemUtils systemUtils;

    public NewsDetailsPresenterImpl(NewsModel newsModel, SystemUtils systemUtils) {
        this.newsModel = newsModel;
        this.systemUtils = systemUtils;
    }

    @Override
    public void loadNews(int id) {
        if (isViewAttached()) {
            getView().showProgressDialog();
        }

        if (systemUtils.isConnected()) {
            addSubscription(
                    newsModel.newsDetailsApi(id)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(response -> {
                                if (response.isSuccessful()) {
                                    newsModel.saveNews(response.body().getNews());

                                    if (isViewAttached()) {
                                        getView().dismissProgressDialog();
                                        getView().setNews(response.body().getNews());
                                    }
                                } else {
                                    if (isViewAttached()) {
                                        getView().dismissProgressDialog();
                                        getView().showErrorDialog();
                                    }
                                }
                            }, throwable -> {
                                throwable.printStackTrace();
                                if (isViewAttached()) {
                                    getView().dismissProgressDialog();
                                    getView().showErrorDialog();
                                }
                            })
            );
        } else {
            addSubscription(
                    newsModel.newsDetailsDb(id)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(news -> {
                                if (isViewAttached()) {
                                    getView().dismissProgressDialog();
                                    getView().setNews(news);
                                }
                            }, throwable -> {
                                throwable.printStackTrace();
                                if (isViewAttached()) {
                                    getView().dismissProgressDialog();
                                    getView().showErrorDialog();
                                }
                            })
            );
        }
    }

    @Override
    public void addComment(int id, String text) {
        if (isViewAttached()) {
            getView().showProgressDialog();
        }

        addSubscription(
                newsModel.addCommentApi(id, text)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            if (isViewAttached()) {
                                getView().dismissProgressDialog();
                                if (response.isSuccessful()) {
                                    getView().commentAddedSuccess();
                                } else {
                                    getView().commentFailed();
                                }
                            }
                        }, throwable -> {
                            throwable.printStackTrace();
                            if (isViewAttached()) {
                                getView().dismissProgressDialog();
                                getView().commentFailed();
                            }
                        })
        );
    }
}
