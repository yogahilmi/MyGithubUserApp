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
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tasanah.mygithubuserapp.model.User;
import com.tasanah.mygithubuserapp.ui.detail.DetailActivity;
import com.tasanah.mygithubuserapp.R;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 Created by Yoga Hilmi Tasanah
 27 June 2020
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    @SerializedName("items")
    @Expose
    private List<User> listUsers;
    private Context context;

    private OnItemClickCallback onItemClickCallback;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public UserAdapter() {
    }

    public void setListUsers(List<User> listUsers) {
        this.listUsers = listUsers;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }


    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_items, parent, false);
        return new UserViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.bind(listUsers.get(position));

    }

    @Override
    public int getItemCount() {
        return this.listUsers.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        CircleImageView ivUserAvatar;

        UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvUserName);
            ivUserAvatar = itemView.findViewById(R.id.ivUserAvatar);
        }

        void bind (final User user) {
            tvName.setText(user.getLogin());

            Glide.with(itemView.getContext())
                    .load(user.getAvatarUrl())
                    .apply(new RequestOptions().override(60, 60))
                    .into(ivUserAvatar);

            itemView.setOnClickListener(v -> onItemClickCallback.onItemClicked(listUsers.get(UserViewHolder.this.getAdapterPosition())));
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(User data);
    }
}

