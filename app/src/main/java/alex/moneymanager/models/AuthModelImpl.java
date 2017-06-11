package alex.moneymanager.models;

import alex.moneymanager.api.ApiClient;
import alex.moneymanager.api.request.RegistrationRequest;
import alex.moneymanager.api.request.UserLoginRequest;
import alex.moneymanager.entities.db.User;
import alex.moneymanager.utils.PreferenceUtil;
import io.reactivex.Observable;
import retrofit2.Response;

public class AuthModelImpl implements AuthModel {

    private ApiClient apiClient;
    private PreferenceUtil preferenceUtil;

    public AuthModelImpl(ApiClient apiClient, PreferenceUtil preferenceUtil) {
        this.apiClient = apiClient;
        this.preferenceUtil = preferenceUtil;
    }

    @Override
    public Observable<Response<User>> login(User user) {
        return apiClient.getApiService().login(
                ApiClient.PLATFORM,
                preferenceUtil.getUdid(),
                new UserLoginRequest(user)
        );
    }

    @Override
    public Observable<Response<User>> registration(User user) {
        return apiClient.getApiService().registration(
                ApiClient.PLATFORM,
                preferenceUtil.getUdid(),
                new RegistrationRequest(user)
        );
    }
}