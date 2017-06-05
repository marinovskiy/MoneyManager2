package alex.moneymanager.di.module;

import android.content.Context;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import alex.moneymanager.api.ApiClient;
import alex.moneymanager.utils.PreferenceUtil;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetModule {

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(PreferenceUtil preferenceUtil, Context context) {
        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
//                .addInterceptor(chain -> { // add isModifiedSince header for unsecured lists requests
//                    Request request = chain.request();
//
//                    List<String> pathSegments = request.url().pathSegments();
//                    if (request.method().equals("GET") && !pathSegments.isEmpty()) {
//
//                        // get requestName and lastModified info for gotten request
//                        String requestName = pathSegments.get(pathSegments.size() - 1);
//                        String lastModified = preferenceUtil.getLastModified(requestName);
//
//                        // add is modified header if need
//                        if (ApiClient.requestsWithModifyHeader.contains(requestName) && lastModified != null) {
//                            request = request.newBuilder()
//                                    .addHeader(ApiClient.HEADER_IS_MODIFIED_SINCE, lastModified)
//                                    .build();
//                        }
//                    }
//
//                    // add api key header if it is not null
//                    if (preferenceUtil.getApiKey() != null) {
//                        request = request.newBuilder()
//                                .addHeader(ApiClient.HEADER_X_API_KEY, preferenceUtil.getApiKey())
//                                .build();
//                    }
//
//                    return chain.proceed(request);
//                })
                .addInterceptor(new HttpLoggingInterceptor().setLevel(
                        HttpLoggingInterceptor.Level.BODY
                ))
                .addInterceptor(new ChuckInterceptor(context))
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(ApiClient.BASE_URL)
                .client(okHttpClient)
//                .addConverterFactory(GsonConverterFactory.create(createGsonConverter()))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    ApiClient provideApiClient(Retrofit retrofit) {
        return new ApiClient(retrofit);
    }

//    private Gson createGsonConverter() {
//        return new GsonBuilder()
//                .setExclusionStrategies(new ExclusionStrategy() {
//                    @Override
//                    public boolean shouldSkipField(FieldAttributes f) {
//                        return f.getDeclaringClass().equals(RealmObject.class);
//                    }
//
//                    @Override
//                    public boolean shouldSkipClass(Class<?> clazz) {
//                        return false;
//                    }
//                })
//                .registerTypeAdapter(new TypeToken<RealmList<RealmInteger>>() {
//                }.getType(), new TypeAdapter<RealmList<RealmInteger>>() {
//
//                    @Override
//                    public void write(JsonWriter out, RealmList<RealmInteger> value) throws IOException {
//                        // Ignore
//                    }
//
//                    @Override
//                    public RealmList<RealmInteger> read(JsonReader in) throws IOException {
//                        RealmList<RealmInteger> list = new RealmList<>();
//                        in.beginArray();
//                        while (in.hasNext()) {
//                            list.add(new RealmInteger(in.nextInt()));
//                        }
//                        in.endArray();
//                        return list;
//                    }
//                })
//                .create();
//    }
}
