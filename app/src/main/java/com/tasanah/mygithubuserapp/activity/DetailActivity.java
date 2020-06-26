package com.tasanah.mygithubuserapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.tasanah.mygithubuserapp.R;
import com.tasanah.mygithubuserapp.model.User;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_USER = "extra_user";
    final String title = "Detail User";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setActionBarTitle(title);

        CircleImageView circleImageView = findViewById(R.id.profile_image);
        TextView nama = findViewById(R.id.tv_nama);
        TextView username = findViewById(R.id.tv_username);
        TextView company = findViewById(R.id.tv_company);
        TextView lokasi = findViewById(R.id.tv_lokasi);
        TextView repo = findViewById(R.id.tv_repo);
        TextView followers = findViewById(R.id.tv_followers);
        TextView following = findViewById(R.id.tv_following);

        User user = getIntent().getParcelableExtra(EXTRA_USER);
        Glide.with(this).load(user.getPhoto()).into(circleImageView);

        if (user != null){
            Glide.with(this).load(user.getPhoto()).into(circleImageView);
            nama.setText(user.getName());
            username.setText(user.getUsername());
            company.setText(getString(R.string.space, user.getPerusahaan()));
            lokasi.setText(getString(R.string.space, user.getLokasi()));
            repo.setText(getString(R.string.repository, user.getRepo()));
            followers.setText(getString(R.string.followers, user.getFollowers()));
            following.setText(getString(R.string.following, user.getFollowing()));
        }
    }

    private void setActionBarTitle(String title) {
        if (getSupportActionBar() !=null) {
            getSupportActionBar().setTitle(title);
        }
    }
}
