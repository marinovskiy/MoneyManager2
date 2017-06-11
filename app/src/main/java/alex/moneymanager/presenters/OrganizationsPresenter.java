package alex.moneymanager.presenters;

import alex.moneymanager.views.OrganizationsView;

public interface OrganizationsPresenter extends BasePresenter<OrganizationsView> {

    void loadOrganization(int organizationId);

//    void editOperation(int accountId, int operationId, NetworkOperation operation);
//
//    void deleteOperation(int accountId, int operationId);

}