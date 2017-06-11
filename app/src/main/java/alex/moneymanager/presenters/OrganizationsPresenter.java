package alex.moneymanager.presenters;

import alex.moneymanager.entities.network.NetworkAccount;
import alex.moneymanager.views.OrganizationsView;

public interface OrganizationsPresenter extends BasePresenter<OrganizationsView> {

    void loadOrganization(int organizationId);

    void deleteAccount(int organizationId, int accountId);

}