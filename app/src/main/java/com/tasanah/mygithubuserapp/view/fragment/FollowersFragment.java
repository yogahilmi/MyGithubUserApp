package com.tasanah.mygithubuserapp.view.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.tasanah.mygithubuserapp.adapter.UserAdapter;
import com.tasanah.mygithubuserapp.connection.ApiService;
import com.tasanah.mygithubuserapp.connection.RetrofitConfig;
import com.tasanah.mygithubuserapp.model.User;
import com.tasanah.mygithubuserapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 Created by Yoga Hilmi Tasanah
 27 June 2020
 */

public class FollowersFragment extends Fragment {

    private RecyclerView recyclerView;
    private ShimmerFrameLayout shimmerFrameLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_followers, container, false);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        setDataFollowers(view);
    }

    private void setDataFollowers(View view){
        recyclerView = view.findViewById(R.id.rv_user);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        User user = Objects.requireNonNull(getActivity()).getIntent().getParcelableExtra("DATA_USER");
        ApiService apiService = RetrofitConfig.getRetrofit().create(ApiService.class);
        Call<List<User>> call = apiService.getUserFollowers(Objects.requireNonNull(user).getLogin());
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NotNull Call<List<User>> call, @NotNull Response<List<User>> response) {
                UserAdapter adapterUser = new UserAdapter(getContext(),response.body());
                recyclerView.setAdapter(adapterUser);
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(@NotNull Call<List<User>> call, @NotNull Throwable t) {

            }
        });
    }
}