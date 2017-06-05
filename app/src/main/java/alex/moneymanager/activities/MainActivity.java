package alex.moneymanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import alex.moneymanager.R;
import butterknife.BindView;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout_main)
    DrawerLayout drawerLayout;
    @BindView(R.id.toolbar_main)
    Toolbar toolbar;
    @BindView(R.id.nav_view_main)
    NavigationView navView;

    private int drawerPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navView.setNavigationItemSelectedListener(this);
        navView.setCheckedItem(R.id.action_nav_home);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_nav_first_playlist:
//                if (drawerPosition != 1) {
//
//                    drawerPosition = 1;
//                }
//                break;
//            case R.id.action_nav_second_playlist:
//                if (drawerPosition != 2) {
//
//                    drawerPosition = 2;
//                }
//                break;
//            case R.id.action_nav_third_playlist:
//                if (drawerPosition != 3) {
//
//                    drawerPosition = 3;
//                }
//                break;
//            case R.id.action_nav_logout:
//                if (mUserId != null) {
//                    logOut();
//                } else {
//                    startActivity(new Intent(this, AuthActivity.class));
//                }
//                break;
//        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}