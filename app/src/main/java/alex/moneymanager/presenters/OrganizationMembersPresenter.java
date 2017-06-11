package alex.moneymanager.presenters;

import alex.moneymanager.views.OrganizationMembersView;

public interface OrganizationMembersPresenter extends BasePresenter<OrganizationMembersView> {

    void loadMembersApi(int organizationId);

    void loadMembersDb(int organizationId);

}