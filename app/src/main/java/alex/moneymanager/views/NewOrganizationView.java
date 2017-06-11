package alex.moneymanager.views;

import alex.moneymanager.entities.db.Organization;

public interface NewOrganizationView extends BaseView {

    void organizationAddedSuccess();

    void setOrganization(Organization organization);

    void showProgressDialog();

    void dismissProgressDialog();

    void showErrorDialog(int errorCase);

}