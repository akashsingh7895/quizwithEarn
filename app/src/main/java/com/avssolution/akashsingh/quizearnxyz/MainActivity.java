package com.avssolution.akashsingh.quizearnxyz;

import androidx.annotation.NonNull;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.mediation.nativeAds.MaxNativeAdView;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;
import com.avssolution.akashsingh.quizearnxyz.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;


import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.unity3d.ads.UnityAds;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements MaxAdListener {

    private ActivityMainBinding binding;
    private FirebaseFirestore database;
    private FirebaseAuth firebaseAuth;
    private ActionBarDrawerToggle toggle;

    long coins;
    String name;
    public static Dialog loadingDialog;


    String date,currentDate,todayDate;
    //spinweel
    int spinCounter = 0;
    int spinTotal = 15;

    // DalyBonus
    int spinCounter4 = 0;
    int spinTotal4= 1;

    // scratchCard
    int spinCounter5 = 0;
    int spinTotal5= 15;

    private FirebaseAnalytics analytics;


    public static  String GameID = "4726509";
    public static String BannerID="Banner_Android";
    public static String InterID="Interstitial_Android";
    public static Boolean TestMode = false; //(select any one. if you test then you select true)

    //applovin ads
    private MaxInterstitialAd interstitialAd;
    private MaxNativeAdLoader nativeAdLoader;
    private MaxAd nativeAd;

    ProgressDialog progressDialog;
    //applovin ads
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        analytics = FirebaseAnalytics.getInstance(MainActivity.this);


        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        currentDate  = sh.getString("date", "");

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        todayDate = df.format(Calendar.getInstance().getTime());
        if(!currentDate.equals(todayDate)){
            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.putString("date",todayDate);

            myEdit.putInt("spinCounter",spinCounter);
            myEdit.putInt("spinTotalLeft",spinTotal);

            myEdit.putInt("spinCounter4",spinCounter4);
            myEdit.putInt("spinTotalLeft4",spinTotal4);

            myEdit.putInt("spinCounter5",spinCounter5);
            myEdit.putInt("spinTotalLeft5",spinTotal5);

            myEdit.commit();
        }



        //applovin
        AppLovinSdk.getInstance( this ).setMediationProvider( "max" );
        AppLovinSdk.initializeSdk( this, new AppLovinSdk.SdkInitializationListener() {
            @Override
            public void onSdkInitialized(final AppLovinSdkConfiguration configuration)
            {
                // AppLovin SDK is initialized, start loading ads
            }
        } );

        interstitialAd = new MaxInterstitialAd(getString(R.string.inter),this);
        interstitialAd.setListener(this);
        interstitialAd.loadAd();
        loadnetiveAd();
        //applovin


        UnityAds.initialize(this,GameID,TestMode);
        BannerView view = new BannerView(MainActivity.this,BannerID,new UnityBannerSize(320,50));
        view.load();
        binding.adView.addView(view);

        binding.toolbar.setTitle(getString(R.string.app_name));
        binding.toolbar.setTitleTextColor(getResources().getColor(R.color.white));

//

        drawerIcon();
        ///loading Dialog
        loadingDialog = new Dialog(MainActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
        /////end loading dialog

        loadInterstital();

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                UnityAds.show(MainActivity.this,InterID);
//
//            }
//        }, 10000);



        binding.allcategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  UnityAds.show(MainActivity.this,InterID);
//                if (interstitialAd.isReady()){
//                    interstitialAd.showAd();
//
//
//                }else {
//                    startActivity(new Intent(MainActivity.this,CategoryActivity.class));
//
//                }

                startActivity(new Intent(MainActivity.this,CategoryActivity.class));

            }
        });

        binding.myWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  UnityAds.show(MainActivity.this,InterID);
//                if (interstitialAd.isReady()){
//                    interstitialAd.showAd();
//
//                }else {
//                    Intent intent = new Intent(MainActivity.this,MyWalletActivity.class);
//                    startActivity(intent);
//                }

                Intent intent = new Intent(MainActivity.this,MyWalletActivity.class);
                startActivity(intent);

            }
        });

        binding.myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  UnityAds.show(MainActivity.this,InterID);
//                if (interstitialAd.isReady()){
//                    interstitialAd.showAd();
//
//                }else {
//                    startActivity(new Intent(MainActivity.this,ProfileActivity.class));
//
//                }
                startActivity(new Intent(MainActivity.this,ProfileActivity.class));

            }
        });
        binding.spinWheel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              if (interstitialAd.isReady()){
//                  interstitialAd.showAd();
//
//              }else {
//                  startActivity(new Intent(MainActivity.this,SpinWheelActivity.class));
//
//              }

                startActivity(new Intent(MainActivity.this,SpinWheelActivity.class));


            }
        });

//        // open custom tab chrome for qreka
//        String url = getString(R.string.play_qureka);
//        CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
//        customIntent.setToolbarColor(ContextCompat.getColor(MainActivity.this, R.color.darkBlue));
//         //open custom tab chrome for qreka

        binding.dailyCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (interstitialAd.isReady()){
//                    interstitialAd.showAd();
//
//                }else {
//                    startActivity(new Intent(MainActivity.this,DailyCheckActivity.class));
//
//                }
                startActivity(new Intent(MainActivity.this,DailyCheckActivity.class));

            }
        });

        binding.scratchCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (interstitialAd.isReady()){
//                    interstitialAd.showAd();
//
//                }else {
//                    startActivity(new Intent(MainActivity.this,ScratchCardActivity.class));
//
//                }
                startActivity(new Intent(MainActivity.this,ScratchCardActivity.class));


            }
        });


