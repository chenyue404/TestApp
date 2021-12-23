package com.cy.testapp.animer.core.interpolator.AndroidNative;

import android.content.Context;
import android.util.AttributeSet;

import com.cy.testapp.animer.core.interpolator.AnInterpolator;

public class AccelerateDecelerateInterpolator extends AnInterpolator {

    public AccelerateDecelerateInterpolator() {
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public AccelerateDecelerateInterpolator(Context context, AttributeSet attrs) {
    }

    public float getInterpolation(float input) {
        return (float) (Math.cos((input + 1) * Math.PI) / 2.0f) + 0.5f;
    }


}
