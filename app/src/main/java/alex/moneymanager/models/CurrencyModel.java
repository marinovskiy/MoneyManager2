package alex.moneymanager.models;

import java.util.List;

import alex.moneymanager.api.response.CurrenciesResponse;
import alex.moneymanager.entities.db.Currency;
import io.reactivex.Observable;
import retrofit2.Response;

public interface CurrencyModel extends BaseModel {

    Observable<Response<CurrenciesResponse>> currenciesApi();

    void saveCurrenciesDb(List<Currency> currencies);

    Observable<List<Currency>> currenciesDb();

}