package alex.moneymanager.api.request;

import alex.moneymanager.entities.network.NetworkOrganization;

public class NewOrganizationRequest {

    private NetworkOrganization organization;

    public NewOrganizationRequest() {
    }

    public NewOrganizationRequest(NetworkOrganization organization) {
        this.organization = organization;
    }

    public NetworkOrganization getOrganization() {
        return organization;
    }

    public void setOrganization(NetworkOrganization organization) {
        this.organization = organization;
    }
}