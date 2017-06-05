package alex.moneymanager.presenters;

import alex.moneymanager.entities.network.User;
import alex.moneymanager.models.AuthModel;
import alex.moneymanager.utils.PreferenceUtil;
import alex.moneymanager.views.LoginView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenterImpl extends AbstractPresenter<LoginView> implements LoginPresenter {

    private AuthModel authModel;
    private PreferenceUtil preferenceUtil;

    public LoginPresenterImpl(AuthModel authModel, PreferenceUtil preferenceUtil) {
        this.authModel = authModel;
        this.preferenceUtil = preferenceUtil;
    }

    @Override
    public void loginUser(User user) {
        if (isViewAttached()) {
            getView().showProgressDialog();
        }

        addSubscription(
                authModel.login(user)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            if (isViewAttached()) {
                                getView().dismissProgressDialog();

                                if (response.isSuccessful()) {
                                    preferenceUtil.setApiKey(response.body().getApiKey());
                                    preferenceUtil.setUser(response.body());

                                    getView().loginSuccess();
                                } else {
                                    getView().loginFailed();
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
    }
}