package alex.moneymanager.views;

import java.util.List;

import alex.moneymanager.entities.db.User;

public interface OrganizationAddMemberView extends BaseView {

    void setSearchResults(List<User> users);

    void memberAddedSuccessful();

    void alreadyMember();

    void showProgressDialog();

    void dismissProgressDialog();

    void showErrorDialog(int errorCase);

}