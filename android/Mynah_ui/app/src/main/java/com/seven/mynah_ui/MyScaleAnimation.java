package com.seven.mynah_ui;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class MyScaleAnimation extends Animation {

    private View mView;
    private float mToHeight;
    private float mFromHeight;
    private float mToWidth;
    private float mFromWidth;

    public MyScaleAnimation(View v, float fromWidth, float fromHeight, float toWidth, float toHeight) {
        mToHeight = toHeight;
        mToWidth = toWidth;
        mFromHeight = fromHeight;
        mFromWidth = fromWidth;
        mView = v;
        setDuration(400);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        float height =
                (mToHeight - mFromHeight) * interpolatedTime + mFromHeight;
        float width = (mToWidth - mFromWidth) * interpolatedTime + mFromWidth;
        ViewGroup.LayoutParams p = mView.getLayoutParams();
        p.height = (int) height;
        p.width = (int) width;
        mView.requestLayout();
    }
}