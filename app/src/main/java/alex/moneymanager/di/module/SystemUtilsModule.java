package alex.moneymanager.di.module;

import android.content.Context;

import javax.inject.Singleton;

import alex.moneymanager.utils.SystemUtils;
import dagger.Module;
import dagger.Provides;

@Module
public class SystemUtilsModule {

    @Provides
    @Singleton
    SystemUtils provideSystemUtils(Context context) {
        return new SystemUtils(context);
    }
}