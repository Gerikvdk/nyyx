package com.example.trastnedviga.ui.Users;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.trastnedviga.Model.LoadingDialog;
import com.example.trastnedviga.R;
import com.example.trastnedviga.ui.Fragm.ProductsFragment;
import com.example.trastnedviga.ui.Fragm.RentFragment;
import com.example.trastnedviga.ui.LoginActiviy;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView user_profile_name;
    private AppBarConfiguration mAppBarConfiguration;
    private View containerView;
    private static final int ACTION_CALL = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);




        containerView = findViewById(R.id.app_bar_home).findViewById(R.id.secInclude).findViewById(R.id.container);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(containerView.getId(), new ProductsFragment()).commit();

        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Меню");
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);



        fab.setOnClickListener(new  View.OnClickListener() {
            public void onClick(View arg0) {
                Intent intent;
                intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+7 (914) 070-00-96"));
                startActivity(intent);

            }
        });




        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) drawerLayout.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        LoadingDialog loadingDialog = new LoadingDialog(HomeActivity.this);
        int id = item.getItemId();
        if (id == R.id.nav_home_arend) {
            LoadingDialog.startLoadingDialog();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LoadingDialog.dissmissDialog();
                }
            }, 3000);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(containerView.getId(), new RentFragment()).commit();
        } else if (id == R.id.nav_home_buy) {
            LoadingDialog.startLoadingDialog();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LoadingDialog.dissmissDialog();
                }
            }, 3000);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(containerView.getId(), new ProductsFragment()).commit();
        } else if (id == R.id.nav_home) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(containerView.getId(), new ProductsFragment()).commit();
        } else if (id == R.id.supp) {
            Paper.book().destroy();
            Intent loginIntent = new Intent(HomeActivity.this, ContactActivity.class);
            startActivity(loginIntent);
        } else if (id == R.id.rozgr) {
            Paper.book().destroy();
            String url = "https://docs.google.com/forms/d/12wL832ezba0eH-XKQIpI9BpvYSeGMwxsDmHjgQGvvcM/edit";
            Intent loginIntent = new Intent(android.content.Intent.ACTION_VIEW,Uri.parse(url));
            startActivity(loginIntent);

        /*} else if (id == R.id.settings) {
            Paper.book().destroy();
            Intent loginIntent = new Intent(HomeActivity.this, Profile.class);
            startActivity(loginIntent);*/
        } else if (id == R.id.nav_exit) {
            Paper.book().destroy();
            Intent loginIntent = new Intent(HomeActivity.this, LoginActiviy.class);
            startActivity(loginIntent);
        }
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }




}
