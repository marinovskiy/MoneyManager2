package alex.moneymanager.models;

import java.util.List;

import alex.moneymanager.api.response.NewsListResponse;
import alex.moneymanager.api.response.NewsResponse;
import alex.moneymanager.api.response.SimpleRespone;
import alex.moneymanager.entities.db.News;
import io.reactivex.Observable;
import retrofit2.Response;

public interface NewsModel extends BaseModel {

    Observable<Response<NewsListResponse>> newsListApi();

    Observable<Response<NewsResponse>> newsDetailsApi(int id);

    Observable<Response<SimpleRespone>> addCommentApi(int id, String text);

    Observable<List<News>> newsListDb();

    Observable<News> newsDetailsDb(int id);

    void saveNewsList(List<News> newsList);

    void saveNews(News news);
}