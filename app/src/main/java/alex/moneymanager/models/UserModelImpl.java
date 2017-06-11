package alex.moneymanager.models;

import alex.moneymanager.api.ApiClient;
import alex.moneymanager.api.response.UsersResponse;
import alex.moneymanager.utils.PreferenceUtil;
import io.reactivex.Observable;
import retrofit2.Response;

public class UserModelImpl implements UserModel {

    private ApiClient apiClient;
    private PreferenceUtil preferenceUtil;

    public UserModelImpl(ApiClient apiClient, PreferenceUtil preferenceUtil) {
        this.apiClient = apiClient;
        this.preferenceUtil = preferenceUtil;
    }

    @Override
    public Observable<Response<UsersResponse>> searchUsersByEmail(String email) {
        return apiClient.getApiService().usersSearch(
                preferenceUtil.getApiKey(),
                email
        );
    }
}