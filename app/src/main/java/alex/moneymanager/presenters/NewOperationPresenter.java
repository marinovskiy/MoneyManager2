package alex.moneymanager.presenters;

import alex.moneymanager.entities.network.NetworkOperation;
import alex.moneymanager.views.NewOperationView;

public interface NewOperationPresenter extends BasePresenter<NewOperationView> {

    void loadCategories();

    void addNewOperation(int accountId, NetworkOperation operation);

}