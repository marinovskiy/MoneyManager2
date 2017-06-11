package alex.moneymanager.api;

import alex.moneymanager.api.request.NewUserAccountRequest;
import alex.moneymanager.api.request.NewUserOperationRequest;
import alex.moneymanager.api.request.RegistrationRequest;
import alex.moneymanager.api.request.UserLoginRequest;
import alex.moneymanager.api.response.AccountResponse;
import alex.moneymanager.api.response.AccountsResponse;
import alex.moneymanager.api.response.CategoriesResponse;
import alex.moneymanager.api.response.CurrenciesResponse;
import alex.moneymanager.api.response.OperationResponse;
import alex.moneymanager.api.response.OrganizationsResponse;
import alex.moneymanager.api.response.UnsecuredListsResponse;
import alex.moneymanager.entities.db.User;
import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    /**
     * Auth requests
     */

    @POST("auth/login")
    Observable<Response<User>> login(
            @Header("platformType") String platformType,
            @Header("udid") String udid,
            @Body UserLoginRequest request
    );

    @POST("auth/registration")
    Observable<Response<User>> registration(
            @Header("platformType") String platformType,
            @Header("udid") String udid,
            @Body RegistrationRequest request
    );

    /**
     * Unsecured lists requests
     */
    @GET("unsecuredLists")
    Observable<Response<UnsecuredListsResponse>> unsecuredLists();

    @GET("categories")
    Observable<Response<CategoriesResponse>> categories();

    @GET("currencies")
    Observable<Response<CurrenciesResponse>> currencies();

    /**
     * Accounts requests
     */
    @GET("accounts")
    Observable<Response<AccountsResponse>> userAccounts(@Header("X-AUTH-TOKEN") String apiKey);

    @POST("accounts/new")
    Observable<Response<AccountResponse>> newUserAccount(
            @Header("X-AUTH-TOKEN") String apiKey,
            @Body NewUserAccountRequest request
    );

    @GET("accounts/{accountId}")
    Observable<Response<AccountResponse>> userAccount(
            @Header("X-AUTH-TOKEN") String apiKey,
            @Path("accountId") int accountId
    );

    /**
     * Organizations requests
     */
    @GET("organizations/all")
    Observable<Response<OrganizationsResponse>> userOrganizations(
            @Header("X-AUTH-TOKEN") String apiKey
    );

    /**
     * Operations requests
     */
    @POST("accounts/{accountId}/operations/add")
    Observable<Response<OperationResponse>> newUserOperation(
            @Header("X-AUTH-TOKEN") String apiKey,
            @Path("accountId") int accountId,
            @Body NewUserOperationRequest request
    );
}