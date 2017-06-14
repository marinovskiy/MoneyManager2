package alex.moneymanager.di.module;

import alex.moneymanager.models.AccountModel;
import alex.moneymanager.models.AuthModel;
import alex.moneymanager.models.CategoryModel;
import alex.moneymanager.models.CurrencyModel;
import alex.moneymanager.models.InitialDataModel;
import alex.moneymanager.models.NewsModel;
import alex.moneymanager.models.OperationModel;
import alex.moneymanager.models.OrganizationModel;
import alex.moneymanager.models.UserModel;
import alex.moneymanager.presenters.AccountDetailsPresenter;
import alex.moneymanager.presenters.AccountDetailsPresenterImpl;
import alex.moneymanager.presenters.AccountsPresenter;
import alex.moneymanager.presenters.AccountsPresenterImpl;
import alex.moneymanager.presenters.HomePresenter;
import alex.moneymanager.presenters.HomePresenterImpl;
import alex.moneymanager.presenters.LoginPresenter;
import alex.moneymanager.presenters.LoginPresenterImpl;
import alex.moneymanager.presenters.MainPresenter;
import alex.moneymanager.presenters.MainPresenterImpl;
import alex.moneymanager.presenters.NewAccountPresenter;
import alex.moneymanager.presenters.NewAccountPresenterImpl;
import alex.moneymanager.presenters.NewOperationPresenter;
import alex.moneymanager.presenters.NewOperationPresenterImpl;
import alex.moneymanager.presenters.NewOrganizationPresenter;
import alex.moneymanager.presenters.NewOrganizationPresenterImpl;
import alex.moneymanager.presenters.NewsDetailsPresenter;
import alex.moneymanager.presenters.NewsDetailsPresenterImpl;
import alex.moneymanager.presenters.OrganizationAddMemberPresenter;
import alex.moneymanager.presenters.OrganizationAddMemberPresenterImpl;
import alex.moneymanager.presenters.OrganizationMembersPresenter;
import alex.moneymanager.presenters.OrganizationMembersPresenterImpl;
import alex.moneymanager.presenters.OrganizationsPresenter;
import alex.moneymanager.presenters.OrganizationsPresenterImpl;
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

    @Provides
    NewOperationPresenter provideNewOperationPresenter(SystemUtils systemUtils,
                                                       CategoryModel categoryModel,
                                                       OperationModel operationModel) {
        return new NewOperationPresenterImpl(systemUtils, categoryModel, operationModel);
    }

    @Provides
    AccountsPresenter provideAccountsPresenter(SystemUtils systemUtils, AccountModel accountModel,
                                               OperationModel operationModel) {
        return new AccountsPresenterImpl(systemUtils, accountModel, operationModel);
    }

    @Provides
    OrganizationsPresenter provideOrganizationsPresenter(SystemUtils systemUtils,
                                                         OrganizationModel organizationModel,
                                                         AccountModel accountModel) {
        return new OrganizationsPresenterImpl(systemUtils, organizationModel, accountModel);
    }

    @Provides
    NewOrganizationPresenter provideNewOrganizationPresenter(OrganizationModel organizationModel) {
        return new NewOrganizationPresenterImpl(organizationModel);
    }

    @Provides
    OrganizationMembersPresenter provideOrganizationMembersPresenter(OrganizationModel organizationModel) {
        return new OrganizationMembersPresenterImpl(organizationModel);
    }

    @Provides
    OrganizationAddMemberPresenter provideOrganizationAddMemberPresenter(UserModel userModel,
                                                                         OrganizationModel organizationModel) {
        return new OrganizationAddMemberPresenterImpl(userModel, organizationModel);
    }

    @Provides
    AccountDetailsPresenter provideAccountDetailsPresenter(SystemUtils systemUtils,
                                                           AccountModel accountModel,
                                                           OperationModel operationModel) {
        return new AccountDetailsPresenterImpl(systemUtils, accountModel, operationModel);
    }

    @Provides
    HomePresenter provideHomePresenter(NewsModel newsModel, SystemUtils systemUtils) {
        return new HomePresenterImpl(newsModel, systemUtils);
    }

    @Provides
    NewsDetailsPresenter provideNewsDetailsPresenter(NewsModel newsModel, SystemUtils systemUtils) {
        return new NewsDetailsPresenterImpl(newsModel, systemUtils);
    }
}