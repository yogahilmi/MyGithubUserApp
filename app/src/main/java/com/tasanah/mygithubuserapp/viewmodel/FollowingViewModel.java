package com.tasanah.mygithubuserapp.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tasanah.mygithubuserapp.connection.ApiService;
import com.tasanah.mygithubuserapp.connection.RetrofitConfig;
import com.tasanah.mygithubuserapp.model.User;

import java.util.List;
import java.util.Objects;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowingViewModel extends ViewModel{

    private MutableLiveData<List<User>> listFollowing = new MutableLiveData<>();
    private Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public MutableLiveData<List<User>> getListFollowing() {
        return listFollowing;
    }

    public void setListFollowing(String username) {
        ApiService apiService;
        retrofit2.Call<List<User>> Call;

        try {
            apiService = RetrofitConfig.getRetrofit().create(ApiService.class);
            Call = apiService.getUserFollowing(username);
            Call.enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(@NonNull retrofit2.Call<List<User>> call, @NonNull Response<List<User>> response) {

                    Log.d("Response", "" + " " + response.body());
                    List<User> listUser;
                    listUser = response.body();
                    listFollowing.postValue(listUser);
                }

                @Override
                public void onFailure(@NonNull retrofit2.Call<List<User>> call, @NonNull Throwable t) {
                    Log.d("Message", Objects.requireNonNull(t.getMessage()));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
