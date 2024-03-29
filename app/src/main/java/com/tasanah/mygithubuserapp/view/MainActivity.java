package com.tasanah.mygithubuserapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.tasanah.mygithubuserapp.R;
import com.tasanah.mygithubuserapp.adapter.SearchAdapter;
import com.tasanah.mygithubuserapp.connection.ApiService;
import com.tasanah.mygithubuserapp.connection.RetrofitConfig;
import com.tasanah.mygithubuserapp.model.response.Search;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 Created by Yoga Hilmi Tasanah
 27 June 2020
 */

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setActionBarTitle();
        recyclerView = findViewById(R.id.rv_user);
        SearchView searchView = findViewById(R.id.sv_user);
        shimmerFrameLayout = findViewById(R.id.shimmer_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        shimmerFrameLayout.startShimmer();

        ImageView imgSetting = findViewById(R.id.main_setting);
        imgSetting.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), SettingActivity.class)));

        ImageView imgFavorite = findViewById(R.id.main_favorite);
        imgFavorite.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), FavoriteActivity.class)));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            public boolean onQueryTextSubmit(String query) {
                getSearchUser(query);
                return false;
            }

            public boolean onQueryTextChange(String newText) {
                getSearchUser(newText);
                return false;
            }
        });
    }

    private void setActionBarTitle() {
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
    }

    private void getSearchUser(String username){
        LinearLayout linearLayout = findViewById(R.id.linear_search);
        ApiService apiService = RetrofitConfig.getRetrofit().create(ApiService.class);
        Call<Search> call = apiService.getGithubSearch(username);
        call.enqueue(new Callback<Search>() {
            @Override
            public void onResponse(@NotNull Call<Search> call, @NotNull Response<Search> response) {
                if (response.body() != null){
                    SearchAdapter searchAdapter = new SearchAdapter(response.body().getItems());
                    recyclerView.setAdapter(searchAdapter);
                    shimmerFrameLayout.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmer();
                    if (recyclerView.isShown()) {
                        linearLayout.setVisibility(View.GONE);
                    } else {
                        linearLayout.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.busy), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NotNull Call<Search> call, @NotNull Throwable t) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(getString(R.string.error))
                        .setMessage(getString(R.string.try_again))
                        .setCancelable(false)
                        .setNegativeButton("OK", null)
                        .show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.exit)
                .setCancelable(false)
                .setPositiveButton(R.string.confirmexit, (dialog, which) -> MainActivity.this.finish())
                .setNegativeButton(R.string.cancelexit, null)
                .show();
    }
}
