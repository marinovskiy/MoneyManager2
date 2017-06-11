package alex.moneymanager.di.module;

import alex.moneymanager.api.ApiClient;
import alex.moneymanager.db.RealmManager;
import alex.moneymanager.models.AccountModel;
import alex.moneymanager.models.AccountModelImpl;
import alex.moneymanager.models.AuthModel;
import alex.moneymanager.models.AuthModelImpl;
import alex.moneymanager.models.CategoryModel;
import alex.moneymanager.models.CategoryModelImpl;
import alex.moneymanager.models.CurrencyModel;
import alex.moneymanager.models.CurrencyModelImpl;
import alex.moneymanager.models.InitialDataModel;
import alex.moneymanager.models.InitialDataModelImpl;
import alex.moneymanager.models.OrganizationModel;
import alex.moneymanager.models.OrganizationModelImpl;
import alex.moneymanager.utils.PreferenceUtil;
import dagger.Module;
import dagger.Provides;

@Module
public class ModelModule {

    @Provides
    InitialDataModel provideInitialDataModel(ApiClient apiClient) {
        return new InitialDataModelImpl(apiClient);
    }

    @Provides
    AuthModel provideAuthModel(ApiClient apiClient, PreferenceUtil preferenceUtil) {
        return new AuthModelImpl(apiClient, preferenceUtil);
    }

    @Provides
    CategoryModel provideCategoryModel(ApiClient apiClient, RealmManager realmManager) {
        return new CategoryModelImpl(apiClient, realmManager);
    }

    @Provides
    CurrencyModel provideCurrencyModel(ApiClient apiClient, RealmManager realmManager) {
        return new CurrencyModelImpl(apiClient, realmManager);
    }

    @Provides
    AccountModel provideAccountModel(ApiClient apiClient, RealmManager realmManager,
                                     PreferenceUtil preferenceUtil) {
        return new AccountModelImpl(apiClient, realmManager, preferenceUtil);
    }

    @Provides
    OrganizationModel provideOrganizationModel(ApiClient apiClient, RealmManager realmManager,
                                               PreferenceUtil preferenceUtil) {
        return new OrganizationModelImpl(apiClient, realmManager, preferenceUtil);
    }
}