// Generated by view binder compiler. Do not edit!
package com.avs.akashsingh.newapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.avs.akashsingh.newapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityLeaderBordBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView backButton;

  @NonNull
  public final RecyclerView dddd;

  @NonNull
  public final Toolbar toolbar2;

  private ActivityLeaderBordBinding(@NonNull ConstraintLayout rootView,
      @NonNull ImageView backButton, @NonNull RecyclerView dddd, @NonNull Toolbar toolbar2) {
    this.rootView = rootView;
    this.backButton = backButton;
    this.dddd = dddd;
    this.toolbar2 = toolbar2;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityLeaderBordBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityLeaderBordBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_leader_bord, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityLeaderBordBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.backButton;
      ImageView backButton = ViewBindings.findChildViewById(rootView, id);
      if (backButton == null) {
        break missingId;
      }

      id = R.id.dddd;
      RecyclerView dddd = ViewBindings.findChildViewById(rootView, id);
      if (dddd == null) {
        break missingId;
      }

      id = R.id.toolbar2;
      Toolbar toolbar2 = ViewBindings.findChildViewById(rootView, id);
      if (toolbar2 == null) {
        break missingId;
      }

      return new ActivityLeaderBordBinding((ConstraintLayout) rootView, backButton, dddd, toolbar2);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}