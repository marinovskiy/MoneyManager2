package alex.moneymanager.models;

import java.util.List;

import alex.moneymanager.api.ApiClient;
import alex.moneymanager.api.request.AddCommentRequest;
import alex.moneymanager.api.response.NewsListResponse;
import alex.moneymanager.api.response.NewsResponse;
import alex.moneymanager.api.response.SimpleRespone;
import alex.moneymanager.db.DbFields;
import alex.moneymanager.db.RealmManager;
import alex.moneymanager.entities.db.Comment;
import alex.moneymanager.entities.db.News;
import alex.moneymanager.utils.PreferenceUtil;
import io.reactivex.Observable;
import io.realm.RealmResults;
import retrofit2.Response;

public class NewsModelImpl implements NewsModel {

    private ApiClient apiClient;
    private RealmManager realmManager;
    private PreferenceUtil preferenceUtil;

    public NewsModelImpl(ApiClient apiClient, RealmManager realmManager,
                         PreferenceUtil preferenceUtil) {
        this.apiClient = apiClient;
        this.realmManager = realmManager;
        this.preferenceUtil = preferenceUtil;
    }

    @Override
    public Observable<Response<NewsListResponse>> newsListApi() {
        return apiClient.getApiService().newsList(preferenceUtil.getApiKey());
    }

    @Override
    public Observable<Response<NewsResponse>> newsDetailsApi(int id) {
        return apiClient.getApiService().newsDetails(preferenceUtil.getApiKey(), id);
    }

    @Override
    public Observable<Response<SimpleRespone>> addCommentApi(int id, String text) {
        return apiClient.getApiService().addComment(preferenceUtil.getApiKey(), id, new AddCommentRequest(new Comment(text)));
    }

    @Override
    public Observable<List<News>> newsListDb() {
        RealmResults<News> newsList = realmManager.getRealm()
                .where(News.class)
                .findAll();

        List<News> unManagedNewsList = realmManager.getRealm().copyFromRealm(newsList);

        return Observable.just(unManagedNewsList);
    }

    @Override
    public Observable<News> newsDetailsDb(int id) {
        News news = realmManager.getRealm()
                .where(News.class)
                .equalTo(DbFields.NEWS_ID, id)
                .findFirst();

        News unManagedNews = realmManager.getRealm().copyFromRealm(news);

        return Observable.just(unManagedNews);
    }

    @Override
    public void saveNewsList(List<News> newsList) {
        realmManager.getRealm().executeTransaction(realm -> realm.copyToRealmOrUpdate(newsList));
    }

    @Override
    public void saveNews(News news) {
        realmManager.getRealm().executeTransaction(realm -> realm.copyToRealmOrUpdate(news));
    }
}