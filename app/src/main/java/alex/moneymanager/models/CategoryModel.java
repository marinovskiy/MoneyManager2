package alex.moneymanager.models;

import java.util.List;

import alex.moneymanager.api.response.CategoriesResponse;
import alex.moneymanager.entities.db.Category;
import io.reactivex.Observable;
import retrofit2.Response;

public interface CategoryModel extends BaseModel {

    Observable<Response<CategoriesResponse>> categoriesApi();

    void saveCategoriesDb(List<Category> categories);

    Observable<List<Category>> categoriesDb();

}