package com.cy.testapp

import android.view.animation.Interpolator

class MyInterpolator : Interpolator {
    override fun getInterpolation(input: Float): Float {
        val x: Float = 2.0f - 1.0f
        return 0.5f * (x * x * x + 1.0f)
    }
}