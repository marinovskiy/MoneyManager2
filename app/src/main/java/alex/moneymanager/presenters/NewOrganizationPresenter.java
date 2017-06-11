package alex.moneymanager.presenters;

import alex.moneymanager.entities.network.NetworkOrganization;
import alex.moneymanager.views.NewOrganizationView;

public interface NewOrganizationPresenter extends BasePresenter<NewOrganizationView> {

    void addNewOrganization(NetworkOrganization organization);

    void loadOrganizationDb(int organizationId);

    void editOrganization(int organizationId, NetworkOrganization organization);

}