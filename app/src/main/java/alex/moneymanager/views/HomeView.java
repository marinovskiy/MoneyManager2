package alex.moneymanager.views;

import java.util.List;

import alex.moneymanager.entities.db.News;

public interface HomeView extends BaseView {

    void setNews(List<News> newsList);

    void showProgressDialog();

    void dismissProgressDialog();

    void showErrorDialog();

}