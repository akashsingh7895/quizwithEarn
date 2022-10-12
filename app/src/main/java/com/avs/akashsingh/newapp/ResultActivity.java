package com.avs.akashsingh.newapp;

import static com.avs.akashsingh.newapp.MainActivity.InterID;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;


import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.avs.akashsingh.newapp.databinding.ActivityResultBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.unity3d.ads.UnityAds;

public class ResultActivity extends AppCompatActivity implements MaxAdListener {

    ActivityResultBinding binding;
    int POINTS = 10;

    //applovin ads
    private MaxInterstitialAd interstitialAd;
    private MaxNativeAdLoader nativeAdLoader;
    private MaxAd nativeAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        interstitialAd = new MaxInterstitialAd(getString(R.string.inter),this);
        interstitialAd.setListener(this);
        interstitialAd.loadAd();

        int correctAnswers = getIntent().getIntExtra("correct", 0);
        int totalQuestions = getIntent().getIntExtra("total", 0);

        long points = correctAnswers * POINTS;

        binding.answerset.setText(String.format("%d/%d", correctAnswers, totalQuestions));
        binding.coins.setText(String.valueOf(points));


        FirebaseFirestore database = FirebaseFirestore.getInstance();

        database.collection("USERS")
                .document(FirebaseAuth.getInstance().getUid())
                .update("coins", FieldValue.increment(points));

        binding.restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if (interstitialAd.isReady()){
                 interstitialAd.showAd();
                 startActivity(new Intent(ResultActivity.this, MainActivity.class));
                 finishAffinity();
             }else {
                 startActivity(new Intent(ResultActivity.this, MainActivity.class));
                 finishAffinity();
             }

            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interstitialAd.isReady()){
                    interstitialAd.showAd();
                    startActivity(new Intent(ResultActivity.this, MainActivity.class));
                    finishAffinity();
                }else {
                    startActivity(new Intent(ResultActivity.this, MainActivity.class));
                    finishAffinity();
                }
            }
        });


    }

    @Override
    public void onAdLoaded(MaxAd ad) {

    }

    @Override
    public void onAdDisplayed(MaxAd ad) {

    }

    @Override
    public void onAdHidden(MaxAd ad) {

    }

    @Override
    public void onAdClicked(MaxAd ad) {

    }

    @Override
    public void onAdLoadFailed(String adUnitId, MaxError error) {

    }

    @Override
    public void onAdDisplayFailed(MaxAd ad, MaxError error) {

    }
}