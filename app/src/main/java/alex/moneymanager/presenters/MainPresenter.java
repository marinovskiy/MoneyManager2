package alex.moneymanager.presenters;

import alex.moneymanager.views.MainView;

public interface MainPresenter extends BasePresenter<MainView> {

    void loadMainDataApi();

    void loadMainDataDb();

}