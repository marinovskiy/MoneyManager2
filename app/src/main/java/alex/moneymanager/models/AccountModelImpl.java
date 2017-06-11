package alex.moneymanager.models;

import java.util.List;

import alex.moneymanager.api.ApiClient;
import alex.moneymanager.api.request.NewUserAccountRequest;
import alex.moneymanager.api.response.AccountResponse;
import alex.moneymanager.api.response.AccountsResponse;
import alex.moneymanager.db.DbFields;
import alex.moneymanager.db.RealmManager;
import alex.moneymanager.entities.db.Account;
import alex.moneymanager.entities.network.NetworkAccount;
import alex.moneymanager.utils.PreferenceUtil;
import io.reactivex.Observable;
import io.realm.RealmResults;
import retrofit2.Response;

public class AccountModelImpl implements AccountModel {

    private ApiClient apiClient;
    private RealmManager realmManager;
    private PreferenceUtil preferenceUtil;

    public AccountModelImpl(ApiClient apiClient, RealmManager realmManager,
                            PreferenceUtil preferenceUtil) {
        this.apiClient = apiClient;
        this.realmManager = realmManager;
        this.preferenceUtil = preferenceUtil;
    }

    @Override
    public Observable<Response<AccountsResponse>> userAccountsApi() {
        return apiClient.getApiService().userAccounts(preferenceUtil.getApiKey());
    }

    @Override
    public void saveAccountsDb(List<Account> accounts) {
        realmManager.getRealm().executeTransaction(realm -> realm.copyToRealmOrUpdate(accounts));
    }

    @Override
    public void saveAccountDb(Account account) {
        realmManager.getRealm().executeTransaction(realm -> realm.copyToRealmOrUpdate(account));
    }

    @Override
    public Observable<List<Account>> userAccountsDb() {
        RealmResults<Account> accounts = realmManager.getRealm()
                .where(Account.class)
                .findAll();

        List<Account> unManagedAccounts = realmManager.getRealm().copyFromRealm(accounts);

        return Observable.just(unManagedAccounts);
    }

    @Override
    public Observable<Response<AccountResponse>> newUserAccountApi(NetworkAccount account) {
        return apiClient.getApiService().newUserAccount(
                preferenceUtil.getApiKey(),
                new NewUserAccountRequest(account)
        );
    }

    @Override
    public Observable<Response<AccountResponse>> userAccountApi(int accountId) {
        return null;
    }

    @Override
    public Observable<Account> userAccountDb(int accountId) {
        Account account = realmManager.getRealm()
                .where(Account.class)
                .equalTo(DbFields.ACCOUNT_ID, accountId)
                .findFirst();

        Account unManagedAccount = realmManager.getRealm().copyFromRealm(account);

        return Observable.just(unManagedAccount);
    }
}