package alex.moneymanager.di.module;

import android.content.Context;

import javax.inject.Singleton;

import alex.moneymanager.utils.PreferenceUtil;
import dagger.Module;
import dagger.Provides;

@Module
public class PreferenceModel {

    @Provides
    @Singleton
    PreferenceUtil providePreferenceUtil(Context context) {
        return new PreferenceUtil(context);
    }
}