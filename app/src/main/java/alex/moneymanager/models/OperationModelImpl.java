package alex.moneymanager.models;

import alex.moneymanager.api.ApiClient;
import alex.moneymanager.api.request.NewUserOperationRequest;
import alex.moneymanager.api.response.OperationResponse;
import alex.moneymanager.api.response.SimpleRespone;
import alex.moneymanager.db.DbFields;
import alex.moneymanager.db.RealmManager;
import alex.moneymanager.entities.db.Operation;
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

    @Override
    public Observable<Response<OperationResponse>> editOperation(int accountId, int operationId,
                                                                 NetworkOperation operation) {
        return apiClient.getApiService().editUserOperation(
                preferenceUtil.getApiKey(),
                accountId,
                operationId,
                new NewUserOperationRequest(operation)
        );
    }

    @Override
    public Observable<Response<SimpleRespone>> deleteOperation(int accountId, int operationId) {
        return apiClient.getApiService().deleteUserOperation(
                preferenceUtil.getApiKey(),
                accountId,
                operationId
        );
    }

    @Override
    public Observable<Operation> operationByIdDb(int operationId) {
        Operation operation = realmManager.getRealm()
                .where(Operation.class)
                .equalTo(DbFields.OPERATION_ID, operationId)
                .findFirst();

        Operation unManagedOperation = realmManager.getRealm().copyFromRealm(operation);

        return Observable.just(unManagedOperation);
    }
}