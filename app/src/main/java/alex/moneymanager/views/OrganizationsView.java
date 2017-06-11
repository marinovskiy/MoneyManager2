package alex.moneymanager.views;

import alex.moneymanager.entities.db.Organization;

public interface OrganizationsView extends BaseView {

    void setOrganization(Organization organization);

    void showProgressDialog();

    void dismissProgressDialog();

    void showErrorDialog(int errorCase);

}