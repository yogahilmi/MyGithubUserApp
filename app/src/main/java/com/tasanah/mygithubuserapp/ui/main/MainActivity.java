package com.tasanah.mygithubuserapp.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.tasanah.mygithubuserapp.R;
import com.tasanah.mygithubuserapp.adapter.*;
import com.tasanah.mygithubuserapp.ui.detail.DetailActivity;
import com.tasanah.mygithubuserapp.ui.favourite.FavouriteActivity;
import com.tasanah.mygithubuserapp.ui.setting.SettingActivity;
import com.tasanah.mygithubuserapp.viewmodel.UserViewModel;

import java.util.Objects;

/**
 Created by Yoga Hilmi Tasanah
 27 June 2020
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText edtUserSearch;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private UserViewModel userViewModel;
    private UserAdapter adapter;

    public static final String EXTRA_USER = "extra_user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String titleSearch = getString(R.string.titleSearch);
        Objects.requireNonNull(getSupportActionBar()).setTitle(titleSearch);

        userViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(UserViewModel.class);

        edtUserSearch = findViewById(R.id.edtSearchUser);
        progressBar = findViewById(R.id.progressBar);
        ImageButton btnSearch = findViewById(R.id.btnSearchUser);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        showLoading(false);
        btnSearch.setOnClickListener(this);

        userViewModel.getListUsers().observe(this, list -> {
            adapter = new UserAdapter();
            adapter.setListUsers(list);
            recyclerView.setAdapter(adapter);
            showLoading(false);

            adapter.setOnItemClickCallback(data -> {
                MainActivity.this.showLoading(true);
                Intent goToDetailUser = new Intent(MainActivity.this, DetailActivity.class);
                goToDetailUser.putExtra(EXTRA_USER, data);
                MainActivity.this.startActivity(goToDetailUser);
            });
        });
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        showLoading(false);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSearchUser) {
            showLoading(true);
            if (edtUserSearch.getText().toString().isEmpty()) {
                edtUserSearch.setError("Username Required");
                edtUserSearch.setFocusable(true);
                showLoading(false);
            } else {
                String username = edtUserSearch.getText().toString();
                userViewModel.setListUsers(username, getApplicationContext());
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.setting) {
            Intent goToSetting = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(goToSetting);
        }

        if (item.getItemId() == R.id.favorite) {
            Intent favoriteIntent = new Intent(MainActivity.this, FavouriteActivity.class);
            startActivity(favoriteIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
