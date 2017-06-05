package alex.moneymanager.api;

import alex.moneymanager.api.response.CategoriesResponse;
import alex.moneymanager.api.response.CurrenciesResponse;
import alex.moneymanager.entities.network.User;
import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {

    /**
     * Auth requests
     */

    @POST("login")
    Observable<Response<User>> login(
            @Header("platformType") String platformType,
            @Header("udid") String udid,
            @Body User user
    );

    @POST("registration")
    Observable<Response<User>> registration(
            @Header("platformType") String platformType,
            @Header("udid") String udid,
            @Body User user
    );

    /**
     * Categories requests
     */

    @GET("categories")
    Observable<Response<CategoriesResponse>> categories();

    /**
     * Currencies requests
     */

    @GET("currencies")
    Observable<Response<CurrenciesResponse>> currencies();
}