package alex.moneymanager.models;

import alex.moneymanager.api.ApiClient;
import alex.moneymanager.api.response.CurrenciesResponse;
import io.reactivex.Observable;
import retrofit2.Response;

public class CurrencyModelImpl implements CurrencyModel {

    private ApiClient apiClient;

    public CurrencyModelImpl(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    public Observable<Response<CurrenciesResponse>> currenciesApi() {
        return apiClient.getApiService().currencies();
    }
}