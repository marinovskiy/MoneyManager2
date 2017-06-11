package alex.moneymanager.models;

import alex.moneymanager.api.ApiClient;
import alex.moneymanager.api.response.UnsecuredListsResponse;
import io.reactivex.Observable;
import retrofit2.Response;

public class InitialDataModelImpl implements InitialDataModel {

    private ApiClient apiClient;

    public InitialDataModelImpl(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    public Observable<Response<UnsecuredListsResponse>> loadInitialData() {
        return apiClient.getApiService().unsecuredLists();
    }
}