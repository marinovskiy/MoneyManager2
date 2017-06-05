package alex.moneymanager.presenters;

import alex.moneymanager.entities.network.User;
import alex.moneymanager.views.LoginView;

public interface LoginPresenter extends BasePresenter<LoginView> {

    void loginUser(User user);

}