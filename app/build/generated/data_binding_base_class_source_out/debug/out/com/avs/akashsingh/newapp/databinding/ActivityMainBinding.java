// Generated by view binder compiler. Do not edit!
package com.avs.akashsingh.newapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.avs.akashsingh.newapp.R;
import com.google.android.material.navigation.NavigationView;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMainBinding implements ViewBinding {
  @NonNull
  private final DrawerLayout rootView;

  @NonNull
  public final LinearLayout adView;

  @NonNull
  public final CardView allcategories;

  @NonNull
  public final CardView dailyCheckIn;

  @NonNull
  public final DrawerLayout drawerLayout;

  @NonNull
  public final TextView helloReaders;

  @NonNull
  public final TextView helloReaders1;

  @NonNull
  public final ImageView imageView;

  @NonNull
  public final ImageView imageView1;

  @NonNull
  public final ImageView imageView14;

  @NonNull
  public final CircleImageView imageView15;

  @NonNull
  public final ImageView imageView2;

  @NonNull
  public final ImageView imageView4;

  @NonNull
  public final LinearLayout ll1;

  @NonNull
  public final LinearLayout ll2;

  @NonNull
  public final LinearLayout ll3;

  @NonNull
  public final CardView myProfile;

  @NonNull
  public final CardView myWallet;

  @NonNull
  public final FrameLayout nativeAdLayout;

  @NonNull
  public final NavigationView navigationView;

  @NonNull
  public final RelativeLayout rel;

  @NonNull
  public final CardView scratchCard;

  @NonNull
  public final CardView spinWheel;

  @NonNull
  public final TextView textView;

  @NonNull
  public final TextView textView1;

  @NonNull
  public final TextView textView14;

  @NonNull
  public final TextView textView15;

  @NonNull
  public final TextView textView2;

  @NonNull
  public final TextView textView4;

  @NonNull
  public final LinearLayout tg;

  @NonNull
  public final Toolbar toolbar;

  private ActivityMainBinding(@NonNull DrawerLayout rootView, @NonNull LinearLayout adView,
      @NonNull CardView allcategories, @NonNull CardView dailyCheckIn,
      @NonNull DrawerLayout drawerLayout, @NonNull TextView helloReaders,
      @NonNull TextView helloReaders1, @NonNull ImageView imageView, @NonNull ImageView imageView1,
      @NonNull ImageView imageView14, @NonNull CircleImageView imageView15,
      @NonNull ImageView imageView2, @NonNull ImageView imageView4, @NonNull LinearLayout ll1,
      @NonNull LinearLayout ll2, @NonNull LinearLayout ll3, @NonNull CardView myProfile,
      @NonNull CardView myWallet, @NonNull FrameLayout nativeAdLayout,
      @NonNull NavigationView navigationView, @NonNull RelativeLayout rel,
      @NonNull CardView scratchCard, @NonNull CardView spinWheel, @NonNull TextView textView,
      @NonNull TextView textView1, @NonNull TextView textView14, @NonNull TextView textView15,
      @NonNull TextView textView2, @NonNull TextView textView4, @NonNull LinearLayout tg,
      @NonNull Toolbar toolbar) {
    this.rootView = rootView;
    this.adView = adView;
    this.allcategories = allcategories;
    this.dailyCheckIn = dailyCheckIn;
    this.drawerLayout = drawerLayout;
    this.helloReaders = helloReaders;
    this.helloReaders1 = helloReaders1;
    this.imageView = imageView;
    this.imageView1 = imageView1;
    this.imageView14 = imageView14;
    this.imageView15 = imageView15;
    this.imageView2 = imageView2;
    this.imageView4 = imageView4;
    this.ll1 = ll1;
    this.ll2 = ll2;
    this.ll3 = ll3;
    this.myProfile = myProfile;
    this.myWallet = myWallet;
    this.nativeAdLayout = nativeAdLayout;
    this.navigationView = navigationView;
    this.rel = rel;
    this.scratchCard = scratchCard;
    this.spinWheel = spinWheel;
    this.textView = textView;
    this.textView1 = textView1;
    this.textView14 = textView14;
    this.textView15 = textView15;
    this.textView2 = textView2;
    this.textView4 = textView4;
    this.tg = tg;
    this.toolbar = toolbar;
  }

  @Override
  @NonNull
  public DrawerLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_main, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMainBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.adView;
      LinearLayout adView = ViewBindings.findChildViewById(rootView, id);
      if (adView == null) {
        break missingId;
      }

      id = R.id.allcategories;
      CardView allcategories = ViewBindings.findChildViewById(rootView, id);
      if (allcategories == null) {
        break missingId;
      }

      id = R.id.daily_checkIn;
      CardView dailyCheckIn = ViewBindings.findChildViewById(rootView, id);
      if (dailyCheckIn == null) {
        break missingId;
      }

      DrawerLayout drawerLayout = (DrawerLayout) rootView;

      id = R.id.helloReaders;
      TextView helloReaders = ViewBindings.findChildViewById(rootView, id);
      if (helloReaders == null) {
        break missingId;
      }

      id = R.id.helloReaders1;
      TextView helloReaders1 = ViewBindings.findChildViewById(rootView, id);
      if (helloReaders1 == null) {
        break missingId;
      }

      id = R.id.imageView;
      ImageView imageView = ViewBindings.findChildViewById(rootView, id);
      if (imageView == null) {
        break missingId;
      }

      id = R.id.imageView1;
      ImageView imageView1 = ViewBindings.findChildViewById(rootView, id);
      if (imageView1 == null) {
        break missingId;
      }

      id = R.id.imageView14;
      ImageView imageView14 = ViewBindings.findChildViewById(rootView, id);
      if (imageView14 == null) {
        break missingId;
      }

      id = R.id.imageView15;
      CircleImageView imageView15 = ViewBindings.findChildViewById(rootView, id);
      if (imageView15 == null) {
        break missingId;
      }

      id = R.id.imageView2;
      ImageView imageView2 = ViewBindings.findChildViewById(rootView, id);
      if (imageView2 == null) {
        break missingId;
      }

      id = R.id.imageView4;
      ImageView imageView4 = ViewBindings.findChildViewById(rootView, id);
      if (imageView4 == null) {
        break missingId;
      }

      id = R.id.ll1;
      LinearLayout ll1 = ViewBindings.findChildViewById(rootView, id);
      if (ll1 == null) {
        break missingId;
      }

      id = R.id.ll2;
      LinearLayout ll2 = ViewBindings.findChildViewById(rootView, id);
      if (ll2 == null) {
        break missingId;
      }

      id = R.id.ll3;
      LinearLayout ll3 = ViewBindings.findChildViewById(rootView, id);
      if (ll3 == null) {
        break missingId;
      }

      id = R.id.myProfile;
      CardView myProfile = ViewBindings.findChildViewById(rootView, id);
      if (myProfile == null) {
        break missingId;
      }

      id = R.id.myWallet;
      CardView myWallet = ViewBindings.findChildViewById(rootView, id);
      if (myWallet == null) {
        break missingId;
      }

      id = R.id.native_ad_layout;
      FrameLayout nativeAdLayout = ViewBindings.findChildViewById(rootView, id);
      if (nativeAdLayout == null) {
        break missingId;
      }

      id = R.id.navigation_view;
      NavigationView navigationView = ViewBindings.findChildViewById(rootView, id);
      if (navigationView == null) {
        break missingId;
      }

      id = R.id.rel;
      RelativeLayout rel = ViewBindings.findChildViewById(rootView, id);
      if (rel == null) {
        break missingId;
      }

      id = R.id.scratchCard;
      CardView scratchCard = ViewBindings.findChildViewById(rootView, id);
      if (scratchCard == null) {
        break missingId;
      }

      id = R.id.spinWheel;
      CardView spinWheel = ViewBindings.findChildViewById(rootView, id);
      if (spinWheel == null) {
        break missingId;
      }

      id = R.id.textView;
      TextView textView = ViewBindings.findChildViewById(rootView, id);
      if (textView == null) {
        break missingId;
      }

      id = R.id.textView1;
      TextView textView1 = ViewBindings.findChildViewById(rootView, id);
      if (textView1 == null) {
        break missingId;
      }

      id = R.id.textView14;
      TextView textView14 = ViewBindings.findChildViewById(rootView, id);
      if (textView14 == null) {
        break missingId;
      }

      id = R.id.textView15;
      TextView textView15 = ViewBindings.findChildViewById(rootView, id);
      if (textView15 == null) {
        break missingId;
      }

      id = R.id.textView2;
      TextView textView2 = ViewBindings.findChildViewById(rootView, id);
      if (textView2 == null) {
        break missingId;
      }

      id = R.id.textView4;
      TextView textView4 = ViewBindings.findChildViewById(rootView, id);
      if (textView4 == null) {
        break missingId;
      }

      id = R.id.tg;
      LinearLayout tg = ViewBindings.findChildViewById(rootView, id);
      if (tg == null) {
        break missingId;
      }

      id = R.id.toolbar;
      Toolbar toolbar = ViewBindings.findChildViewById(rootView, id);
      if (toolbar == null) {
        break missingId;
      }

      return new ActivityMainBinding((DrawerLayout) rootView, adView, allcategories, dailyCheckIn,
          drawerLayout, helloReaders, helloReaders1, imageView, imageView1, imageView14,
          imageView15, imageView2, imageView4, ll1, ll2, ll3, myProfile, myWallet, nativeAdLayout,
          navigationView, rel, scratchCard, spinWheel, textView, textView1, textView14, textView15,
          textView2, textView4, tg, toolbar);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
