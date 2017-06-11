package alex.moneymanager.models;

import alex.moneymanager.api.response.UnsecuredListsResponse;
import io.reactivex.Observable;
import retrofit2.Response;

public interface InitialDataModel extends BaseModel {

    Observable<Response<UnsecuredListsResponse>> loadInitialData();

}