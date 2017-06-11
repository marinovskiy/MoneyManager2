package alex.moneymanager.models;

import java.util.List;

import alex.moneymanager.api.ApiClient;
import alex.moneymanager.api.response.CurrenciesResponse;
import alex.moneymanager.db.RealmManager;
import alex.moneymanager.entities.db.Currency;
import io.reactivex.Observable;
import io.realm.RealmResults;
import retrofit2.Response;

public class CurrencyModelImpl implements CurrencyModel {

    private ApiClient apiClient;
    private RealmManager realmManager;

    public CurrencyModelImpl(ApiClient apiClient, RealmManager realmManager) {
        this.apiClient = apiClient;
        this.realmManager = realmManager;
    }

    @Override
    public Observable<Response<CurrenciesResponse>> currenciesApi() {
        return apiClient.getApiService().currencies();
    }

    @Override
    public void saveCurrenciesDb(List<Currency> currencies) {
        realmManager.getRealm().executeTransaction(realm -> realm.copyToRealmOrUpdate(currencies));
    }

    @Override
    public Observable<List<Currency>> currenciesDb() {
        RealmResults<Currency> currencies = realmManager.getRealm()
                .where(Currency.class)
                .findAll();

        List<Currency> unManagedCurrencies = realmManager.getRealm().copyFromRealm(currencies);

        return Observable.just(unManagedCurrencies);
    }
}