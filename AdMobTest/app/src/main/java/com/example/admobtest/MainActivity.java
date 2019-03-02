package com.example.admobtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    private static final String APP_ID = "ca-app-pub-1510406228550104~2157566697";
    private static final String APP_UNIT_ID = "ca-app-pub-1510406228550104/2405936563";

    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobileAds.initialize(MainActivity.this, APP_ID);
        setContentView(R.layout.activity_main);

        adView = findViewById(R.id.adViewTest);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

    }
}
