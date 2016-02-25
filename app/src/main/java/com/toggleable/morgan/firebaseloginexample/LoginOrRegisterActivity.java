package com.toggleable.morgan.firebaseloginexample;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

public class LoginOrRegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button mLoginSubmit;
    private Button mRegisterSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*Find app hashcode for facebook :

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
            //replace next line with correct package name if it has changed
                    "com.pick6fan.checkerspick6.development",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Timber.i("KeyHash: %s", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Timber.e("name not found exception");
            e.printStackTrace();

        } catch (NoSuchAlgorithmException ex) {
            Timber.e(ex, "NoSuchAlgorithmException");

        }
        */

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_signup);

        mEmailEditText = (EditText) findViewById(R.id.activity_login_or_signup_email);
        mPasswordEditText = (EditText) findViewById(R.id.activity_login_or_signup_password);
        mLoginSubmit = (Button) findViewById(R.id.activity_login_or_signup_loginButton);
        mLoginSubmit.setOnClickListener(this);
        mRegisterSubmit = (Button) findViewById(R.id.activity_login_or_signup_registerButton);
        mRegisterSubmit.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v == mLoginSubmit) {
            loginUser();
        } else if(v == mRegisterSubmit) {
            registerUser();
        }
    }

    private void clearEditTexts() {
        mEmailEditText.setText("");
        mPasswordEditText.setText("");
    }

    private void registerUser() {
        final String email = mEmailEditText.getText().toString();
        final String password = mPasswordEditText.getText().toString();

        MyApp.getInstance().getFirebaseRef()
                .createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
                    @Override
                    public void onSuccess(Map<String, Object> stringObjectMap) {
                        /* Firebase does not automatically log the user in after they register, so
                        * you have to do it here in the onSuccess method */
                        MyApp.getInstance().getFirebaseRef()
                                .authWithPassword(email, password, new Firebase.AuthResultHandler() {
                                    @Override
                                    public void onAuthenticated(AuthData authData) {
                                        String token = authData.getToken();
                                        String provider = authData.getProvider();
                                        String uid = authData.getUid();

                                        initializeUser(uid, email, provider, token);
                                        saveInformationInSharedPrefs(uid, email);
                                        goToLandingPage();

                                    }

                                    @Override
                                    public void onAuthenticationError(FirebaseError firebaseError) {
                                        Toast.makeText(MyApp.getInstance(), firebaseError.getMessage(), Toast.LENGTH_LONG).show();

                                    }
                                });

                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        Toast.makeText(MyApp.getInstance(), firebaseError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void saveInformationInSharedPrefs(String userId, String email) {
        /* Save whatever info you grab from users here */
    }

    private void initializeUser(String uid,
                                String email,
                                String provider,
                                String token) {
        /* save user in users table */
        Map<String, Object> createUser = new HashMap<>();
        createUser.put("email", email);
        createUser.put("provider", provider);
        createUser.put("token", token);
        createUser.put("uid", uid);
        MyApp.getInstance()
                .getFirebaseRef()
                .child("users/" + uid)
                .updateChildren(createUser);
    }

    private void goToLandingPage() {
        Intent intent = new Intent(this, LandingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    private void loginUser() {
        final String email = mEmailEditText.getText().toString();
        final String password = mPasswordEditText.getText().toString();

        MyApp.getInstance().getFirebaseRef()
                .authWithPassword(email, password, new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        String userId = authData.getUid();
                        saveInformationInSharedPrefs(userId, email);
                        goToLandingPage();
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        Toast.makeText(MyApp.getInstance(), firebaseError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }




}
