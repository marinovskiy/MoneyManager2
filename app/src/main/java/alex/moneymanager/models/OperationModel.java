package alex.moneymanager.models;

import alex.moneymanager.api.response.OperationResponse;
import alex.moneymanager.entities.network.NetworkOperation;
import io.reactivex.Observable;
import retrofit2.Response;

public interface OperationModel extends BaseModel {

    Observable<Response<OperationResponse>> newOperation(int accountId, NetworkOperation operation);

}