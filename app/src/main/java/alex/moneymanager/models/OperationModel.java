package alex.moneymanager.models;

import alex.moneymanager.api.response.OperationResponse;
import alex.moneymanager.api.response.SimpleRespone;
import alex.moneymanager.entities.db.Operation;
import alex.moneymanager.entities.network.NetworkOperation;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;

public interface OperationModel extends BaseModel {

    Observable<Response<OperationResponse>> newOperation(int accountId, NetworkOperation operation);

    Observable<Response<OperationResponse>> editOperation(
            int accountId,
            int operationId,
            NetworkOperation operation
    );

    Observable<Response<SimpleRespone>> deleteOperation(int accountId, int operationId);

    Observable<Operation> operationByIdDb(int operationId);

}