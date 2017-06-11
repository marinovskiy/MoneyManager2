package alex.moneymanager.models;

import alex.moneymanager.api.response.UsersResponse;
import io.reactivex.Observable;
import retrofit2.Response;

public interface UserModel extends BaseModel {

    Observable<Response<UsersResponse>> searchUsersByEmail(String email);

}