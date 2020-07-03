package com.tasanah.mygithubuserapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.tasanah.mygithubuserapp.model.User;
import com.tasanah.mygithubuserapp.view.DetailActivity;
import com.tasanah.mygithubuserapp.R;
import java.util.List;

/**
 Created by Yoga Hilmi Tasanah
 27 June 2020
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private Context c;
    private List<User> userList ;

    public UserAdapter(Context c, List<User> userGithubList) {
        this.c = c;
        this.userList = userGithubList;
    }

    @NonNull
    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, int position) {
        User userGithub = userList.get(position);
        holder.tv_name.setText(userGithub.getLogin());
        holder.tv_url.setText(userGithub.getHtmlUrl());
        Glide.with(holder.itemView.getContext())
                .load(userGithub.getAvatarUrl())
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img;
        TextView tv_url, tv_name;
        UserViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imageList);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_url = itemView.findViewById(R.id.tv_url);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            User userGithub = userList.get(getAdapterPosition());
            Intent intent = new Intent(c, DetailActivity.class);
            intent.putExtra("DATA_USER",userGithub);
            v.getContext().startActivity(intent);
        }
    }
}
