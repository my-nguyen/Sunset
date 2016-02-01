package com.bignerdranch.android.sunset;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by My on 2/1/2016.
 */
public class SunsetFragment extends Fragment {
   private View   mSceneView;
   private View   mSunView;
   private View   mSkyView;

   public static SunsetFragment newInstance() {
      return new SunsetFragment();
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_sunset, container, false);
      mSceneView = view;
      mSunView = view.findViewById(R.id.sun);
      mSkyView = view.findViewById(R.id.sky);
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
      // start the animator
      heightAnimator.start();
   }
}
