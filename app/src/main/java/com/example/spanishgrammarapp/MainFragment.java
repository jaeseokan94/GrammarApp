package com.example.spanishgrammarapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Pc on 2016-02-04.
 */
public class MainFragment extends Activity {

    private static final String TAG_CANCEL = "FACEBOOK LOGIN";
    private Button fbbutton;
    private Button hombtn;
    public String str_email;
    public String str_id;
    public String str_firstname;
    public String str_lastname;
    public Date data;
    public String email2;
    // Creating Facebook CallbackManager Value
    public CallbackManager callbackmanager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize SDK before setContentView(Layout ID)
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.fragment_main);
        callbackmanager = CallbackManager.Factory.create();

        // Initialize layout button
        fbbutton = (Button) findViewById(R.id.login_button);

        fbbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LoginManager.getInstance().registerCallback(callbackmanager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object,
                                            GraphResponse response) {
                                        // Application code
                                        Log.v("LoginActivity", response.toString());
                                    }

                                });

                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender, birthday");
                        request.setParameters(parameters);
                        request.executeAsync();
                        Intent intent = new Intent(MainFragment.this, LanguageActivity.class);
                        startActivity(intent);
                        finish();

                    }

                    @Override
                    public void onCancel() {
                        // not called
                        Log.d("fb_login_sdk", "callback cancel");
                    }

                    @Override
                    public void onError(FacebookException e) {
                        // not called
                        Log.d("fb_login_sdk", "callback onError");
                    }
                });
            }
        });

        hombtn = (Button) findViewById(R.id.fbhome_btn);
        hombtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackmanager.onActivityResult(requestCode, resultCode, data);
    }
}