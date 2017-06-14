package alex.moneymanager.presenters;

import alex.moneymanager.views.NewsDetailsView;

public interface NewsDetailsPresenter extends BasePresenter<NewsDetailsView> {

    void loadNews(int id);

    void addComment(int id, String text);

}
