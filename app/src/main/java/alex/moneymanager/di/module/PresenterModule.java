package alex.moneymanager.di.module;

import alex.moneymanager.models.AuthModel;
import alex.moneymanager.presenters.LoginPresenter;
import alex.moneymanager.presenters.LoginPresenterImpl;
import alex.moneymanager.utils.PreferenceUtil;
import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @Provides
    LoginPresenter provideLoginPresenter(AuthModel authModel, PreferenceUtil preferenceUtil) {
        return new LoginPresenterImpl(authModel, preferenceUtil);
    }

}