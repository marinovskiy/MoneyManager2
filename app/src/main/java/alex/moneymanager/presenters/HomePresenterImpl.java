package alex.moneymanager.presenters;

import alex.moneymanager.models.NewsModel;
import alex.moneymanager.utils.SystemUtils;
import alex.moneymanager.views.HomeView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomePresenterImpl extends AbstractPresenter<HomeView> implements HomePresenter {

    private NewsModel newsModel;
    private SystemUtils systemUtils;

    public HomePresenterImpl(NewsModel newsModel, SystemUtils systemUtils) {
        this.newsModel = newsModel;
        this.systemUtils = systemUtils;
    }

    @Override
    public void loadNewsList() {
        if (isViewAttached()) {
            getView().showProgressDialog();
        }

        if (systemUtils.isConnected()) {
            addSubscription(
                    newsModel.newsListApi()
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(newsListResponseResponse -> {
                                if (newsListResponseResponse.isSuccessful()) {
                                    newsModel.saveNewsList(newsListResponseResponse.body().getNewsList());

                                    if (isViewAttached()) {
                                        getView().dismissProgressDialog();
                                        getView().setNews(newsListResponseResponse.body().getNewsList());
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
                    newsModel.newsListDb()
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(newsList -> {
                                if (isViewAttached()) {
                                    getView().dismissProgressDialog();
                                    getView().setNews(newsList);
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
}