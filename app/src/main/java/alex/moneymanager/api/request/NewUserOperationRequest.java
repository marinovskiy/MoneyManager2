package alex.moneymanager.api.request;

import alex.moneymanager.entities.network.NetworkOperation;

public class NewUserOperationRequest {

    private NetworkOperation operation;

    public NewUserOperationRequest() {
    }

    public NewUserOperationRequest(NetworkOperation operation) {
        this.operation = operation;
    }

    public NetworkOperation getOperation() {
        return operation;
    }

    public void setOperation(NetworkOperation operation) {
        this.operation = operation;
    }
}