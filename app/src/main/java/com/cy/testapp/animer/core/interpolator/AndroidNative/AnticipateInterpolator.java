package com.cy.testapp.animer.core.interpolator.AndroidNative;

import com.cy.testapp.animer.core.interpolator.AnInterpolator;

public class AnticipateInterpolator extends AnInterpolator {

    private float mTension;

    public AnticipateInterpolator() {
        mTension = 2.0f;
        initArgData(0, 2, "factor", 0, 10);
    }

    /**
     * @param tension Amount of anticipation. When tension equals 0.0f, there is
     *                no anticipation and the interpolator becomes a simple
     *                acceleration interpolator.
     */
    public AnticipateInterpolator(float tension) {
        mTension = tension;
        initArgData(0, tension, "factor", 0, 10);
    }

    public float getInterpolation(float t) {
        // a(t) = t * t * ((tension + 1) * t - tension)
        return t * t * ((mTension + 1) * t - mTension);
    }

    @Override
    public void resetArgValue(int i, float value) {
        setArgValue(i, value);
        if (i == 0) {
            mTension = value;
        }
    }

}
