package alex.moneymanager.models;

import java.util.List;

import alex.moneymanager.api.ApiClient;
import alex.moneymanager.api.response.OrganizationsResponse;
import alex.moneymanager.db.RealmManager;
import alex.moneymanager.entities.db.Organization;
import alex.moneymanager.utils.PreferenceUtil;
import io.reactivex.Observable;
import io.realm.RealmResults;
import retrofit2.Response;

public class OrganizationModelImpl implements OrganizationModel {

    private ApiClient apiClient;
    private RealmManager realmManager;
    private PreferenceUtil preferenceUtil;

    public OrganizationModelImpl(ApiClient apiClient, RealmManager realmManager,
                                 PreferenceUtil preferenceUtil) {
        this.apiClient = apiClient;
        this.realmManager = realmManager;
        this.preferenceUtil = preferenceUtil;
    }

    @Override
    public Observable<Response<OrganizationsResponse>> userOrganizationsApi() {
        return apiClient.getApiService().userOrganizations(preferenceUtil.getApiKey());
    }

    @Override
    public void saveOrganizationsDb(List<Organization> organizations) {
        realmManager.getRealm().executeTransaction(realm -> realm.copyToRealmOrUpdate(organizations));
    }

    @Override
    public void saveOrganizationDb(Organization organization) {
        realmManager.getRealm().executeTransaction(realm -> realm.copyToRealmOrUpdate(organization));
    }

    @Override
    public Observable<List<Organization>> userOrganizationsDb() {
        RealmResults<Organization> organizations = realmManager.getRealm()
                .where(Organization.class)
                .findAll();

        List<Organization> unManagedOrganizations = realmManager.getRealm().copyFromRealm(organizations);

        return Observable.just(unManagedOrganizations);
    }
}