package com.tasanah.mygithubuserapp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.tasanah.mygithubuserapp.adapter.UserAdapter;
import com.tasanah.mygithubuserapp.connection.ApiService;
import com.tasanah.mygithubuserapp.connection.RetrofitConfig;
import com.tasanah.mygithubuserapp.model.User;
import com.tasanah.mygithubuserapp.R;
import com.tasanah.mygithubuserapp.ui.detail.DetailActivity;
import com.tasanah.mygithubuserapp.viewmodel.FollowersViewModel;

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

    private RecyclerView rvFollowers;
    private ProgressBar progressBar;
    private UserAdapter userAdapter;

    public static final String EXTRA_FOLLOWERS = "extra_followers";

    public FollowersFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_followers, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressBarDetailFollowers);
        rvFollowers = view.findViewById(R.id.rvFollowers);
        rvFollowers.setLayoutManager(new LinearLayoutManager(getContext()));

        assert getArguments() != null;
//        String username = getArguments().getString(EXTRA_FOLLOWERS);

        showLoading(false);

        FollowersViewModel followersViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel.class);

//        followersViewModel.setListFollowers(username);

        followersViewModel.getListFollower().observe(getViewLifecycleOwner(), list -> {
            userAdapter = new UserAdapter();
            userAdapter.notifyDataSetChanged();
            userAdapter.setListUsers(list);

            rvFollowers.setAdapter(userAdapter);
            showLoading(false);

            userAdapter.setOnItemClickCallback((User data) -> {
                showLoading(true);
                Intent goToDetailUser = new Intent(getContext(), DetailActivity.class);
                goToDetailUser.putExtra(DetailActivity.EXTRA_USER, data);
                startActivity(goToDetailUser);
            });
        });


    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        showLoading(false);
    }
}