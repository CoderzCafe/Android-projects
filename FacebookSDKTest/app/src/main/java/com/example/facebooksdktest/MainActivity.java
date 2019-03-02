package com.example.facebooksdktest;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private Button facebookButton;
    private LoginButton loginButton;

    private ImageView profilePic;
    private TextView email, birthday, friends;

    private CallbackManager callbackManager;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.setApplicationId("376911099812816");
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_main);
//        facebookButton = (Button) findViewById(R.id.continueWithFacebookButton);

        loginButton = (LoginButton) findViewById(R.id.continueWithFacebookButton);
        profilePic = (ImageView) findViewById(R.id.facebookProfilePictureImageView);
        email = (TextView) findViewById(R.id.facebookProfileEmailTextView);
        birthday = (TextView) findViewById(R.id.facebookProfileBirthdayTextView);
        friends = (TextView) findViewById(R.id.facebookProfileFriendsTextView);

        //  main
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Receiving data...");
                progressDialog.show();

                String accessToken = loginResult.getAccessToken().toString();

                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        progressDialog.dismiss();
                        Log.d("response", object.toString());
                        getFacebookData(object);
                    }
                });


                //  request grapht api
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, email, birthday, friends");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });


        //  if already login
        if (AccessToken.getCurrentAccessToken() != null) {
            email.setText(AccessToken.getCurrentAccessToken().getUserId());
        }

    }

    private void getFacebookData(JSONObject object) {
        try {
            URL profile_picture = new URL("https://graph.facebook.com/"+object.getString("id")+"/picture?width=250&height=250");

            Picasso.with(getApplicationContext()).load(profile_picture.toString()).into(profilePic);

            email.setText("Email : "+object.getString("email"));
            birthday.setText("Birthday : " +object.getString("birthday"));
            friends.setText("Friends : " +object.getJSONObject("friends").getJSONObject("summary").getString("total_count"));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void printHashKey() {

        try {
            @SuppressLint("PackageManagerGetSignatures") PackageInfo packageInfo = getPackageManager().getPackageInfo("com.example.facebooksdktest", PackageManager.GET_SIGNATURES);

            for (Signature signature: packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

}
