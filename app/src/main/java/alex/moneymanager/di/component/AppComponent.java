package alex.moneymanager.di.component;

import javax.inject.Singleton;

import alex.moneymanager.activities.ForgotPasswordActivity;
import alex.moneymanager.activities.LoginActivity;
import alex.moneymanager.activities.MainActivity;
import alex.moneymanager.activities.RegistrationActivity;
import alex.moneymanager.activities.SplashActivity;
import alex.moneymanager.activities.WelcomeActivity;
import alex.moneymanager.application.MoneyManagerApplication;
import alex.moneymanager.di.module.AppModule;
import alex.moneymanager.di.module.ModelModule;
import alex.moneymanager.di.module.NetModule;
import alex.moneymanager.di.module.PreferenceModel;
import alex.moneymanager.di.module.PresenterModule;
import alex.moneymanager.di.module.SystemUtilsModule;
import dagger.Component;

@Component(modules = {
        AppModule.class,
        ModelModule.class,
        NetModule.class,
        PreferenceModel.class,
        PresenterModule.class,
        SystemUtilsModule.class,
})
@Singleton
public interface AppComponent {

    // application
    void inject(MoneyManagerApplication application);

    // activities
    void inject(SplashActivity activity);

    void inject(WelcomeActivity activity);

    void inject(LoginActivity activity);

    void inject(ForgotPasswordActivity activity);

    void inject(RegistrationActivity activity);

    void inject(MainActivity activity);

    // fragments

}