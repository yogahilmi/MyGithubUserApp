package com.tasanah.mygithubuserapp.connection;

import com.tasanah.mygithubuserapp.model.User;
import com.tasanah.mygithubuserapp.model.UserDetail;
import com.tasanah.mygithubuserapp.model.response.Search;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.tasanah.mygithubuserapp.BuildConfig.TOKEN;

/**
 Created by Yoga Hilmi Tasanah
 27 June 2020
 */

public interface ApiService {

    @GET("/users/{username}/followers")
    @Headers("Authorization: token " + TOKEN)
    Call<List<User>> getUserFollowers(@Path("username") String username);

    @GET("/users/{username}/following")
    @Headers("Authorization: token " + TOKEN)
    Call<List<User>> getUserFollowing(@Path("username") String username);


    @GET("/search/users")
    @Headers("Authorization: token " + TOKEN)
    Call<Search> getGithubSearch(
            @Query("q") String username
    );

    @GET("users/{username}")
    @Headers("Authorization: token " + TOKEN)
    Call<UserDetail> getUserDetail(@Path("username") String username);
}
