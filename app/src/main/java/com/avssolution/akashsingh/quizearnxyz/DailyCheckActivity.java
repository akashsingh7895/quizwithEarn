package com.avssolution.akashsingh.quizearnxyz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.MaxReward;
import com.applovin.mediation.MaxRewardedAdListener;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.mediation.ads.MaxRewardedAd;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.avssolution.akashsingh.quizearnxyz.databinding.ActivityDailyCheckBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;
import java.util.Random;

public class DailyCheckActivity extends AppCompatActivity implements MaxAdListener, MaxRewardedAdListener {
    ActivityDailyCheckBinding binding;

    int spinCounter4 = 0;
    int spinTotal4 = 1;
    String todayDate,currentDate;

    int spinCounterPlus4 ;
    int spinTotalLeft4 ;

    Dialog dialog,dialog1;

    Random random;
    int randomNumber;



    private MaxInterstitialAd interstitialAd;
    private MaxNativeAdLoader nativeAdLoader;
    private MaxAd nativeAd;

    private MaxRewardedAd rewardedAd;
    private int           retryAttempt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_check);
        binding = ActivityDailyCheckBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        interstitialAd = new MaxInterstitialAd(getString(R.string.inter),this);
        interstitialAd.setListener(this);
        interstitialAd.loadAd();
        createRewardedAd();

        dialog = new Dialog(this);
        dialog1 = new Dialog(this);


        random = new Random();
        randomNumber = random.nextInt(25);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        todayDate = df.format(Calendar.getInstance().getTime());

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        myEdit.putInt("spinCounter4",spinCounter4);
        myEdit.putInt("spinTotal4",spinTotal4);
        myEdit.putString("date",todayDate);

        myEdit.commit();


        // FetchData from shredpref
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        spinCounter4  = sh.getInt("spinCounter4",0 );
        currentDate  = sh.getString("date", "");
        spinTotal4  = sh.getInt("spinTotalLeft4", 0);
        binding.totalSpin.setText(String.valueOf("You've left : " +spinTotal4));
//        binding.spinAvail.setText(String.valueOf(spinCounter4));


        binding.claimButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spinTotal4>spinCounter4 && currentDate.equals(todayDate)){
                    //  spinCounterPlus =  spinCounter++;
                    // spinTotalLeft4 = spinTotal4--;
                    spinTotalLeft4 = --spinTotal4;
                    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putInt("spinCounter4",spinCounterPlus4);
                    myEdit.putInt("spinTotalLeft4",spinTotalLeft4);
                    myEdit.commit();

                    dialog.setContentView(R.layout.scratch_diloag);
                    dialog.setCancelable(false);
                    Objects.requireNonNull(dialog
                            .getWindow()).setBackgroundDrawable
                            (new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                    Button button = dialog.findViewById(R.id.addButton);
                    TextView textView = dialog.findViewById(R.id.amount1);
                    TextView  textView1 = dialog.findViewById(R.id.text);
                    textView1.setText("Cograts Your Bonus");
                    textView.setText(String.valueOf(randomNumber));
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (rewardedAd.isReady()){
                                rewardedAd.showAd();
//                                FirebaseFirestore firestore = FirebaseFirestore.getInstance();
//                                firestore.collection("USERS")
//                                        .document(FirebaseAuth.getInstance().getUid())
//                                        .update("coins", FieldValue.increment(randomNumber))
//                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<Void> task) {
//                                                if (task.isSuccessful()){
//                                                    //dialog.show();
////
//                                                }else {
//                                                    Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
//                                                }
//                                            }
//                                        });
                            }else {
                                FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                                firestore.collection("USERS")
                                        .document(FirebaseAuth.getInstance().getUid())
                                        .update("coins", FieldValue.increment(randomNumber))
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    //dialog.show();
//
                                                }else {
                                                    Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }


                                startActivity(getIntent());
                                overridePendingTransition(0,0);
                                dialog.cancel();

                        }
                    });


                }else {
                    dialog1.setContentView(R.layout.insuffecent_coin_diloag);
                    dialog1.setCancelable(false);
                    Objects.requireNonNull(dialog
                            .getWindow()).setBackgroundDrawable
                            (new ColorDrawable(Color.TRANSPARENT));
                    dialog1.show();
                    Button button = dialog1.findViewById(R.id.addButton);
                    TextView  textView = dialog1.findViewById(R.id.amount1);
                    TextView textView1  = dialog1.findViewById(R.id.text);
                    textView1.setText("You Don't Have any chance !");

                    textView.setText(String.valueOf(randomNumber));
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (rewardedAd.isReady()){
                                rewardedAd.showAd();
                                Intent intent = new Intent(DailyCheckActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else {
                                Intent intent = new Intent(DailyCheckActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }

                        }
                    });
                }
            }





        });


        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DailyCheckActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DailyCheckActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    void createRewardedAd(){

        rewardedAd = MaxRewardedAd.getInstance( getString(R.string.reward), this );
        rewardedAd.setListener( this );

        rewardedAd.loadAd();
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

    @Override
    public void onRewardedVideoStarted(MaxAd ad) {

    }

    @Override
    public void onRewardedVideoCompleted(MaxAd ad) {

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("USERS")
                .document(FirebaseAuth.getInstance().getUid())
                .update("coins", FieldValue.increment(randomNumber))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            //dialog.show();
//
                        }else {
                            Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    public void onUserRewarded(MaxAd ad, MaxReward reward) {

    }
}