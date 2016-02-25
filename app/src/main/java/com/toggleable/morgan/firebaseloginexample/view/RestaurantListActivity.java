package com.toggleable.morgan.firebaseloginexample.view;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.toggleable.morgan.firebaseloginexample.MyApp;
import com.toggleable.morgan.firebaseloginexample.R;
import com.toggleable.morgan.firebaseloginexample.adapter.RestaurantListAdapter;
import com.toggleable.morgan.firebaseloginexample.model.Restaurant;

import org.parceler.Parcels;

import java.util.ArrayList;

public class RestaurantListActivity extends AppCompatActivity
        implements RestaurantListAdapter.OnRestaurantClickedListener {
    private final static String SAVED_ADAPTER_ITEMS = "SAVED_ADAPTER_ITEMS";
    private final static String SAVED_ADAPTER_KEYS = "SAVED_ADAPTER_KEYS";
    private RestaurantListAdapter mAdapter;
    private ArrayList<Restaurant> mAdapterItems;
    private ArrayList<String> mAdapterKeys;
    private String mUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);


        /* Get user id from shared prefs, sqlite, or authData */
        AuthData authData = MyApp.getInstance().getFirebaseRef().getAuth();
        mUserId = authData.getUid();

        handleSavedInstanceState(savedInstanceState);

    }

    private void handleSavedInstanceState(Bundle savedInstanceState) {
        if(savedInstanceState != null &&
                savedInstanceState.containsKey(SAVED_ADAPTER_ITEMS) &&
                savedInstanceState.containsKey(SAVED_ADAPTER_KEYS)) {
            mAdapterItems = Parcels.unwrap(savedInstanceState.getParcelable(SAVED_ADAPTER_ITEMS));
            mAdapterKeys = Parcels.unwrap(savedInstanceState.getParcelable(SAVED_ADAPTER_KEYS));
        } else {
            mAdapterItems = new ArrayList<>();
            mAdapterKeys = new ArrayList<>();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVED_ADAPTER_ITEMS, Parcels.wrap(mAdapter.getItems()));
        outState.putStringArrayList(SAVED_ADAPTER_KEYS, mAdapter.getKeys());
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpRecyclerView();
    }

    @Override
    public void onPause() {
        super.onPause();
        mAdapter.destroy();
    }

    @Override
    public void onRestaurantClicked(Restaurant restaurant) {
        /* Do something with your restaurant model like show a detailed view */
        Toast.makeText(this, restaurant.getRestaurantName(), Toast.LENGTH_LONG).show();
    }

    private void setUpRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.activity_restaurant_list_recyclerView);
        mAdapter =
                new RestaurantListAdapter(this,
                        MyApp.getInstance().getFirebaseRef().child("userFavoriteRestaurants/" + mUserId),
                        Restaurant.class,
                        mAdapterItems,
                        mAdapterKeys);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(mAdapter);
    }
}
