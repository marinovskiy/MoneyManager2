package alex.moneymanager.models;

import alex.moneymanager.api.ApiClient;
import alex.moneymanager.api.request.NewUserOperationRequest;
import alex.moneymanager.api.response.OperationResponse;
import alex.moneymanager.db.RealmManager;
import alex.moneymanager.entities.network.NetworkOperation;
import alex.moneymanager.utils.PreferenceUtil;
import io.reactivex.Observable;
import retrofit2.Response;

public class OperationModelImpl implements OperationModel {

    private ApiClient apiClient;
    private RealmManager realmManager;
    private PreferenceUtil preferenceUtil;

    public OperationModelImpl(ApiClient apiClient, RealmManager realmManager,
                              PreferenceUtil preferenceUtil) {
        this.apiClient = apiClient;
        this.realmManager = realmManager;
        this.preferenceUtil = preferenceUtil;
    }

    @Override
    public Observable<Response<OperationResponse>> newOperation(int accountId,
                                                                NetworkOperation operation) {
        return apiClient.getApiService().newUserOperation(
                preferenceUtil.getApiKey(),
                accountId,
                new NewUserOperationRequest(operation)
        );
    }
}