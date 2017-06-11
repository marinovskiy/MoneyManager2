package alex.moneymanager.api.response;

import alex.moneymanager.entities.db.Operation;

public class OperationResponse {

    private Operation operation;

    public OperationResponse() {
    }

    public OperationResponse(Operation operation) {
        this.operation = operation;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}