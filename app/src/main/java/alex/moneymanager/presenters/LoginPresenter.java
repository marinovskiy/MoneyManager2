package alex.moneymanager.presenters;

import alex.moneymanager.entities.db.User;
import alex.moneymanager.views.LoginView;

public interface LoginPresenter extends BasePresenter<LoginView> {

    void loginUser(User user);

}