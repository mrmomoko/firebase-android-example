package com.toggleable.morgan.firebaseloginexample.model;


public class Restaurant {
    private String restaurantName;

    public Restaurant(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Restaurant() {
        //required empty public constructor
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
}
