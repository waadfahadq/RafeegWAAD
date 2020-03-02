package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.myapplication.ui.home.FavoriteFragment;
import com.example.myapplication.ui.home.StoreListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StorwsList extends AppCompatActivity {

    private String title=null;

    private BottomNavigationView bottomNavigationView;
    private FavoriteFragment favoriteFragment;
    private StoreListFragment storeListFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storelist);
        Intent intent=getIntent();
        if(intent.hasExtra("title")){
            title=intent.getStringExtra("title");
            loadFragment(StoreListFragment.newInstance(title));
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        bottomNavigationView=findViewById(R.id.main_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id=menuItem.getItemId();
                if(id== R.id.action_places){
                    loadFragment(StoreListFragment.newInstance(title));

                }

                if(id== R.id.action_fav){
                    loadFragment(favoriteFragment);
                }

                return true;
            }
        });

    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
