package alex.moneymanager.di.component;

import javax.inject.Singleton;

import alex.moneymanager.activities.AccountDetailsActivity;
import alex.moneymanager.activities.AuthActivity;
import alex.moneymanager.activities.MainActivity;
import alex.moneymanager.activities.NewAccountActivity;
import alex.moneymanager.activities.NewOperationActivity;
import alex.moneymanager.activities.NewOrganizationActivity;
import alex.moneymanager.activities.SplashActivity;
import alex.moneymanager.application.MoneyManagerApplication;
import alex.moneymanager.di.module.AppModule;
import alex.moneymanager.di.module.ModelModule;
import alex.moneymanager.di.module.NetModule;
import alex.moneymanager.di.module.PreferenceModel;
import alex.moneymanager.di.module.PresenterModule;
import alex.moneymanager.di.module.RealmModule;
import alex.moneymanager.di.module.SystemUtilsModule;
import alex.moneymanager.fragments.AccountsFragment;
import alex.moneymanager.fragments.ForgotPasswordFragment;
import alex.moneymanager.fragments.HomeFragment;
import alex.moneymanager.fragments.LoginFragment;
import alex.moneymanager.fragments.OrganizationsFragment;
import alex.moneymanager.fragments.ProfileFragment;
import alex.moneymanager.fragments.RegistrationFragment;
import alex.moneymanager.fragments.ReportsFragment;
import alex.moneymanager.fragments.WelcomeFragment;
import dagger.Component;

@Component(modules = {
        AppModule.class,
        ModelModule.class,
        NetModule.class,
        PreferenceModel.class,
        PresenterModule.class,
        RealmModule.class,
        SystemUtilsModule.class,
})
@Singleton
public interface AppComponent {

    // application
    void inject(MoneyManagerApplication application);

    // activities
    void inject(SplashActivity activity);

    void inject(AuthActivity activity);

    void inject(MainActivity activity);

    void inject(NewOperationActivity activity);

    void inject(NewAccountActivity activity);

    void inject(NewOrganizationActivity activity);

    void inject(AccountDetailsActivity activity);

    // fragments
    void inject(WelcomeFragment fragment);

    void inject(LoginFragment fragment);

    void inject(ForgotPasswordFragment fragment);

    void inject(RegistrationFragment fragment);

    void inject(HomeFragment fragment);

    void inject(AccountsFragment fragment);

    void inject(OrganizationsFragment fragment);

    void inject(ReportsFragment fragment);

    void inject(ProfileFragment fragment);
}