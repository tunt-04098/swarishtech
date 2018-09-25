package com.swarish.app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

/**
 * Created by TuNT on 9/25/2018.
 * tunt.program.04098@gmail.com
 */
public class MainActivity extends FragmentActivity {

    private static final String URL_HOME = "https://swarish.in/";
    private static final String URL_ALL_STORES = "https://swarish.in/store/all";
    private static final String URL_ALL_CATEGORIES = "https://swarish.in/category/all";
    private static final String URL_MY_ACCOUNT = "https://swarish.in/login.html";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            loadFragment(WebFragment.getInstance(URL_HOME, getString(R.string.title_home)));
                            return true;
                        case R.id.navigation_all_stores:
                            loadFragment(WebFragment.getInstance(URL_ALL_STORES, getString(R.string.title_all_stores)));
                            return true;
                        case R.id.navigation_all_categories:
                            loadFragment(WebFragment.getInstance(URL_ALL_CATEGORIES, getString(R.string.title_all_categories)));
                            return true;
                        case R.id.navigation_my_account:
                            loadFragment(WebFragment.getInstance(URL_MY_ACCOUNT, getString(R.string.title_my_account)));
                            return true;
                        case R.id.navigation_more:
                            loadFragment(MoreFragment.getInstance());
                            return true;
                    }
                    return false;
                }
            };

    private BottomNavigationView navigation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = findViewById(R.id.navigation);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadFragment(WebFragment.getInstance(URL_HOME, getString(R.string.title_home)));
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.content, fragment);
        getSupportFragmentManager().popBackStack();

        ft.addToBackStack(Integer.toString((int) (2.147483646E9D * Math.random())));
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content);
        if (((BackView) fragment).onBackPressed()) {
            if (getSupportFragmentManager().getBackStackEntryCount() <= 1) {
                finish();
            }
        }
    }
}