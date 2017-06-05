package alex.moneymanager.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

public class SystemUtils {

    private Context context;

    public SystemUtils(Context context) {
        this.context = context;
    }

    public boolean isConnected() {
        NetworkInfo networkInfo = getNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    private NetworkInfo getNetworkInfo() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE
        );
        return cm.getActiveNetworkInfo();
    }

    @SuppressLint("HardwareIds")
    public String getUdid() {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
}