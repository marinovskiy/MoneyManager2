package alex.moneymanager.presenters;

import alex.moneymanager.entities.network.NetworkOrganization;
import alex.moneymanager.views.NewOrganizationView;

public interface NewOrganizationPresenter extends BasePresenter<NewOrganizationView> {

    void addNewOrganization(NetworkOrganization organization);

//    void loadOperationDb(int operationId);
//
//    void editOperation(int accountId, int operationId, NetworkOperation operation);

}