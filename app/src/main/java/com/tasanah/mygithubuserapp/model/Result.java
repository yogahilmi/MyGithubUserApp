package com.tasanah.mygithubuserapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {

    @SerializedName("items")
    @Expose
    private List<User> mResultMember;

    public Result(List<User> mResultMember) {
        this.mResultMember = mResultMember;
    }

    public List<User> getmResultMember() {
        return mResultMember;
    }

}

