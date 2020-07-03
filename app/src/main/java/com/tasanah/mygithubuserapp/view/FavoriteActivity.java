package com.tasanah.mygithubuserapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tasanah.mygithubuserapp.R;
import com.tasanah.mygithubuserapp.adapter.FavoriteAdapter;
import com.tasanah.mygithubuserapp.database.UserHelper;
import com.tasanah.mygithubuserapp.model.User;

import java.util.ArrayList;

/**
 Created by Yoga Hilmi Tasanah
 29 June 2020
 */

public class FavoriteActivity extends AppCompatActivity {

    private UserHelper userHelper;
    private ArrayList<User> userList =  new ArrayList<>();
    private FavoriteAdapter favoriteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        userHelper = new UserHelper(getApplicationContext());
        userHelper.open();
        userList = userHelper.getDataUser();

        setRecyclerView();

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        ImageView imgBack = findViewById(R.id.favorite_back);
        imgBack.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), MainActivity.class)));
    }

    private void setRecyclerView(){
        RecyclerView rv = findViewById(R.id.rv_user);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);
        favoriteAdapter = new FavoriteAdapter(getApplicationContext());
        rv.setAdapter(favoriteAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        userList = userHelper.getDataUser();
        favoriteAdapter.setUserList(userList);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userHelper.close();
    }
}