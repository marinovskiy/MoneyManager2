package alex.moneymanager.presenters;

import alex.moneymanager.entities.db.User;
import alex.moneymanager.views.RegistrationView;

public interface RegistrationPresenter extends BasePresenter<RegistrationView> {

    void registerUser(User user);

}