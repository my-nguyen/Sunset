package com.bignerdranch.android.sunset;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
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
      // animator for the sky going from blue to orange
      ObjectAnimator sunsetSkyAnimator = ObjectAnimator.ofInt(mSkyView, "backgroundColor", mBlueSkyColor, mSunsetSkyColor).setDuration(3000);
      // use ArgbEvaluator to tell ObjectAnimator how to find values between the start (blue color)
      // and end (orange color)
      sunsetSkyAnimator.setEvaluator(new ArgbEvaluator());
      // animator for the sky going from orange to dark
      ObjectAnimator nightSkyAnimator = ObjectAnimator.ofInt(mSkyView, "backgroundColor", mSunsetSkyColor, mNightSkyColor).setDuration(1500);
      // see setEvaluator() above
      nightSkyAnimator.setEvaluator(new ArgbEvaluator());
      // AnimatorSet is a set of animations that can be played together.
      AnimatorSet animatorSet = new AnimatorSet();
      // play heightAnimator with sunsetSkyAnimator; also play heightAnimator before nightSkyAnimator
      // which means the nightSkyAnimator won't start until the other two (played in sync) finish.
      animatorSet.play(heightAnimator).with(sunsetSkyAnimator).before(nightSkyAnimator);
      animatorSet.start();
   }
}
