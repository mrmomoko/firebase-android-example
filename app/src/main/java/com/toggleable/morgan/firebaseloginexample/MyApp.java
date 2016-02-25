package com.toggleable.morgan.firebaseloginexample;


import android.app.Application;

import com.firebase.client.Firebase;

public class MyApp extends Application {
    private static MyApp application;
    private Firebase mFirebaseRef;

    public static MyApp getInstance() {
        return application;
    }

    public Firebase getFirebaseRef() {
        return mFirebaseRef;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        Firebase.setAndroidContext(this);
        mFirebaseRef = new Firebase("https://flickering-fire-7492.firebaseio.com/");
    }
}
