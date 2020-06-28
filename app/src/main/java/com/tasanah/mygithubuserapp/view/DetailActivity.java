package com.tasanah.mygithubuserapp.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.tabs.TabLayout;
import com.tasanah.mygithubuserapp.R;
import com.tasanah.mygithubuserapp.adapter.*;
import com.tasanah.mygithubuserapp.connection.*;
import com.tasanah.mygithubuserapp.model.*;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 Created by Yoga Hilmi Tasanah
 27 June 2020
 */

public class DetailActivity extends AppCompatActivity {

    private TextView fullname,repo,follower,following,username,location,company;
    private ImageView imageView;
    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ViewPagerAdapter adapter = new ViewPagerAdapter(this,getSupportFragmentManager());
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.vp);
        shimmerFrameLayout = findViewById(R.id.shimmer_layout);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setElevation(0);
        setDataDetailUser();

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("Profile");
        }
    }

    private void setDataDetailUser(){
        User user = getIntent().getParcelableExtra("DATA_USER");
        if (user != null){
            repo = findViewById(R.id.tv_repo);
            follower = findViewById(R.id.tv_followers);
            following = findViewById(R.id.tv_following);
            fullname = findViewById(R.id.tv_fullname);
            username = findViewById(R.id.tv_username);
            company = findViewById(R.id.tv_company);
            location = findViewById(R.id.tv_location);
            imageView = findViewById(R.id.imageView);

            final LinearLayout linearLayout = findViewById(R.id.linear_layout);
            ApiService apiService = RetrofitConfig.getRetrofit().create(ApiService.class);
            Call<UserDetail> call = apiService.getUserDetail(user.getLogin());
            call.enqueue(new Callback<UserDetail>() {
                @Override
                public void onResponse(@NotNull Call<UserDetail> call, @NotNull Response<UserDetail> response) {
                    if (response.body() != null){
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        repo.setText(String.valueOf(response.body().getPublicRepos()));
                        follower.setText(String.valueOf(response.body().getFollowers()));
                        following.setText(String.valueOf(response.body().getFollowing()));
                        fullname.setText(response.body().getName());
                        username.setText(response.body().getLogin());
                        company.setText(response.body().getCompany());
                        location.setText(response.body().getLocation());
                        Glide.with(getApplicationContext())
                                .load(response.body().getAvatarUrl())
                                .into(imageView);
                        fullname.setVisibility(View.VISIBLE);
                        username.setVisibility(View.VISIBLE);
                        company.setVisibility(View.VISIBLE);
                        location.setVisibility(View.VISIBLE);
                        imageView.setVisibility(View.VISIBLE);
                        linearLayout.setVisibility(View.VISIBLE);

                    }
                }

                @Override
                public void onFailure(@NotNull Call<UserDetail> call, @NotNull Throwable t) {
                    new AlertDialog.Builder(DetailActivity.this)
                            .setTitle(getString(R.string.error))
                            .setMessage(getString(R.string.try_again))
                            .setCancelable(false)
                            .setNegativeButton("OK", null)
                            .show();
                }
            });
        }
    }
}
