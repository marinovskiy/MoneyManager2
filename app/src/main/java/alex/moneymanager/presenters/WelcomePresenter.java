package alex.moneymanager.presenters;

import alex.moneymanager.views.WelcomeView;

public interface WelcomePresenter extends BasePresenter<WelcomeView> {

    void loadInitialData();

}