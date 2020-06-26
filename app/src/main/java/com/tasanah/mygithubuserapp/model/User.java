package com.tasanah.mygithubuserapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable{

    private int photo;
    private String name;
    private String perusahaan;
    private String repo;
    private String username;
    private String lokasi;
    private String followers;
    private String following;


    protected User(Parcel in) {
        photo = in.readInt();
        name = in.readString();
        perusahaan = in.readString();
        repo = in.readString();
        username = in.readString();
        lokasi = in.readString();
        followers = in.readString();
        following = in.readString();
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

    public User() {

    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPerusahaan() {
        return perusahaan;
    }

    public void setPerusahaan(String perusahaan) {
        this.perusahaan = perusahaan;
    }

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(photo);
        parcel.writeString(name);
        parcel.writeString(perusahaan);
        parcel.writeString(repo);
        parcel.writeString(username);
        parcel.writeString(lokasi);
        parcel.writeString(followers);
        parcel.writeString(following);
    }
}
