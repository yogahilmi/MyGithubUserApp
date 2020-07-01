package com.tasanah.mygithubuserapp.ui.favourite;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.tasanah.mygithubuserapp.R;
import com.tasanah.mygithubuserapp.adapter.FavouriteAdapter;
import com.tasanah.mygithubuserapp.model.User;
import com.tasanah.mygithubuserapp.ui.detail.DetailActivity;
import com.tasanah.mygithubuserapp.util.helper.Mapping;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.tasanah.mygithubuserapp.database.DbContract.UserColumns.CONTENT_URI;
import static com.tasanah.mygithubuserapp.ui.main.MainActivity.EXTRA_USER;

public class FavouriteActivity extends AppCompatActivity implements LoadUserCallback {

    private ProgressBar progressBar;
    private FavouriteAdapter adapter;

    private static final String EXTRA_STATE = "EXTRA_STATE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Favourite");
        }

//        progressBar = findViewById(R.id.favorite_progressBar);

        RecyclerView rvFavorites = findViewById(R.id.favorite_recyclerView);

        rvFavorites.setLayoutManager(new LinearLayoutManager(this));
        rvFavorites.setHasFixedSize(true);

        adapter = new FavouriteAdapter();
        rvFavorites.setAdapter(adapter);


        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());

        DataObserver myObserver = new DataObserver(handler, this);
        getContentResolver().registerContentObserver(CONTENT_URI, true, myObserver);

        if (savedInstanceState == null) {
            new LoadUserAsync(this, this).execute();
        } else {
            ArrayList<User> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setListUser(list);
                adapter.setOnItemClickCallback(data -> {
                    Intent goToDetailUser2 = new Intent(FavouriteActivity.this, DetailActivity.class);
                    goToDetailUser2.putExtra(EXTRA_USER, data);
                    FavouriteActivity.this.startActivity(goToDetailUser2);
                });
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListUser());
    }


    @Override
    public void preExecute() {
        runOnUiThread(() -> progressBar.setVisibility(View.VISIBLE));
    }

    @Override
    public void postExecute(ArrayList<User> userLocal) {
        progressBar.setVisibility(View.INVISIBLE);
        if (userLocal.size() > 0) {
            adapter.setListUser(userLocal);
            adapter.setOnItemClickCallback(data -> {
                Intent goToDetailUser = new Intent(FavouriteActivity.this, DetailActivity.class);
                goToDetailUser.putExtra(EXTRA_USER, data);
                FavouriteActivity.this.startActivity(goToDetailUser);
            });
        } else {
            adapter.setListUser(new ArrayList<>());
            Toast.makeText(this, "Tidak Ada Data Saat Ini", Toast.LENGTH_SHORT).show();
        }
    }


    private static class LoadUserAsync extends AsyncTask<Void, Void, ArrayList<User>> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadUserCallback> weakCallback;

        LoadUserAsync(Context context, LoadUserCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<User> doInBackground(Void... voids) {
            Context context = weakContext.get();
            Cursor dataCursor = context.getContentResolver().query(CONTENT_URI, null, null, null, null);
            assert dataCursor != null;
            return Mapping.mapCursorToArrayList(dataCursor);
        }

        @Override
        protected void onPostExecute(ArrayList<User> userLocals) {
            super.onPostExecute(userLocals);
            weakCallback.get().postExecute(userLocals);
        }
    }

    public static class DataObserver extends ContentObserver {
        final Context context;

        DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new LoadUserAsync(context, (LoadUserCallback) context).execute();
        }
    }
}

interface LoadUserCallback {
    void preExecute();
    void postExecute(ArrayList<User> userLocal);
}