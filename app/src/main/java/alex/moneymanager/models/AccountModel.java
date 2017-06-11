package alex.moneymanager.models;

import java.util.List;

import alex.moneymanager.api.response.AccountResponse;
import alex.moneymanager.api.response.AccountsResponse;
import alex.moneymanager.entities.db.Account;
import alex.moneymanager.entities.network.NetworkAccount;
import io.reactivex.Observable;
import retrofit2.Response;

public interface AccountModel extends BaseModel {

    Observable<Response<AccountsResponse>> userAccountsApi();

    void saveAccountsDb(List<Account> accounts);

    void saveAccountDb(Account account);

    Observable<List<Account>> userAccountsDb();

    Observable<Response<AccountResponse>> newUserAccountApi(NetworkAccount account);

    Observable<Response<AccountResponse>> userAccountApi(int accountId);

    Observable<Account> userAccountDb(int accountId);

}