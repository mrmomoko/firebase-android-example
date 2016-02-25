package com.toggleable.morgan.firebaseloginexample.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.toggleable.morgan.firebaseloginexample.R;
import com.toggleable.morgan.firebaseloginexample.model.Restaurant;

public class RestaurantListViewHolder extends RecyclerView.ViewHolder{
    private TextView mRestaurantName;
    private LinearLayout mParentLayout;

    public RestaurantListViewHolder(View itemView) {
        super(itemView);
        mRestaurantName = (TextView) itemView.findViewById(R.id.list_item_restaurant_name);
        mParentLayout = (LinearLayout) itemView.findViewById(R.id.list_item_restaurant_parentLayout);

    }

    public void populateRestaurants(Restaurant restaurant) {
        itemView.setTag(restaurant);
        mRestaurantName.setText(restaurant.getRestaurantName());
    }

    public LinearLayout getBackgroundView() {
        return mParentLayout;
    }
}
