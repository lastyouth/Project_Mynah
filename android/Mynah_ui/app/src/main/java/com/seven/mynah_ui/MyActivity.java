package com.seven.mynah_ui;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
import android.widget.Toast;

public class MyActivity extends Activity {

    CustomLayoutSet layout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        layout = new CustomLayoutSet(this, 5);
        layout.setAnimationDuration(400);
        layout.setInterpolator(new AccelerateDecelerateInterpolator());
        setContentView(layout);
        setupLayout();

        Toast.makeText(getBaseContext(), "Press back to start demoAnimations", Toast.LENGTH_SHORT).show();

    }

    private void setupLayout() {

    }

    @Override
    public void onBackPressed() {
        //testAnimations();
    }

    private void testAnimations() {
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                layout.startAnimation(2);
            }
        }, 1000);

        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                layout.setInterpolator(new BounceInterpolator());
                layout.startAnimation(3);
            }
        }, 3000);

        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                layout.setInterpolator(new AccelerateInterpolator());
                layout.startAnimation(4);
            }
        }, 6000);

        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                layout.setInterpolator(new DecelerateInterpolator());
                layout.startAnimation(1);
            }
        }, 9000);
    }

}
