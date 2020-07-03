package com.tasanah.consumerapp.adapter;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tasanah.consumerapp.R;
import com.tasanah.consumerapp.model.User;

/**
 Created by Yoga Hilmi Tasanah
 29 June 2020
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Cursor cursor;

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    private User getItemData(int position){
        if (!cursor.moveToPosition(position)){
            throw new IllegalStateException("INVALID");
        }
        return new User(cursor);
    }
    @NonNull
    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, int position) {
        User userGithub = getItemData(position);
        holder.tv_name.setText(userGithub.getLogin());
        holder.tv_url.setText(userGithub.getHtmlUrl());
        Glide.with(holder.itemView.getContext())
                .load(userGithub.getAvatarUrl())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if (cursor == null) return 0;
        return cursor.getCount();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView tv_url,tv_name;
        UserViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageList);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_url = itemView.findViewById(R.id.tv_url);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), "Touched Favourite User ", Toast.LENGTH_SHORT).show();
        }
    }
}

