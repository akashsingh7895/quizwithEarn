package com.avssolution.akashsingh.quizearnxyz;

import static com.avssolution.akashsingh.quizearnxyz.MainActivity.InterID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.avssolution.akashsingh.quizearnxyz.Model.WithdrawRequest;

import com.avssolution.akashsingh.quizearnxyz.databinding.ActivityMyWalletBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.unity3d.ads.UnityAds;

import java.util.HashMap;
import java.util.Map;

public class MyWalletActivity extends AppCompatActivity implements MaxAdListener {
    ActivityMyWalletBinding binding;
    FirebaseFirestore database;
    FirebaseAuth firebaseAuth;

    private long coins;
    private String name;
    private long coinsAmount;

    public static Dialog loadingDialog,loadingDialog1;

    //applovin ads
    private MaxInterstitialAd interstitialAd;
    private MaxNativeAdLoader nativeAdLoader;
    private MaxAd nativeAd;


    String paymentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyWalletBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();


        interstitialAd = new MaxInterstitialAd(getString(R.string.inter),this);
        interstitialAd.setListener(this);
        interstitialAd.loadAd();
       // loadnetiveAd();
        //applovin

//

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                finish();

            }
        });

        ///loading Dialog
        loadingDialog1 = new Dialog(MyWalletActivity.this);
        loadingDialog1.setContentView(R.layout.loading_progress_dialog);
        loadingDialog1.setCancelable(false);
        loadingDialog1.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog1.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog1.show();
        /////end loading dialog


        loadingDialog = new Dialog(MyWalletActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        // coins fetch
        database.collection("USERS")
                .document(firebaseAuth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()){
                            DocumentSnapshot snapshot = task.getResult();
                            coins = (long) snapshot.get("coins");  // error line
                            name = (String)snapshot.get("name");
                            binding.textView3.setText(String.valueOf("Coins = "+coins));
                            loadingDialog1.dismiss();
                        }else {
                            Toast.makeText(getApplicationContext(), ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

      //   coins fetch finish

        binding.g1.setChecked(true);



       binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.g1:
                        // do operations specific to this selection
                        binding.withdrawMob.setHint("Enter G pay Number");
                        paymentType = "G pay";
                        Toast.makeText(MyWalletActivity.this, ""+paymentType, Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.g2:
                        // do operations specific to this selection
                        binding.withdrawMob.setHint("Enter Phone pay Number");
                        paymentType = "Phone pay";
                        Toast.makeText(MyWalletActivity.this, ""+paymentType, Toast.LENGTH_SHORT).show();



                        break;
                    case R.id.g3:
                        // do operations specific to this selection
                        binding.withdrawMob.setHint("Enter Paytm Number");
                        paymentType = "Paytm";
                        Toast.makeText(MyWalletActivity.this, ""+paymentType, Toast.LENGTH_SHORT).show();



                        break;
                    case R.id.g4:
                        // do operations specific to this selection
                        binding.withdrawMob.setHint("Enter Paypal Gmail I'D");
                        paymentType = "Paypal";
                        Toast.makeText(MyWalletActivity.this, ""+paymentType, Toast.LENGTH_SHORT).show();



                        break;
                }
            }
        });

        binding.textView3.setText(String.valueOf(coins));
        binding.requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (!binding.withdrawMob.getText().toString().equals("")){

                        if (!binding.amount.getText().toString().equals("")){
                            loadingDialog.show();
                            binding.requestButton.setEnabled(true);
                            if(coins > 5000) {
                                String uid = FirebaseAuth.getInstance().getUid();
                                String mobil =   binding.withdrawMob.getText().toString();
                               // coinsAmount = Long.parseLong(binding.amount.getText().toString());
                                coinsAmount = Long.parseLong(binding.amount.getText().toString());

                                if (coins>=coinsAmount) {

                                    WithdrawRequest request = new WithdrawRequest(uid, mobil, name, coinsAmount,paymentType);
                                    database
                                            .collection("withdraws")
                                            .document(uid)
                                            .set(request).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            long finalCoins = coins - coinsAmount;
                                            binding.textView3.setText(String.valueOf("Coins = " + finalCoins));
                                            Map<String, Object> updateUserData = new HashMap<>();
                                            updateUserData.put("coins", finalCoins);

                                            database.collection("USERS").document(firebaseAuth.getUid())
                                                    .update(updateUserData)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                              //  UnityAds.show(MyWalletActivity.this,InterID);
                                                            } else {
                                                                Toast.makeText(getApplicationContext(), "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });

                                            loadingDialog.dismiss();
                                            Dialog dialog = new Dialog(MyWalletActivity.this);
                                            dialog.setContentView(R.layout.request_send_diloag);
                                            dialog.setCancelable(true);
                                            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                                            dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
                                            dialog.show();
                                            binding.withdrawMob.setText("");
                                            binding.amount.setText("");
                                            Toast.makeText(MyWalletActivity.this, "Request sent successfully.", Toast.LENGTH_SHORT).show();
                                            binding.requestButton.setEnabled(true);


                                        }
                                    });

                                }else {

                                    loadingDialog.dismiss();
                                    Dialog dialog = new Dialog(MyWalletActivity.this);
                                    dialog.setContentView(R.layout.infcument_coins_diolag);
                                    dialog.setCancelable(true);
                                    dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                                    dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
                                    dialog.show();
                                    binding.requestButton.setEnabled(true);
                                    UnityAds.show(MyWalletActivity.this,InterID);
                                }


                            } else {
                                loadingDialog.dismiss();
                                Dialog dialog = new Dialog(MyWalletActivity.this);
                                dialog.setContentView(R.layout.infcument_coins_diolag);
                                dialog.setCancelable(true);
                                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                                dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
                                dialog.show();
                                binding.requestButton.setEnabled(true);
                            }
                        }else {
                            binding.amount.setError("please enter coins");
                        }
                    }else {
                        binding.withdrawMob.setError("Please enter coins");
                    }

            }
        });


    }

    @Override
    public void onBackPressed() {

        finish();
//        if (interstitialAd.isReady()){
//            interstitialAd.showAd();
//            finish();
//        }else {
//            finish();
//        }
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