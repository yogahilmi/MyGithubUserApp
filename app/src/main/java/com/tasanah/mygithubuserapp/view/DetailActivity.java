package com.tasanah.mygithubuserapp.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.android.material.tabs.TabLayout;
import com.tasanah.mygithubuserapp.R;
import com.tasanah.mygithubuserapp.adapter.ViewPagerAdapter;
import com.tasanah.mygithubuserapp.connection.ApiService;
import com.tasanah.mygithubuserapp.connection.RetrofitConfig;
import com.tasanah.mygithubuserapp.database.DbContract;
import com.tasanah.mygithubuserapp.database.DbHelper;
import com.tasanah.mygithubuserapp.database.UserHelper;
import com.tasanah.mygithubuserapp.model.User;
import com.tasanah.mygithubuserapp.model.UserDetail;
import com.tasanah.mygithubuserapp.view.widget.ImageBannerWidget;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.tasanah.mygithubuserapp.database.DbContract.UserColumns.TABLE_USER_NAME;

/**
 Created by Yoga Hilmi Tasanah
 27 June 2020
 */

public class DetailActivity extends AppCompatActivity {

    private TextView fullname,repo,follower,following,username,location,company;
    private ImageView imageView;
    private User user;
    private ShimmerFrameLayout shimmerFrameLayout;
    private UserHelper userHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        init();
        setDataDetailUser();
        setOnClickFavoriteButton();
        userHelper = UserHelper.getInstance(getApplicationContext());

        ImageView imgBack = findViewById(R.id.detail_back);
        imgBack.setOnClickListener(v -> startActivity(new Intent(DetailActivity.this, MainActivity.class)));

        ImageView imgSetting = findViewById(R.id.detail_setting);
        imgSetting.setOnClickListener(v -> startActivity(new Intent(DetailActivity.this, SettingActivity.class)));

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
    }

    private void setOnClickFavoriteButton(){
        MaterialFavoriteButton materialFavoriteButton = findViewById(R.id.detail_floatingAction);
        if (EXIST(user.getLogin())){
            materialFavoriteButton.setFavorite(true);
            materialFavoriteButton.setOnFavoriteChangeListener((buttonView, favorite) -> {
                if (favorite){
                    userHelper.getDataUser();
                    userHelper.userInsert(user);
                    sendUpdateFavoriteList(this);
                    Toast.makeText(DetailActivity.this, getString(R.string.add_fav), Toast.LENGTH_SHORT).show();
                }else {
                    userHelper.getDataUser();
                    userHelper.userDelete(String.valueOf(user.getId()));
                    sendUpdateFavoriteList(this);
                    Toast.makeText(DetailActivity.this, getString(R.string.del_fav), Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            materialFavoriteButton.setOnFavoriteChangeListener((buttonView, favorite) -> {
                if (favorite){
                    userHelper.getDataUser();
                    userHelper.userInsert(user);
                    sendUpdateFavoriteList(this);
                    Toast.makeText(DetailActivity.this, getString(R.string.add_fav), Toast.LENGTH_SHORT).show();
                }else {
                    userHelper.getDataUser();
                    userHelper.userDelete(String.valueOf(user.getId()));
                    sendUpdateFavoriteList(this);
                    Toast.makeText(DetailActivity.this, getString(R.string.del_fav), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private boolean EXIST(String username){
        String change = DbContract.UserColumns.USERNAME + "=?";
        String[] changeArg = {username};
        String limit = "1";
        userHelper = new UserHelper(this);
        userHelper.open();
        user = getIntent().getParcelableExtra("DATA_USER");

        DbHelper userDbHelper = new DbHelper(getApplicationContext());
        SQLiteDatabase database = userDbHelper.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = database.query(TABLE_USER_NAME,null,change,changeArg,null,null,null,limit);
        boolean exist = (cursor.getCount() > 0 );
        cursor.close();
        return exist;
    }

    private void setDataDetailUser(){
        user = getIntent().getParcelableExtra("DATA_USER");
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
                    Toast.makeText(DetailActivity.this, "not load data", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void init() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(this, getSupportFragmentManager());
        shimmerFrameLayout = findViewById(R.id.shimmer_layout);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.vp);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);
        tabLayout.setElevation(0);

    }

    public static void sendUpdateFavoriteList(Context context)
    {
        Intent i = new Intent(context, ImageBannerWidget.class);
        i.setAction(ImageBannerWidget.UPDATE_WIDGET);
        context.sendBroadcast(i);
    }
}
