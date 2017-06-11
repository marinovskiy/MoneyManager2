package alex.moneymanager.views;

import java.util.List;

import alex.moneymanager.entities.db.Organization;
import alex.moneymanager.entities.db.User;

public interface OrganizationMembersView extends BaseView {

    void setMembers(Organization organization);

    void showProgressDialog();

    void dismissProgressDialog();

    void showErrorDialog();

}