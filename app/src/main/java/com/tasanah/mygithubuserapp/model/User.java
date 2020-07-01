package com.tasanah.mygithubuserapp.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;

/**
 Created by Yoga Hilmi Tasanah
 27 June 2020
 */

public class User implements Parcelable {
    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;

    @SerializedName("followers")
    @Expose
    private Integer followers;

    @SerializedName("following")
    @Expose
    private Integer following;

    @SerializedName("bio")
    @Expose
    private String bio;

    @SerializedName("name")
    @Expose
    private String name;

    private Integer id;

    public User(String login, String name, String avatarUrl, Integer followers, Integer following, String bio) {
        this.login = login;
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.followers = followers;
        this.following = following;
        this.bio = bio;
    }

    public User() {
    }

    public User(Integer id, String login, String name, String avatarUrl) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.avatarUrl = avatarUrl;
    }

    public User(Parcel in) {
        id = in.readInt();
        login = in.readString();
        name = in.readString();
        avatarUrl = in.readString();
        bio = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getLogin() {
        return login;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public Integer getFollowers() {
        return followers;
    }

    public Integer getFollowing() {
        return following;
    }

    public String getBio() {
        return bio;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(login);
        dest.writeString(name);
        dest.writeString(avatarUrl);
        dest.writeString(bio);
    }
}
