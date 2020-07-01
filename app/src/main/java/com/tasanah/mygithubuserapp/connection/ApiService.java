package com.tasanah.mygithubuserapp.connection;

import com.tasanah.mygithubuserapp.model.Result;
import com.tasanah.mygithubuserapp.model.response.Search;
import com.tasanah.mygithubuserapp.model.User;
import com.tasanah.mygithubuserapp.model.UserDetail;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 Created by Yoga Hilmi Tasanah
 27 June 2020
 */

public interface ApiService {

    @GET("/search/users")
    Call<Result> getListUser(@Query("q") String login);

    @GET("/users/{username}/followers")
    @Headers("Authorization: token 2091dae5321b573c3cfba68ad5a8637fa0720c94")
    Call<List<User>> getUserFollowers(@Path("username") String username);

    @GET("/users/{username}/following")
    @Headers("Authorization: token 2091dae5321b573c3cfba68ad5a8637fa0720c94")
    Call<List<User>> getUserFollowing(@Path("username") String username);


    @GET("/search/users")
    @Headers("Authorization: token 2091dae5321b573c3cfba68ad5a8637fa0720c94")
    Call<Search> getGithubSearch(
            @Query("q") String username
    );

    @GET("users/{username}")
    @Headers("Authorization: token 2091dae5321b573c3cfba68ad5a8637fa0720c94")
    Call<User> getUserDetail(@Path("username") String username);
}
