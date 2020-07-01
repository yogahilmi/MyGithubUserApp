package com.tasanah.mygithubuserapp.adapter;

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

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.UserViewHolder>{

    private List<User> userList ;

    public SearchAdapter(List<User> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public SearchAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.tv_name.setText(user.getLogin());
        holder.tv_url.setText(user.getHtmlUrl());
        Glide.with(holder.itemView.getContext())
                .load(user.getAvatarUrl())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView tv_url,tv_name;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageList);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_url = itemView.findViewById(R.id.tv_url);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            User user = userList.get(getAdapterPosition());
            Intent intent = new Intent(v.getContext(), DetailActivity.class);
            intent.putExtra("DATA_USER",user);
            v.getContext().startActivity(intent);
        }
    }
}
