package alex.moneymanager.api.response;

import java.util.List;

import alex.moneymanager.entities.db.Organization;

public class OrganizationsResponse {

    private List<Organization> organizations;

    public OrganizationsResponse() {
    }

    public OrganizationsResponse(List<Organization> organizations) {
        this.organizations = organizations;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }
}