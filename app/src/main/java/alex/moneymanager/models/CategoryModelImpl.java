package alex.moneymanager.models;

import java.util.List;

import alex.moneymanager.api.ApiClient;
import alex.moneymanager.api.response.CategoriesResponse;
import alex.moneymanager.db.RealmManager;
import alex.moneymanager.entities.db.Category;
import io.reactivex.Observable;
import retrofit2.Response;

public class CategoryModelImpl implements CategoryModel {

    private ApiClient apiClient;
    private RealmManager realmManager;

    public CategoryModelImpl(ApiClient apiClient, RealmManager realmManager) {
        this.apiClient = apiClient;
        this.realmManager = realmManager;
    }

    @Override
    public Observable<Response<CategoriesResponse>> categoriesApi() {
        return apiClient.getApiService().categories();
    }

    @Override
    public void saveCategoriesDb(List<Category> categories) {
        realmManager.getRealm().executeTransaction(realm -> realm.copyToRealmOrUpdate(categories));
    }
}