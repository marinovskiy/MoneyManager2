package alex.moneymanager.di.module;

import alex.moneymanager.models.AccountModel;
import alex.moneymanager.models.AuthModel;
import alex.moneymanager.models.CategoryModel;
import alex.moneymanager.models.CurrencyModel;
import alex.moneymanager.models.InitialDataModel;
import alex.moneymanager.models.OrganizationModel;
import alex.moneymanager.presenters.LoginPresenter;
import alex.moneymanager.presenters.LoginPresenterImpl;
import alex.moneymanager.presenters.MainPresenter;
import alex.moneymanager.presenters.MainPresenterImpl;
import alex.moneymanager.presenters.NewAccountPresenter;
import alex.moneymanager.presenters.NewAccountPresenterImpl;
import alex.moneymanager.presenters.RegistrationPresenter;
import alex.moneymanager.presenters.RegistrationPresenterImpl;
import alex.moneymanager.presenters.WelcomePresenter;
import alex.moneymanager.presenters.WelcomePresenterImpl;
import alex.moneymanager.utils.PreferenceUtil;
import alex.moneymanager.utils.SystemUtils;
import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @Provides
    WelcomePresenter provideWelcomePresenter(InitialDataModel initialDataModel,
                                             CategoryModel categoryModel,
                                             CurrencyModel currencyModel,
                                             PreferenceUtil preferenceUtil) {
        return new WelcomePresenterImpl(
                initialDataModel,
                categoryModel,
                currencyModel,
                preferenceUtil
        );
    }

    @Provides
    LoginPresenter provideLoginPresenter(AuthModel authModel, PreferenceUtil preferenceUtil) {
        return new LoginPresenterImpl(authModel, preferenceUtil);
    }

    @Provides
    RegistrationPresenter provideRegistrationPresenter(AuthModel authModel, PreferenceUtil preferenceUtil) {
        return new RegistrationPresenterImpl(authModel, preferenceUtil);
    }

    @Provides
    MainPresenter provideMainPresenter(AccountModel accountModel,
                                       OrganizationModel organizationModel,
                                       PreferenceUtil preferenceUtil) {
        return new MainPresenterImpl(accountModel, organizationModel, preferenceUtil);
    }

    @Provides
    NewAccountPresenter provideNewAccountPresenter(SystemUtils systemUtils,
                                                   CurrencyModel currencyModel,
                                                   AccountModel accountModel) {
        return new NewAccountPresenterImpl(systemUtils, currencyModel, accountModel);
    }
}