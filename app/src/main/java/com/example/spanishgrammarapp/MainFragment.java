package com.example.spanishgrammarapp;

import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

/**
 * Created by Pc on 2016-02-04.
 */
public class MainFragment extends Activity {
    // Custom button
    private static final String TAG_CANCEL = "FACEBOOK LOGIN";
    private Button fbbutton;
    private Button hombtn;
    public String str_email;
    public String str_id;
    public String str_firstname;
    public String str_lastname;
    public ContactsContract.Data data;
    public String email2;
    // Creating Facebook CallbackManager Value
    public CallbackManager callbackmanager;
    private TextView mTextDetails;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize SDK before setContentView(Layout ID)
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.fragment_layout);
        callbackmanager = CallbackManager.Factory.create();

        // Initialize layout button
        fbbutton = (Button) findViewById(R.id.login_button);

        fbbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LoginManager.getInstance().registerCallback(callbackmanager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        AccessToken accessToken = loginResult.getAccessToken();
                        Profile profile = Profile.getCurrentProfile();
                        if (profile != null) {
                            mTextDetails.setText("wellcome to the Team Paragon" + profile.getName());

                        }
                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException e) {
                    }
                });



            }
        });
    }
}