package com.example.product;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.product.Adapter.AdapterViewPagerHome;
import com.example.product.Fragment.FragmentGioHang;
import com.example.product.Fragment.FragmentHome;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class NavigationView extends AppCompatActivity
        implements com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener {
    private static final int FRAGMENT_HOME = 1;
    private static final int FRAGMENT_HOME2 = 2;
    private DrawerLayout mDrawerLayout;
    private com.google.android.material.navigation.NavigationView navigationView;
    private int currentFragment = FRAGMENT_HOME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_view);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayShowTitleEnabled(false);

//        getSupportActionBar().setTitle("Hello World");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        replaceFragment(new FragmentHome());

        /////// navigationView
        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
//        navigationView.setNavigationItemSelectedListener(
//                new com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(MenuItem menuItem) {
//                        // set item as selected to persist highlight
//                        menuItem.setChecked(true);
//                        int id = menuItem.getItemId();
//                        if (id == R.id.nav_home) {
//                            if(FRAGMENT_HOME!=currentFragment){
//                                replaceFragment(new FragmentOne());
//                                currentFragment = FRAGMENT_HOME;
//                            }
//                        }
//                        // close drawer when item is tapped
//                        mDrawerLayout.closeDrawers();
//
//                        // Add code here to update the UI based on the item selected
//                        // For example, swap UI fragments here
//                        return true;
//                    }
//
//                });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close
        );
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();



        //bottom_navigation

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_sanpham_bottom:
                        replaceFragment(new FragmentHome());
                        break;
                    case R.id.nav_giohang_bottom: {
                        replaceFragment(new FragmentGioHang());
                        break;
                    }
                }

            }
        });

    }

    // Set custom menu bar


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    // bắt sự kiện cho nút trên Action Bar
    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.item_logout:
                Intent intent = new Intent(NavigationView.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.item_dangnhap:
                Intent intent2 = new Intent(NavigationView.this, MainActivity.class);
                startActivity(intent2);
                break;
            case R.id.item_dangky:
                Intent intent1 = new Intent(NavigationView.this, Register.class);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_fragment, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            if (FRAGMENT_HOME != currentFragment) {
                replaceFragment(new FragmentHome());
                currentFragment = FRAGMENT_HOME;
            }
        } else if (id == R.id.nav_Login) {

            Intent intent = new Intent(NavigationView.this, Home.class);
            startActivity(intent);
        }
        return false;

    }

}