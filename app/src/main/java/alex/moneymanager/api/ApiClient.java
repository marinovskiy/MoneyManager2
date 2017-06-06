package alex.moneymanager.api;

import retrofit2.Retrofit;

public class ApiClient {

//    public static final String BASE_URL = "http://127.0.0.1:8000/api/";
    public static final String BASE_URL = "http://127.0.0.1:8000/api/";

    public static final String HEADER_X_API_KEY = "X-AUTH-TOKEN";

    public static final String PLATFORM = "android";

    private ApiService apiService;

    public ApiClient(Retrofit retrofit) {
        apiService = retrofit.create(ApiService.class);
    }

    public ApiService getApiService() {
        return apiService;
    }
}