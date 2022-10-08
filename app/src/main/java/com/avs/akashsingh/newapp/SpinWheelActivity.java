package com.avs.akashsingh.newapp;

import static com.avs.akashsingh.newapp.MainActivity.InterID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.avs.akashsingh.newapp.databinding.ActivitySpinWheelBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.unity3d.ads.UnityAds;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class SpinWheelActivity extends AppCompatActivity implements MaxAdListener {
    ActivitySpinWheelBinding binding;
    Dialog dialog;
    //private static final int[]  sectors = {100,30,40,200,50,60,70,80,3,90,10,20,1,0,110};
    private static final int[]  sectors = {50,40,35,30,25,20,15,10,5,0};
    private static final int[] sectorsDegrees = new int[sectors.length];
    private static final Random random = new Random();

    private int degree = 0;
    private boolean isSpinning = false;

    int wonAmount;

    int spinCounter = 0;
    int spinTotal = 15;
    String todayDate,currentDate;

    int spinCounterPlus ;
    int spinTotalLeft ;


    //applovin ads
    private MaxInterstitialAd interstitialAd;
    private MaxNativeAdLoader nativeAdLoader;
    private MaxAd nativeAd;



    @SuppressLint({"ResourceType", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySpinWheelBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        interstitialAd = new MaxInterstitialAd(getString(R.string.inter),this);
        interstitialAd.setListener(this);
        interstitialAd.loadAd();
       // loadnetiveAd();
        //applovin

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        todayDate = df.format(Calendar.getInstance().getTime());
        dialog = new Dialog(this);
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        myEdit.putInt("spinCounter",spinCounter);
        myEdit.putInt("spinTotal",spinTotal);
        myEdit.putString("date",todayDate);

        myEdit.commit();

        // FetchData from shredpref
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        spinCounter  = sh.getInt("spinCounter",0 );
        currentDate  = sh.getString("date", "");
        spinTotal  = sh.getInt("spinTotalLeft", 0);
        binding.totalSpin.setText(String.valueOf("You've left : " +spinTotal));
        binding.spinAvail.setText(String.valueOf(spinCounter));

        getDegreesForSectors();


        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(SpinWheelActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        binding.spinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spinTotal>spinCounter && currentDate.equals(todayDate)){
                    if (!isSpinning){
                        Log.d("left", "bvalue:"+spinTotalLeft);
                        spin();
                        isSpinning = true;
                        // spinCounterPlus =  spinCounter++;
                        spinTotalLeft = --spinTotal;
                        Log.d("left", "value:"+spinTotalLeft);
                        //Log.d(String.valueOf(spinTotalLeft),"how");
                        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                        myEdit.putInt("spinCounter",spinCounterPlus);
                        myEdit.putInt("spinTotalLeft",spinTotalLeft);
                        myEdit.commit();
                        binding.spinAvail.setText(String.valueOf(spinCounterPlus));
                        binding.totalSpin.setText(String.valueOf("You've left : " + spinTotalLeft));
                    }
                }else {
                    Toast.makeText(SpinWheelActivity.this, "you Don't have chance", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
    private void spin(){
        degree = random.nextInt(sectors.length-1);
        RotateAnimation rotateAnimation = new RotateAnimation(0,(360*sectors.length)+sectorsDegrees[degree],
                RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);

        rotateAnimation.setDuration(3600);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setInterpolator(new DecelerateInterpolator());
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {


                wonAmount= sectors[sectors.length-(degree+1)];

                dialog.setContentView(R.layout.scratch_diloag);
                dialog.setCancelable(false);
                Objects.requireNonNull(dialog
                        .getWindow()).setBackgroundDrawable
                        (new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                Button button = dialog.findViewById(R.id.addButton);
                TextView textView = dialog.findViewById(R.id.amount1);
                textView.setText(String.valueOf(wonAmount));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (interstitialAd.isReady()){
                            interstitialAd.showAd();
                            for (int i = 0;i<spinTotalLeft;i++){
                                FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                                firestore.collection("USERS")
                                        .document(FirebaseAuth.getInstance().getUid())
                                        .update("coins", FieldValue.increment(wonAmount))
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    dialog.show();
//
                                                }else {
                                                    Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                            }
                        }else {
                            for (int i = 0;i<spinTotalLeft;i++){
                                FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                                firestore.collection("USERS")
                                        .document(FirebaseAuth.getInstance().getUid())
                                        .update("coins", FieldValue.increment(wonAmount))
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    dialog.show();
//
                                                }else {
                                                    Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                            }
                        }

                        startActivity(getIntent());
                        overridePendingTransition(0,0);
                        dialog.dismiss();
                    }
                });

                isSpinning = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        binding.spinWheel.startAnimation(rotateAnimation);



    }

    public void getDegreesForSectors(){
        int sectorsDegree = 360/sectors.length;

        for (int i = 0;i<sectors.length;i++){
            sectorsDegrees[i] =(i+1)*sectorsDegree;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent  = new Intent(SpinWheelActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
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