//        binding.tg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/+qlt2BCHVmUwyMTVl"));
//                    PackageManager pm = getPackageManager();
//                    if (intent.resolveActivity(pm) != null) {
//                        startActivity(intent);
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Error message", Toast.LENGTH_LONG).show();
//                    }
//                } catch (Exception ignored) {
//                    Toast.makeText(MainActivity.this, ""+ignored, Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


        firebaseNotification();

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
                loadingDialog.dismiss();
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
                loadingDialog.dismiss();
                // We recommend retrying with exponentially higher delays up to a maximum delay
            }

            @Override
            public void onNativeAdClicked(final MaxAd ad)
            {
                // Optional click callback
                loadingDialog.dismiss();
            }
        } );

        nativeAdLoader.loadAd();

    }


    public static void openCustomTab(Activity activity, CustomTabsIntent customTabsIntent, Uri uri) {

        String packageName = "com.android.chrome";
        if (packageName != null) {
            customTabsIntent.intent.setPackage(packageName);
            customTabsIntent.launchUrl(activity, uri);
        } else {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    }

    private void loadInterstital() {
        if (UnityAds.isInitialized()){
            UnityAds.load((InterID));
        }else {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    UnityAds.load(InterID);

                }
            }, 5000);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        database.collection("USERS").document(firebaseAuth.getCurrentUser().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                      if (task.isSuccessful()){
                          DocumentSnapshot snapshot = task.getResult();
                          name = (String)snapshot.get("name");
                          coins = (long) snapshot.get("coins");
                          binding.coinsTotal.setText(String.valueOf("Coins " + coins));
                          binding.helloReaders1.setText(name);
                          loadingDialog.dismiss();
                      }else {
                          Toast.makeText(getApplicationContext(), ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                      }
            }
        });

    }
    void firebaseNotification(){
        FirebaseMessaging.getInstance().subscribeToTopic("QPaisa")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg ="Done";
                        if (!task.isSuccessful()) {
                            msg = "Failed";
                        }

                       // Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("कृपया इस ऐप को Play store पर 5 Star ratings दें Thank you ")
                .setCancelable(false)
                .setPositiveButton("exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        MainActivity.super.onBackPressed();

                    }
                })
                .setNegativeButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try{
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+getPackageName())));
                        }
                        catch (ActivityNotFoundException e){
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+getPackageName())));
                        }
                    }
                })
                .show();
    }
    public void drawerIcon(){
        toggle = new ActionBarDrawerToggle(this,binding.drawerLayout,binding.toolbar,R.string.drawerOpen,R.string.drawerClose);

        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

        binding. drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        binding.navigationView.setItemIconTintList(null);

        binding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            MenuItem menuItem;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                menuItem = item;


                binding.drawerLayout.closeDrawer(GravityCompat.START);
                binding.drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
                    @Override
                    public void onDrawerClosed(View drawerView) {
                        super.onDrawerClosed(drawerView);

                        switch (menuItem.getItemId()) {

                            case R.id.myProfile:
                                       //  UnityAds.show(MainActivity.this,InterID);
                                         startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                                         break;
                                     case R.id.myWallet:
                                        // UnityAds.show(MainActivity.this,InterID);
                                         startActivity(new Intent(MainActivity.this,MyWalletActivity.class));

                                         break;
                                     case R.id.shareThis:

                                         try {
                                             Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                             shareIntent.setType("text/plain");
                                             shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                                             String shareMessage= "\nLet me recommend you this application You can get Rs 50/- as SIGN UP Bonous\n\n";
                                             shareMessage = shareMessage + "https://play.google.com/store/apps/details?id="+getPackageName();
                                             shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                                             startActivity(Intent.createChooser(shareIntent, "choose one"));
                                         } catch(Exception e) {
                                             //e.toString();
                                         }

                                         break;
                                     case R.id.rateThisApp:

                                         try{
                                             startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+getPackageName())));
                                         }
                                         catch (ActivityNotFoundException e){
                                             startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+getPackageName())));
                                         }

                                         break;
                                     case R.id.contactUs:
                                         UnityAds.show(MainActivity.this,InterID);
                                         Intent i = new Intent(Intent.ACTION_SEND);
                                         i.setType("message/rfc822");
                                         i.putExtra(Intent.EXTRA_EMAIL  , new String[]{getString(R.string.supported_email)});
                                         i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
                                         i.putExtra(Intent.EXTRA_TEXT   , "body of email");
                                         try {
                                             startActivity(Intent.createChooser(i, "Send mail..."));
                                         } catch (android.content.ActivityNotFoundException ex) {
                                             Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                                         }

                                         break;
                                     case R.id.privacyPoliy:

                                         Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.privacy_policy)));
                                         startActivity(browserIntent);

                                         break;
                                     case R.id.termsCondi:
                                         Intent searchIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.terms_condition)));
                                         startActivity(searchIntent);

                                         break;
                                     case R.id.logout:
                                         FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                                         firebaseAuth.signOut();
                                         startActivity(new Intent(MainActivity.this,LogInActivity.class));

                        }
                        binding.drawerLayout.removeDrawerListener(this);


                    }
                });

                return true;
            }
        });
    }

    @Override
    public void onAdLoaded(MaxAd ad) {
     //   Toast.makeText(MainActivity.this, "lodInter", Toast.LENGTH_SHORT).show();

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
        //Toast.makeText(MainActivity.this, "Ads Not Load", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onAdDisplayFailed(MaxAd ad, MaxError error) {

    }
}