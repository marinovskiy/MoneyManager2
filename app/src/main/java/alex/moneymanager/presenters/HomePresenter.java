package alex.moneymanager.presenters;

import alex.moneymanager.views.HomeView;

public interface HomePresenter extends BasePresenter<HomeView> {

    void loadNewsList();

}
