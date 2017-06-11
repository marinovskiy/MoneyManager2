package alex.moneymanager.models;

import java.util.List;

import alex.moneymanager.api.response.OrganizationsResponse;
import alex.moneymanager.entities.db.Organization;
import io.reactivex.Observable;
import retrofit2.Response;

public interface OrganizationModel extends BaseModel {

    Observable<Response<OrganizationsResponse>> userOrganizationsApi();

    void saveOrganizationsDb(List<Organization> organizations);

    void saveOrganizationDb(Organization organization);

    Observable<List<Organization>> userOrganizationsDb();

}