package com.tasanah.consumerapp.view;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.tasanah.consumerapp.R;
import com.tasanah.consumerapp.adapter.UserAdapter;
import static com.tasanah.consumerapp.database.DbContract.UserColumns.CONTENT_URI;

/**
 Created by Yoga Hilmi Tasanah
 29 June 2020
 */

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (savedInstanceState == null){
            new Favorite().execute();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class Favorite extends AsyncTask<Void,Void, Cursor> {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Cursor doInBackground(Void... voids) {
            return getApplicationContext().getContentResolver().query(CONTENT_URI,null,null,null,null);
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(Cursor cursor) {

            super.onPostExecute(cursor);
            UserAdapter userAdapter = new UserAdapter();
            userAdapter.setCursor(cursor);
            userAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(userAdapter);
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.quit)
                .setMessage(R.string.exit)
                .setCancelable(false)
                .setPositiveButton(R.string.confirmexit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton(R.string.cancelexit, null)
                .show();
    }
}
