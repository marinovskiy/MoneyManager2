package alex.moneymanager.models;

import java.util.List;

import alex.moneymanager.api.response.OrganizationResponse;
import alex.moneymanager.api.response.OrganizationsResponse;
import alex.moneymanager.api.response.SimpleRespone;
import alex.moneymanager.entities.db.Organization;
import alex.moneymanager.entities.network.NetworkOrganization;
import io.reactivex.Observable;
import retrofit2.Response;

public interface OrganizationModel extends BaseModel {

    Observable<Response<OrganizationsResponse>> userOrganizationsApi();

    void saveOrganizationsDb(List<Organization> organizations);

    void saveOrganizationDb(Organization organization);

    Observable<List<Organization>> userOrganizationsDb();

    Observable<Response<OrganizationResponse>> organizationByIdApi(int organizationId);

    Observable<Organization> organizationByIdDb(int organizationId);

    Observable<Response<OrganizationResponse>> newOrganization(NetworkOrganization organization);

    Observable<Response<SimpleRespone>> addMember(int organizationId, int userId);

    Observable<Response<SimpleRespone>> removeMember(int organizationId, int userId);

}