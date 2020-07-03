package com.tasanah.consumerapp.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.tasanah.consumerapp.database.DbContract;
import static com.tasanah.consumerapp.database.DbContract.getFavoriteItem;

/**
 Created by Yoga Hilmi Tasanah
 29 June 2020
 */

public class User implements Parcelable{

    @SerializedName("login")
    private String login;

    @SerializedName("avatar_url")
    private String avatarUrl;

    @SerializedName("html_url")
    private String htmlUrl;

    @SerializedName("id")
    private int id;

    public String getLogin() {
        return login;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    protected User(Parcel in) {
        login = in.readString();
        avatarUrl = in.readString();
        htmlUrl = in.readString();
        id = in.readInt();
    }

    public User(Cursor cursor) {
        this.id = cursor.getInt(0);
        this.login = getFavoriteItem(cursor, DbContract.UserColumns.USERNAME);
        this.htmlUrl = getFavoriteItem(cursor, DbContract.UserColumns.URL);
        this.avatarUrl = getFavoriteItem(cursor, DbContract.UserColumns.AVATAR);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(login);
        dest.writeString(avatarUrl);
        dest.writeString(htmlUrl);
        dest.writeInt(id);
    }
}
