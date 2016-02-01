package com.bignerdranch.android.sunset;

import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;

/**
 * Created by My on 2/1/2016.
 */
public class SunsetFragment extends Fragment {
   private View   mSceneView;
   private View   mSunView;
   private View   mSkyView;
   private int    mBlueSkyColor;
   private int    mSunsetSkyColor;
   private int    mNightSkyColor;

   public static SunsetFragment newInstance() {
      return new SunsetFragment();
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_sunset, container, false);
      mSceneView = view;
      mSunView = view.findViewById(R.id.sun);
      mSkyView = view.findViewById(R.id.sky);
      // extract colors
      Resources resources = getResources();
      mBlueSkyColor = resources.getColor(R.color.blue_sky);
      mSunsetSkyColor = resources.getColor(R.color.sunset_sky);
      mNightSkyColor = resources.getColor(R.color.night_sky);
      // press anywhere on the screen to run the animation
      mSceneView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            startAnimation();
         }
      });
      return view;
   }

   private void startAnimation() {
      // animation starts with the top of the view at its current location
      float sunYStart = mSunView.getTop();
      // animation ends with the top at the bottom of mSunView's parent, mSkyView. to get there, it
      // should be as far down as mSkyView is tall, by calling getHeight()
      float sunYEnd = mSkyView.getHeight();
      // create a property animator
      ObjectAnimator heightAnimator = ObjectAnimator.ofFloat(mSunView, "y", sunYStart, sunYEnd).setDuration(3000);
      // add acceleration to make the sun start out moving slowly and accelerate to a quicker pace
      // as it moves toward the horizon.
      heightAnimator.setInterpolator(new AccelerateInterpolator());
      // animator for the sky from mBlueSkyColor to mSunsetSkyColor.
      ObjectAnimator sunsetSkyAnimator = ObjectAnimator.ofInt(mSkyView, "backgroundColor", mBlueSkyColor, mSunsetSkyColor).setDuration(3000);
      // animate the sun going down
      heightAnimator.start();
      // animate the sky color changes
      sunsetSkyAnimator.start();
   }
}
