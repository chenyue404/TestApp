package com.cy.testapp.Animer.core.interpolator.AndroidNative;

import android.content.Context;
import android.util.AttributeSet;

import com.cy.testapp.Animer.core.interpolator.AnInterpolator;

public class LinearInterpolator extends AnInterpolator {
    public LinearInterpolator() {
    }

    public LinearInterpolator(Context context, AttributeSet attrs) {
    }

    public float getInterpolation(float input) {
        return input;
    }

}
