package com.tourism.myapplication.api.model;

import androidx.annotation.Keep;

import java.io.Serializable;

@Keep
public class HotelModel implements Serializable {
    private String name;
    private String rating;
    private String location;
    private String image;
    private String price;

    public String getName() {
        return name;
    }

    public String getRating() {
        return rating;
    }

    public String getLocation() {
        return location;
    }

    public String getImage() {
        return image;
    }

    public String getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Name :- " + name + "\n" +
                "Rating :- " + rating + "\n" +
                "Location :- " + location + "\n" +
                "Price :- " + price + "\n";
    }
}
