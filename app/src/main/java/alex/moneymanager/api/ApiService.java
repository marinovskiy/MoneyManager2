package alex.moneymanager.api;

import alex.moneymanager.api.request.NewOrganizationRequest;
import alex.moneymanager.api.request.NewUserAccountRequest;
import alex.moneymanager.api.request.NewUserOperationRequest;
import alex.moneymanager.api.request.RegistrationRequest;
import alex.moneymanager.api.request.UserLoginRequest;
import alex.moneymanager.api.response.AccountResponse;
import alex.moneymanager.api.response.AccountsResponse;
import alex.moneymanager.api.response.CategoriesResponse;
import alex.moneymanager.api.response.CurrenciesResponse;
import alex.moneymanager.api.response.OperationResponse;
import alex.moneymanager.api.response.OrganizationResponse;
import alex.moneymanager.api.response.OrganizationsResponse;
import alex.moneymanager.api.response.SimpleRespone;
import alex.moneymanager.api.response.UnsecuredListsResponse;
import alex.moneymanager.api.response.UsersResponse;
import alex.moneymanager.entities.db.Account;
import alex.moneymanager.entities.db.User;
import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

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

    @PUT("accounts/{accountId}/operations/{operationId}/edit")
    Observable<Response<OperationResponse>> editUserOperation(
            @Header("X-AUTH-TOKEN") String apiKey,
            @Path("accountId") int accountId,
            @Path("operationId") int operationId,
            @Body NewUserOperationRequest request
    );

    @DELETE("accounts/{accountId}/operations/{operationId}/delete")
    Observable<Response<SimpleRespone>> deleteUserOperation(
            @Header("X-AUTH-TOKEN") String apiKey,
            @Path("accountId") int accountId,
            @Path("operationId") int operationId
    );

    /**
     * Organizations requests
     */
    @POST("organizations/new")
    Observable<Response<OrganizationResponse>> newOrganization(
            @Header("X-AUTH-TOKEN") String apiKey,
            @Body NewOrganizationRequest request
    );

    @PUT("organizations/{organizationId}/edit")
    Observable<Response<OrganizationResponse>> editOrganization(
            @Header("X-AUTH-TOKEN") String apiKey,
            @Path("organizationId") int organizationId,
            @Body NewOrganizationRequest request
    );

    @PUT("organizations/{organizationId}/disable")
    Observable<Response<SimpleRespone>> disableOrganization(
            @Header("X-AUTH-TOKEN") String apiKey,
            @Path("organizationId") int organizationId
    );

    @PUT("organizations/{organizationId}/enable")
    Observable<Response<SimpleRespone>> enableOrganization(
            @Header("X-AUTH-TOKEN") String apiKey,
            @Path("organizationId") int organizationId
    );

    @PUT("organizations/{organizationId}/members/{userId}/add")
    Observable<Response<SimpleRespone>> addMember(
            @Header("X-AUTH-TOKEN") String apiKey,
            @Path("organizationId") int organizationId,
            @Path("userId") int userId
    );

    @PUT("organizations/{organizationId}/members/{userId}/remove")
    Observable<Response<SimpleRespone>> removeMember(
            @Header("X-AUTH-TOKEN") String apiKey,
            @Path("organizationId") int organizationId,
            @Path("userId") int userId
    );

    @GET("organizations/{organizationId}")
    Observable<Response<OrganizationResponse>> organizationDetails(
            @Header("X-AUTH-TOKEN") String apiKey,
            @Path("organizationId") int organizationId
    );

    @GET("organizations/all")
    Observable<Response<OrganizationsResponse>> allUserOrganizations(
            @Header("X-AUTH-TOKEN") String apiKey
    );

    @POST("organizations/{organizationId}/accounts/new")
    Observable<Response<AccountResponse>> newOrganizationAccount(
//    Observable<Response<Account>> newOrganizationAccount(
            @Header("X-AUTH-TOKEN") String apiKey,
            @Path("organizationId") int organizationId,
            @Body NewUserAccountRequest request
    );

    @PUT("organizations/{organizationId}/accounts/{accountId}/edit")
    Observable<Response<AccountResponse>> editOrganizationAccount(
            @Header("X-AUTH-TOKEN") String apiKey,
            @Path("organizationId") int organizationId,
            @Path("accountId") int accountId,
            @Body NewUserAccountRequest request
    );

    @DELETE("organizations/{organizationId}/accounts/{accountId}/delete")
    Observable<Response<SimpleRespone>> deleteOrganizationAccount(
            @Header("X-AUTH-TOKEN") String apiKey,
            @Path("organizationId") int organizationId,
            @Path("accountId") int accountId
    );

    /**
     * Users requests
     */
    @GET("users/search")
    Observable<Response<UsersResponse>> usersSearch(
            @Header("X-AUTH-TOKEN") String apiKey,
            @Query("email") String email
    );
}