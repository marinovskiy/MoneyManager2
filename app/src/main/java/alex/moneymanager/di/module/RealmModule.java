package alex.moneymanager.di.module;

import android.content.Context;

import javax.inject.Singleton;

import alex.moneymanager.db.RealmManager;
import dagger.Module;
import dagger.Provides;

@Module
public class RealmModule {

    @Provides
    @Singleton
    RealmManager provideRealmManager(Context context) {
        return new RealmManager(context);
    }
}