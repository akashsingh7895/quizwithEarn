package com.avssolution.akashsingh.quizearnxyz;

import static com.avssolution.akashsingh.quizearnxyz.MainActivity.BannerID;
import static com.avssolution.akashsingh.quizearnxyz.MainActivity.InterID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.avssolution.akashsingh.quizearnxyz.databinding.ActivityWatchVideoBinding;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.unity3d.ads.UnityAds;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;

public class WatchVideoActivity extends AppCompatActivity {

    ActivityWatchVideoBinding binding;
    long coins= 0;
    FirebaseFirestore firestore;
    private RewardedAd mRewardedAd;
    private AdView mAdView;

    public static Dialog loadingDialog,loadingDialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWatchVideoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firestore = FirebaseFirestore.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                UnityAds.show(WatchVideoActivity.this,InterID);

            }
        }, 10000);

        BannerView view = new BannerView(WatchVideoActivity.this,BannerID,new UnityBannerSize(320,50));
        view.load();
        binding.adView.addView(view);



        ///loading Dialog
        loadingDialog1 = new Dialog(WatchVideoActivity.this);
        loadingDialog1.setContentView(R.layout.loading_progress_dialog);
        loadingDialog1.setCancelable(false);
        loadingDialog1.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog1.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog1.show();
        /////end loading dialog





        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // fetch coins

        // coins fetch
        firestore.collection("USERS")
                .document(FirebaseAuth.getInstance().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()){
                            loadingDialog1.dismiss();
                            DocumentSnapshot snapshot = task.getResult();
                            coins = (long) snapshot.get("coins");  // error line

                            binding.coinsCoins.setText(String.valueOf(coins));
                           // loadingDialog1.dismiss();
                        }else {
                            Toast.makeText(getApplicationContext(), ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

        //   coins fetch finish

        binding.video1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


    }

}

//
//    Activity activityContext = WatchVideoActivity.this;
//                mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
//@Override
//public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
//        // loadAd1();
//
//        coins = coins + 10;
//        firestore.collection("USERS")
//        .document(FirebaseAuth.getInstance().getUid())
//        .update("coins",coins).addOnCompleteListener(new OnCompleteListener<Void>() {
//@Override
//public void onComplete(@NonNull Task<Void> task) {
//        if (task.isSuccessful()){
//        firestore.collection("USERS")
//        .document(FirebaseAuth.getInstance().getUid())
//        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//@Override
//public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//        if (task.isSuccessful()){
//
//        DocumentSnapshot snapshot = task.getResult();
//        coins = (long) snapshot.get("coins");  // error line
//
//        binding.coinsCoins.setText(String.valueOf(coins));
//        }else {
//        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
//        }
//        }
//        });
//
//        }else {
//        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
//        }
//        }
//        });
//
//        binding.video1Image.setImageResource(R.drawable.check);
//        }
//        });
