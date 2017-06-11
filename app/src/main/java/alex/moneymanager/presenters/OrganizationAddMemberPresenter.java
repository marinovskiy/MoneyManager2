package alex.moneymanager.presenters;

import alex.moneymanager.views.OrganizationAddMemberView;

public interface OrganizationAddMemberPresenter extends BasePresenter<OrganizationAddMemberView> {

    void searchUsers(String email);

    void addMember(int organizationId, int memberId);

}