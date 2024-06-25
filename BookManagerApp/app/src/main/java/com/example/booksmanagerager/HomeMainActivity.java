package com.example.booksmanagerager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.booksmanagerager.adapter.SanPhamAdapter;
import com.example.booksmanagerager.fragment.GetViewProductFragment;
import com.example.booksmanagerager.fragment.IntroductMySelfFragment;
import com.example.booksmanagerager.model.SanPham;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeMainActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_main);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigation_view);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fragment = new GetViewProductFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentLayout, fragment)
                .commit();


        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                HomeMainActivity.this,
                drawerLayout,
                toolbar,
                R.string.open,
                R.string.close);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return false;
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.product_manager) {
                    toolbar.setTitle("Trang chủ");
                    fragment = new GetViewProductFragment();
                } else if (item.getItemId() == R.id.introduct) {
                    toolbar.setTitle("Dấu trang");
                    fragment = new IntroductMySelfFragment();
                } else if (item.getItemId() == R.id.log_out) {
                    toolbar.setTitle("Dấu trang");
                    Intent intent = new Intent(HomeMainActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    toolbar.setTitle("Cài đặt");
                    //  fragment = new SettingFragment();
                }
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentLayout, fragment)
                        .commit();
                drawerLayout.close();
                return true;
            }
        });


    }


}