package com.tasanah.mygithubuserapp.model.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.tasanah.mygithubuserapp.model.User;

import org.jetbrains.annotations.NotNull;

/**
 Created by Yoga Hilmi Tasanah
 27 June 2020
 */

public class Search {
    @SerializedName("total_count")
    private int totalCount;

    @SerializedName("incomplete_results")
    private boolean incompleteResults;

    @SerializedName("items")
    private List<User> items;

    public Search(int totalCount, boolean incompleteResults, List<User> items) {
        this.totalCount = totalCount;
        this.incompleteResults = incompleteResults;
        this.items = items;
    }

    public List<User> getItems() {
        return items;
    }

    @NotNull
    @Override
    public String toString(){
        return
                "UserSearchGithub{" +
                        "total_count = '" + totalCount + '\'' +
                        ",incomplete_results = '" + incompleteResults + '\'' +
                        ",items = '" + items + '\'' +
                        "}";
    }
}
