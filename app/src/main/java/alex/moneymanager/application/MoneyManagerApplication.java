package alex.moneymanager.application;

import android.app.Application;

import javax.inject.Inject;

import alex.moneymanager.di.component.AppComponent;
import alex.moneymanager.di.component.DaggerAppComponent;
import alex.moneymanager.di.module.AppModule;
import alex.moneymanager.utils.PreferenceUtil;
import alex.moneymanager.utils.SystemUtils;

public class MoneyManagerApplication extends Application {

    @Inject
    PreferenceUtil preferenceUtil;
    @Inject
    SystemUtils systemUtils;

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = buildComponent();
        component().inject(this);

        if (preferenceUtil.getUdid() == null) {
            preferenceUtil.setUdid(systemUtils.getUdid());
        }
    }

    public AppComponent component() {
        return appComponent;
    }

    protected AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }
}