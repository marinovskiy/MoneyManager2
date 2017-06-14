package alex.moneymanager.views;

import alex.moneymanager.entities.db.News;

public interface NewsDetailsView extends BaseView {

    void setNews(News news);

    void commentAddedSuccess();

    void commentFailed();

    void showProgressDialog();

    void dismissProgressDialog();

    void showErrorDialog();

}