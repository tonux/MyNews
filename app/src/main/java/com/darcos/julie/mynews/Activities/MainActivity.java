package com.darcos.julie.mynews.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.darcos.julie.mynews.Views.PagerAdapter;
import com.darcos.julie.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    //declare the different view
    @BindView(R.id.activity_main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.activity_main_drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.activity_main_nav_view)
    NavigationView navigationView;
    @BindView(R.id.activity_main_viewPager)
    ViewPager viewPager;
    @BindView(R.id.activiy_main_tabLayout)
    TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        this.configureToolBar();

        this.configureDrawerLayout();

        this.configureNavigationView();

        this.configureViewPager();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_toolbar, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        // Handle back click to close menu
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    //toolbar button click(searsh, notification,help and about)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_search:
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                break;

            case R.id.action_notifications:
                Intent intentNotification = new Intent(MainActivity.this, NotificationsActivity.class);
                startActivity(intentNotification);
                break;

            case R.id.action_help:
                this.help();
                break;

            case R.id.action_about:
                this.about();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //navigation drawer
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.activity_main_search:
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                break;

            case R.id.activity_main_topStories:
                this.viewPager.setCurrentItem(0);
                break;
            case R.id.activity_main_most_popular:
                this.viewPager.setCurrentItem(1);
                break;
            case R.id.activity_main_sports:
                this.viewPager.setCurrentItem(2);
                break;
            case R.id.activity_main_notification:
                Intent intentNotification = new Intent(MainActivity.this, NotificationsActivity.class);
                startActivity(intentNotification);
                break;
            default:
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    // ---------------------
    // CONFIGURATION
    // ---------------------

    // Configure Toolbar
    private void configureToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My News");
    }

    //Configure Drawer Layout
    private void configureDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    //Configure NavigationView
    private void configureNavigationView() {
        navigationView.setNavigationItemSelectedListener(this);
    }

    //Configure ViewPager
    private void configureViewPager() {

        this.viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));

        tabs.setupWithViewPager(this.viewPager);
    }

    // when click on help open pop up
    private void help() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Help");
        builder.setMessage("If you have a problem contact julien.darcos@gmail.com.");
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // when clik on about open a new pop up
    private void about() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("About");
        builder.setMessage("This application was created as part of a project for OpenClassrooms");
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
