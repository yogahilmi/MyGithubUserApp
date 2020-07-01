package com.tasanah.mygithubuserapp.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.tasanah.mygithubuserapp.database.DbContract;
import static com.tasanah.mygithubuserapp.database.DbContract.getColumnString;

/**
 Created by Yoga Hilmi Tasanah
 27 June 2020
 */

public class UserDetail implements Parcelable {

    @SerializedName("login")
    private String login;

    @SerializedName("company")
    private String company;

    @SerializedName("id")
    private int id;

    @SerializedName("public_repos")
    private int publicRepos;

    @SerializedName("followers")
    private int followers;

    @SerializedName("avatar_url")
    private String avatarUrl;

    @SerializedName("following")
    private int following;

    @SerializedName("name")
    private String name;

    @SerializedName("location")
    private String location;

    public UserDetail(Cursor cursor) {
        this.avatarUrl = getColumnString(cursor, DbContract.UserColumns.AVATAR);
        this.login = getColumnString(cursor, DbContract.UserColumns.USERNAME);
    }

    protected UserDetail(Parcel in) {
        login = in.readString();
        company = in.readString();
        id = in.readInt();
        publicRepos = in.readInt();
        followers = in.readInt();
        avatarUrl = in.readString();
        following = in.readInt();
        name = in.readString();
        location = in.readString();
    }

    public static final Creator<UserDetail> CREATOR = new Creator<UserDetail>() {
        @Override
        public UserDetail createFromParcel(Parcel in) {
            return new UserDetail(in);
        }

        @Override
        public UserDetail[] newArray(int size) {
            return new UserDetail[size];
        }
    };

    public String getLogin() {
        return login;
    }

    public String getCompany() {
        return company;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPublicRepos() {
        return publicRepos;
    }

    public int getFollowers() {
        return followers;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public int getFollowing() {
        return following;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(login);
        parcel.writeString(company);
        parcel.writeInt(id);
        parcel.writeInt(publicRepos);
        parcel.writeString(avatarUrl);
        parcel.writeInt(followers);
        parcel.writeInt(following);
        parcel.writeString(location);
        parcel.writeString(name);
    }
}
