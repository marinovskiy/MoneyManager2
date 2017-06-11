package alex.moneymanager.models;

import alex.moneymanager.entities.db.User;
import io.reactivex.Observable;
import retrofit2.Response;

public interface AuthModel extends BaseModel {

    Observable<Response<User>> login(User user);

    Observable<Response<User>> registration(User user);

}