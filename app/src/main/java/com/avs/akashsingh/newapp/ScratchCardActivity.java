package com.avs.akashsingh.newapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anupkumarpanwar.scratchview.ScratchView;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.mediation.nativeAds.MaxNativeAdView;
import com.avs.akashsingh.newapp.databinding.ActivityScratchCardBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;
import java.util.Random;

public class ScratchCardActivity extends AppCompatActivity implements MaxAdListener {

    ActivityScratchCardBinding binding;


    Dialog dialog,dialog1;

    Button button;
    TextView textView;
    Random random;
    int randomNumber;

    int spinCounter5 = 0;
    int spinTotal5 = 16;
    String todayDate,currentDate;

    int spinCounterPlus5 ;
    int spinTotalLeft5 ;



    //applovin ads
    private MaxInterstitialAd interstitialAd;
    private MaxNativeAdLoader nativeAdLoader;
    private MaxAd nativeAd;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scratch_card);
        binding = ActivityScratchCardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        interstitialAd = new MaxInterstitialAd(getString(R.string.inter),this);
        interstitialAd.setListener(this);
        interstitialAd.loadAd();
        loadnetiveAd();

        dialog = new Dialog(this);
        dialog1 = new Dialog(this);

        random = new Random();
        randomNumber = random.nextInt(25);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        todayDate = df.format(Calendar.getInstance().getTime());

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        myEdit.putInt("spinCounter5",spinCounter5);
        myEdit.putInt("spinTotal5",spinTotal5);
        myEdit.putString("date",todayDate);

        myEdit.commit();

        // FetchData from shredpref
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        spinCounter5  = sh.getInt("spinCounter5",0 );
        currentDate  = sh.getString("date", "");
        spinTotal5  = sh.getInt("spinTotalLeft5", 0);
        binding.totalSpin.setText(String.valueOf("You've left : " +spinTotal5));


        if (spinTotal5>spinCounter5 && currentDate.equals(todayDate)){


            binding.scratchView.setRevealListener(new ScratchView
                    .IRevealListener() {
                @Override
                public void onRevealed(ScratchView scratchView) {


                    spinTotalLeft5 = --spinTotal5;
                    SharedPreferences sharedPreferences1 = getSharedPreferences("MySharedPref",MODE_PRIVATE);
                    SharedPreferences.Editor myEdit1 = sharedPreferences1.edit();
                    myEdit1.putInt("spinCounter5",spinCounterPlus5);
                    myEdit1.putInt("spinTotalLeft5",spinTotalLeft5);
                    myEdit1.commit();
                    binding.totalSpin.setText(String.valueOf("You've left : " +spinTotalLeft5));

                    binding.wonAmount.setText(String.valueOf(randomNumber));
                    dialog.setContentView(R.layout.scratch_diloag);
                    dialog.setCancelable(false);
                    Objects.requireNonNull(dialog
                            .getWindow()).setBackgroundDrawable
                            (new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                    button = dialog.findViewById(R.id.addButton);
                    textView = dialog.findViewById(R.id.amount1);
                    textView.setText(String.valueOf(randomNumber));
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (interstitialAd.isReady()){
                                interstitialAd.showAd();
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


                }
                @Override
                public void onRevealPercentChangedListener
                        (ScratchView scratchView, float percent) {
                    Log.d("Revealed", String.valueOf(percent));
                }
            });

        }else {
            dialog1.setContentView(R.layout.insuffecent_coin_diloag);
            dialog1.setCancelable(false);
            Objects.requireNonNull(dialog
                    .getWindow()).setBackgroundDrawable
                    (new ColorDrawable(Color.TRANSPARENT));
            dialog1.show();
            button = dialog1.findViewById(R.id.addButton);
            textView = dialog1.findViewById(R.id.amount1);
            TextView textView1 = dialog1.findViewById(R.id.text);
            textView1.setText("You Don't Have any chance !");

            textView.setText(String.valueOf(randomNumber));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ScratchCardActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScratchCardActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    void loadnetiveAd(){

        FrameLayout nativeAdContainer = findViewById( R.id.native_ad_layout );

        nativeAdLoader = new MaxNativeAdLoader( getString(R.string.netive), this );
        nativeAdLoader.setNativeAdListener( new MaxNativeAdListener()
        {
            @Override
            public void onNativeAdLoaded(final MaxNativeAdView nativeAdView, final MaxAd ad)
            {
                nativeAdContainer.setVisibility(View.VISIBLE);
               // loadingDialog.dismiss();
                // Clean up any pre-existing native ad to prevent memory leaks.
                if ( nativeAd != null )
                {
                    nativeAdLoader.destroy( nativeAd );
                }

                // Save ad for cleanup.
                nativeAd = ad;

                // Add ad view to view.
                nativeAdContainer.removeAllViews();
                nativeAdContainer.addView( nativeAdView );
            }

            @Override
            public void onNativeAdLoadFailed(final String adUnitId, final MaxError error)
            {
                nativeAdContainer.setVisibility(View.GONE);
                // Toast.makeText(MainActivity.this, "NetiveFailed", Toast.LENGTH_SHORT).show();
              //  loadingDialog.dismiss();
                // We recommend retrying with exponentially higher delays up to a maximum delay
            }

            @Override
            public void onNativeAdClicked(final MaxAd ad)
            {
                // Optional click callback
               // loadingDialog.dismiss();
            }
        } );

        nativeAdLoader.loadAd();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ScratchCardActivity.this,MainActivity.class);
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