package alex.moneymanager.activities;

import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;

import alex.moneymanager.application.MoneyManagerApplication;
import alex.moneymanager.utils.PreferenceUtil;

public class SplashActivity extends BaseActivity {

    @Inject
    PreferenceUtil preferenceUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MoneyManagerApplication) getApplication()).component().inject(this);

        if (preferenceUtil.getUser() == null) {
            startActivity(new Intent(this, AuthActivity.class));
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }
//            startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}