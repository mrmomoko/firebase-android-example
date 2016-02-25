package com.toggleable.morgan.firebaseloginexample.adapter;


import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.Query;
import com.toggleable.morgan.firebaseloginexample.R;
import com.toggleable.morgan.firebaseloginexample.model.Restaurant;
import com.toggleable.morgan.firebaseloginexample.util.FirebaseRecyclerAdapter;

import java.util.ArrayList;

public class RestaurantListAdapter extends FirebaseRecyclerAdapter<RestaurantListViewHolder, Restaurant>
    implements View.OnClickListener {
    private ArrayList<Restaurant> mRestaurants;
    private Restaurant mRestaurant;
    private final OnRestaurantClickedListener mListener;

    public RestaurantListAdapter(OnRestaurantClickedListener listener,
                                 Query query,
                                 Class<Restaurant> restaurantClass,
                                 @Nullable ArrayList<Restaurant> restaurants,
                                 @Nullable ArrayList<String> keys) {
        super(query, restaurantClass, null, null);
        this.mListener = listener;
    }

    @Override
    public RestaurantListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_restaurant, parent, false);

//        mContext = view.getContext(); - you have to pass context if you plan to load images thru picasso
        view.setOnClickListener(this);
        RestaurantListViewHolder holder = new RestaurantListViewHolder(view);
        holder.getBackgroundView().setOnClickListener(this);

        return holder;
    }

    @Override
    public void onBindViewHolder(RestaurantListViewHolder holder, int position) {
        mRestaurant = getItem(position);
        holder.getBackgroundView().setTag(mRestaurant);
        holder.populateRestaurants(mRestaurant);
    }

    @Override
    protected void itemAdded(Restaurant item, String key, int position) {

    }

    @Override
    protected void itemChanged(Restaurant oldItem, Restaurant newItem, String key, int position) {

    }

    @Override
    protected void itemRemoved(Restaurant item, String key, int position) {

    }

    @Override
    protected void itemMoved(Restaurant item, String key, int oldPosition, int newPosition) {

    }

    @Override
    public void onClick(View v) {
        if(v.getTag() instanceof Restaurant) {
            Restaurant restaurant = (Restaurant) v.getTag();
            mListener.onRestaurantClicked(restaurant);
        }
    }

    public interface OnRestaurantClickedListener {
        void onRestaurantClicked(Restaurant restaurant);
    }
}
