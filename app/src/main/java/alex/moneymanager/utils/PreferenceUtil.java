package alex.moneymanager.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import alex.moneymanager.entities.db.User;

public class PreferenceUtil {

    private static final String INITIAL_DATA_LOADED = "prefs_key_initial_data_loaded";
    private static final String UDID = "prefs_key_udid";
    private static final String API_KEY = "prefs_key_api_key";
    private static final String USER = "prefs_key_user";
//    private static final String

    private SharedPreferences sharedPreferences;

    private Gson gson = new Gson();

    public PreferenceUtil(Context context) {
        sharedPreferences = context.getSharedPreferences(
                context.getPackageName(),
                Context.MODE_PRIVATE
        );
    }

    public boolean isInitialDataLoaded() {
        return sharedPreferences.getBoolean(INITIAL_DATA_LOADED, false);
    }

    public void setInitialDataLoaded(boolean value) {
        updateBooleanValue(INITIAL_DATA_LOADED, value);
    }

    public String getUdid() {
        return sharedPreferences.getString(UDID, null);
    }

    public void setUdid(String udid) {
        updateStringValue(UDID, udid);
    }

    public String getApiKey() {
        return sharedPreferences.getString(API_KEY, null);
    }

    public void setApiKey(String apiKey) {
        updateStringValue(API_KEY, apiKey);
    }

    public User getUser() {
        return gson.fromJson(sharedPreferences.getString(USER, null), User.class);
    }

    public void setUser(User user) {
        updateStringValue(USER, gson.toJson(user));
    }

    private void updateBooleanValue(String key, boolean value) {
        sharedPreferences.edit()
                .putBoolean(key, value)
                .apply();
    }

    private void updateStringValue(String key, String value) {
        sharedPreferences.edit()
                .putString(key, value)
                .apply();
    }
}