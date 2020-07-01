package com.tasanah.mygithubuserapp.ui.detail;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.tabs.TabLayout;
import com.tasanah.mygithubuserapp.R;
import com.tasanah.mygithubuserapp.adapter.*;
import com.tasanah.mygithubuserapp.connection.*;
import com.tasanah.mygithubuserapp.model.*;
import com.tasanah.mygithubuserapp.viewmodel.UserViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tasanah.mygithubuserapp.database.DbContract.UserColumns.AVATAR_URL;
import static com.tasanah.mygithubuserapp.database.DbContract.UserColumns.CONTENT_URI;
import static com.tasanah.mygithubuserapp.database.DbContract.UserColumns.LOGIN;
import static com.tasanah.mygithubuserapp.database.DbContract.UserColumns.NAME;

/**
 Created by Yoga Hilmi Tasanah
 27 June 2020
 */

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvDetailUserName, tvBio, tvFollow;
    CircleImageView ivDetailUserAvatar;
    Button btnFavorite;

    ContentValues values = new ContentValues();

    public String login = "";
    public Integer id;

    private final static String addtofavorite = "Add To Favorite";
    private final static String removefromfavorite = "Remove From Favorite";

    public static final String EXTRA_USER = "extra_user";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvDetailUserName = findViewById(R.id.tvDetailUserName);
        tvBio = findViewById(R.id.tvDetailUserBio);
        tvFollow = findViewById(R.id.tvDetailUserFollowingFollowers);
        ivDetailUserAvatar = findViewById(R.id.ivDetailUserAvatar);
        btnFavorite = findViewById(R.id.btnFavorite);
        btnFavorite.setOnClickListener(this);

        String followers = getString(R.string.followers);
        String following = getString(R.string.following);


        User mUser = getIntent().getParcelableExtra(EXTRA_USER);
        if(mUser != null){
            login = mUser.getLogin();
            Glide.with(getApplicationContext())
                    .load(mUser.getAvatarUrl())
                    .apply(new RequestOptions().override(150, 150))
                    .into(ivDetailUserAvatar);

            UserViewModel userViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(UserViewModel.class);

            userViewModel.setmUserDataApi(mUser.getLogin());
            userViewModel.getmUserDataApi().observe(this, user -> {
                String folow_text = user.getFollowers() + " " + followers + " " + user.getFollowing() + " " + following;
                tvFollow.setText(folow_text);
                tvDetailUserName.setText(user.getName());
                if (user.getBio() != null){
                    tvBio.setText(user.getBio());
                }

                values.put(LOGIN, user.getLogin());
                values.put(NAME, user.getName());
                values.put(AVATAR_URL, user.getAvatarUrl());

            });

//            userViewModel.setUserLocal(getContentResolver());
            userViewModel.getUserLocal().observe(this, userLocals -> {
                boolean check = false;
                for (int i = 0; i < userLocals.size(); i++) {
                    if (userLocals.get(i).getLogin().equals(login))
                    {
                        check = true;
                        id = userLocals.get(i).getId();
                    }
                }
                if (check) {
                    btnFavorite.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    btnFavorite.setText(removefromfavorite);
                    Toast.makeText(DetailActivity.this, btnFavorite.getText(), Toast.LENGTH_SHORT).show();
                } else {
                    btnFavorite.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    btnFavorite.setText(addtofavorite);
                }
            });
        }

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, getSupportFragmentManager(), mUser);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(viewPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        String titleDetail = getString(R.string.titleDetail);
        Objects.requireNonNull(getSupportActionBar()).setTitle(titleDetail);

        btnFavorite.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnFavorite) {
            if (btnFavorite.getText() == addtofavorite){
                //content uri untuk insert
                getContentResolver().insert(CONTENT_URI, values);
                addFavorite();
                Toast.makeText(this, getResources().getString(R.string.success_add_to_favorite), Toast.LENGTH_SHORT).show();

            } else if (btnFavorite.getText() == removefromfavorite) {
                //uriwithusername untuk delete
                Uri uriWithId = Uri.parse(CONTENT_URI + "/" + id);
                getContentResolver().delete(uriWithId, null, null );
                removeFavorite();
                Toast.makeText(this, getResources().getString(R.string.success_remove_from_favorite), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void addFavorite(){
        btnFavorite.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        btnFavorite.setText(getResources().getString(R.string.removefromfavorite));
    }

    public void removeFavorite() {
        btnFavorite.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        btnFavorite.setText(getResources().getString(R.string.addtofavorite));
    }
}
