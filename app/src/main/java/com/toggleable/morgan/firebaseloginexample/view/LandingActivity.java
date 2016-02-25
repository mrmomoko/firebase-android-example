package com.toggleable.morgan.firebaseloginexample.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.AuthData;
import com.toggleable.morgan.firebaseloginexample.MyApp;
import com.toggleable.morgan.firebaseloginexample.R;

import java.util.HashMap;

public class LandingActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mRestaurantName;
    private Button mSubmitButton;
    private Button mSeeYourRestaurants;
    private String mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);


        mRestaurantName = (EditText) findViewById(R.id.activity_landing_restaurantName);
        mSubmitButton = (Button) findViewById(R.id.activity_landing_submitButton);
        mSubmitButton.setOnClickListener(this);
        mSeeYourRestaurants = (Button) findViewById(R.id.activity_landing_restaurantListButton);
        mSeeYourRestaurants.setOnClickListener(this);

        /* Get user id from shared prefs, sqlite, or authdata */
        AuthData authData = MyApp.getInstance().getFirebaseRef().getAuth();
        mUserId = authData.getUid();
    }

    @Override
    public void onClick(View v) {
        if(v == mSubmitButton) {
            String name = mRestaurantName.getText().toString();
            saveRestaurant(mUserId, name);
        } else if(v == mSeeYourRestaurants) {
            goToRestaurantsList();
        }
    }

    private void goToRestaurantsList() {
        Intent intent = new Intent(this, RestaurantListActivity.class);
        startActivity(intent);
    }

    private void clearEditTexts() {
        mRestaurantName.setText("");
    }

    public void saveRestaurant(String userId, String name) {
        /* I am creating a hashmap here, but ideally you will have
        * a Restaurant Model object with all the information such as address
        * etc. Then you can just call .setValue(Restaurant Model Object)*/
        HashMap<String, Object> newRestaurant = new HashMap<>();
        newRestaurant.put("restaurantName", name);

        MyApp.getInstance().getFirebaseRef()
                .child("userFavoriteRestaurants/"
                + userId
                + "/"
                + name)
                .setValue(newRestaurant);

        clearEditTexts();
    }
}
