package com.tourism.myapplication.api.model;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@Keep
public class GetModel implements Serializable {
    private String name;
    private String description;

    private String image;

    @SerializedName("long_description")
    private String longDescription;

    private List<String> images;


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public List<String> getImages() {
        return images;
    }
}
