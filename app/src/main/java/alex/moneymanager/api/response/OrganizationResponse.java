package alex.moneymanager.api.response;

import alex.moneymanager.entities.db.Organization;

public class OrganizationResponse {

    private Organization organization;

    public OrganizationResponse() {
    }

    public OrganizationResponse(Organization organization) {
        this.organization = organization;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}