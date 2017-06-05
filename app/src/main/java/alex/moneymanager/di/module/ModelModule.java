package alex.moneymanager.di.module;

import alex.moneymanager.api.ApiClient;
import alex.moneymanager.models.AuthModel;
import alex.moneymanager.models.AuthModelImpl;
import alex.moneymanager.models.CategoryModel;
import alex.moneymanager.models.CategoryModelImpl;
import alex.moneymanager.models.CurrencyModel;
import alex.moneymanager.models.CurrencyModelImpl;
import alex.moneymanager.utils.PreferenceUtil;
import dagger.Module;
import dagger.Provides;

@Module
public class ModelModule {

    @Provides
    AuthModel provideAuthModel(ApiClient apiClient, PreferenceUtil preferenceUtil) {
        return new AuthModelImpl(apiClient, preferenceUtil);
    }

    @Provides
    CategoryModel provideCategoryModel(ApiClient apiClient) {
        return new CategoryModelImpl(apiClient);
    }

    @Provides
    CurrencyModel provideCurrencyModel(ApiClient apiClient) {
        return new CurrencyModelImpl(apiClient);
    }
}