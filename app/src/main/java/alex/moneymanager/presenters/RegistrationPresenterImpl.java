package alex.moneymanager.presenters;

import alex.moneymanager.entities.db.User;
import alex.moneymanager.models.AuthModel;
import alex.moneymanager.utils.PreferenceUtil;
import alex.moneymanager.views.RegistrationView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RegistrationPresenterImpl extends AbstractPresenter<RegistrationView>
        implements RegistrationPresenter {

    private AuthModel authModel;
    private PreferenceUtil preferenceUtil;

    public RegistrationPresenterImpl(AuthModel authModel, PreferenceUtil preferenceUtil) {
        this.authModel = authModel;
        this.preferenceUtil = preferenceUtil;
    }

    @Override
    public void registerUser(User user) {
        if (isViewAttached()) {
            getView().showProgressDialog();
        }

        addSubscription(
                authModel.registration(user)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            if (isViewAttached()) {
                                getView().dismissProgressDialog();

                                if (response.isSuccessful()) {
                                    preferenceUtil.setApiKey(response.body().getApiKey());
                                    preferenceUtil.setUser(response.body());

                                    getView().registrationSuccess();
                                } else {
                                    getView().registrationFailed();
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