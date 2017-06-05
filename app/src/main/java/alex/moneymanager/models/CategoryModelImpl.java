package alex.moneymanager.models;

import alex.moneymanager.api.ApiClient;
import alex.moneymanager.api.response.CategoriesResponse;
import io.reactivex.Observable;
import retrofit2.Response;

public class CategoryModelImpl implements CategoryModel {

    private ApiClient apiClient;

    public CategoryModelImpl(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    public Observable<Response<CategoriesResponse>> categoriesApi() {
        return apiClient.getApiService().categories();
    }
}