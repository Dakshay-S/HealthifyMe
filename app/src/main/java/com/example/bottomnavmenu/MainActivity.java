package com.example.bottomnavmenu;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
/*
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;*/

//import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;

    private DrawerLayout drawer;

    private Context context;
    private NavigationView drawerNav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /* requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar*/


        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_main);

        // set toolbar as action bar
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        context = this;

        drawer = findViewById(R.id.drawer_layout);

        drawerNav = findViewById(R.id.DrawerNav_view);

        drawerNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.viewHospital:
                        if (!isConnectedToNetwork()) {
                            Toast.makeText(context, "No internet access. Turn on mobile data or Wifi", Toast.LENGTH_LONG).show();
                        }
                        else if (! isGPSon())
                        {
                            Toast.makeText(context, "Turn on GPS", Toast.LENGTH_LONG).show();
                        }
                            else {
                            Intent intent = new Intent(context, MapsActivity.class);
                            context.startActivity(intent);
                        }

                        break;

                    case R.id.stepsCounter:
                        Intent counterIntent = new Intent(context, StepsCounter.class);
                        context.startActivity(counterIntent);
                        break;


                    case R.id.abs:
                        Intent intent1 = new Intent(context, AbsExercise.class);
                        context.startActivity(intent1);
                        break;

                    case R.id.leg:
                        Intent intent2 = new Intent(context, LegExercise.class);
                        context.startActivity(intent2);
                        break;

                    case R.id.arm:
                        Intent intent3 = new Intent(context, ArmExercise.class);
                        context.startActivity(intent3);
                        break;
                }
                return false;
            }
        });


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadFragment(new HomeFragment());

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    loadFragment(new HomeFragment());
                    return true;

                case R.id.navigation_add:
                    loadFragment(new AddFragment());
                    return true;

                case R.id.navigation_view:
                    loadFragment(new ViewFragment());
                    return true;
            }

            return false;
        }
    };


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        //transaction.addToBackStack(null);
        transaction.commit();
    }


    public boolean isConnectedToNetwork() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;

    }

    public boolean isGPSon()
    {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE );
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }


}
