package alex.moneymanager.models;

import alex.moneymanager.api.response.CurrenciesResponse;
import io.reactivex.Observable;
import retrofit2.Response;

public interface CurrencyModel extends BaseModel {

    Observable<Response<CurrenciesResponse>> currenciesApi();

}