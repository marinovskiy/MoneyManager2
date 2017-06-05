package alex.moneymanager.models;

import alex.moneymanager.api.response.CategoriesResponse;
import io.reactivex.Observable;
import retrofit2.Response;

public interface CategoryModel extends BaseModel {

    Observable<Response<CategoriesResponse>> categoriesApi();

}