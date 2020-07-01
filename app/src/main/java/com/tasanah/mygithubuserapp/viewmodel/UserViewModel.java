package com.tasanah.mygithubuserapp.viewmodel;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tasanah.mygithubuserapp.R;
import com.tasanah.mygithubuserapp.connection.ApiService;
import com.tasanah.mygithubuserapp.connection.RetrofitConfig;
import com.tasanah.mygithubuserapp.model.Result;
import com.tasanah.mygithubuserapp.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import retrofit2.Callback;
import retrofit2.Response;

public class UserViewModel extends ViewModel{
    private MutableLiveData<List<User>> listUsers = new MutableLiveData<>();
    private MutableLiveData<List<User>> listUserDataLocal = new MutableLiveData<>();
    private MutableLiveData<User> mUserDataApi = new MutableLiveData<>();

    private User user;
    private Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public MutableLiveData<List<User>> getListUsers() {
        return listUsers;
    }

    public void setListUsers(String username, Context context) {
        ApiService apiService;
        retrofit2.Call<Result> Call;

        try {
            apiService = RetrofitConfig.getRetrofit().create(ApiService.class);
            Call = apiService.getListUser(username);
            Call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(@NonNull retrofit2.Call<Result> call, @NonNull Response<Result> response) {
                    Log.d("Response", "" + " " + response.body());
                    List<User> listUser;
                    assert response.body() != null;
                    listUser = response.body().getmResultMember();
                    listUsers.postValue(listUser);
                    if (listUser.isEmpty()) {
                        Toast.makeText(context, R.string.not_found, Toast.LENGTH_SHORT).show();
                    }

                }
                @Override
                public void onFailure(@NonNull retrofit2.Call<Result> call, @NonNull Throwable t) {
                    Log.d("Message", Objects.requireNonNull(t.getMessage()));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MutableLiveData<List<User>> getUserLocal() {
        return listUserDataLocal;

    }

//    public void setUserLocal(ContentResolver contentResolver) {
//        Cursor cursor = contentResolver.query(CONTENT_URI, null, null, null, null);
//        if (cursor != null) {
//            ArrayList<User> userLocal = MappingHelper.mapCursorToArrayList(cursor);
//            cursor.close();
//            listUserDataLocal.postValue(userLocal);
//        }
//    }

    public MutableLiveData<User> getmUserDataApi() {
        return mUserDataApi;
    }

    public void setmUserDataApi(String login) {
        ApiService apiService;
        retrofit2.Call<User> Call;
        try {
            apiService = RetrofitConfig.getRetrofit().create(ApiService.class);
            Call = apiService.getUserDetail(login);
            Call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(@NonNull retrofit2.Call<User> call, @NonNull Response<User> response) {
                    user = response.body();
                    mUserDataApi.postValue(user);
                }

                @Override
                public void onFailure(@NonNull retrofit2.Call<User> call, @NonNull Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
