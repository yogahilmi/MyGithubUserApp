package com.tasanah.mygithubuserapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tasanah.mygithubuserapp.R;
import com.tasanah.mygithubuserapp.adapter.UserAdapter;
import com.tasanah.mygithubuserapp.model.User;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String[] dataName;
    private String[] dataUsername;
    private String[] dataPerusahaan;
    private String[] dataLokasi;
    private String[] dataRepo;
    private String[] dataFollowers;
    private String[] dataFollowing;
    private TypedArray dataPhoto;
    private UserAdapter adapter;
    private ArrayList<User> users;
    final String title = "Github Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setActionBarTitle();

        adapter = new UserAdapter(this);
        ListView listView = findViewById(R.id.lv_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_USER, users.get(i));
                startActivity(intent);
            }
        });
        prepare();
        addItem();
    }

    private void prepare() {
        dataName = getResources().getStringArray(R.array.name);
        dataUsername = getResources().getStringArray(R.array.username);
        dataPerusahaan = getResources().getStringArray(R.array.company);
        dataLokasi = getResources().getStringArray(R.array.location);
        dataRepo = getResources().getStringArray(R.array.repository_ar);
        dataFollowers = getResources().getStringArray(R.array.followers_ar);
        dataFollowing = getResources().getStringArray(R.array.following_ar);
        dataPhoto = getResources().obtainTypedArray(R.array.avatar);
    }

    private void addItem() {
        users = new ArrayList<>();

        for (int i = 0; i < dataName.length; i++) {
            User user = new User();
            user.setPhoto(dataPhoto.getResourceId(i, -1));
            user.setName(dataName[i]);
            user.setUsername(dataUsername[i]);
            user.setPerusahaan(dataPerusahaan[i]);
            user.setLokasi(dataLokasi[i]);
            user.setRepo(dataRepo[i]);
            user.setFollowers(dataFollowers[i]);
            user.setFollowing(dataFollowing[i]);
            users.add(user);
        }

        adapter.setUsers(users);
    }

    private void setActionBarTitle() {
        if (getSupportActionBar() !=null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Apakah anda ingin keluar?")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("Tidak", null)
                .show();
    }

}
