package com.cy.testapp.widget

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.constraintlayout.motion.widget.MotionLayout

/**
 * Created by chenyue on 2022/7/22 0022.
 */
class ClickableMotionLayout2 @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : MotionLayout(context, attrs) {
    private var isLongPressing = false
    private var compatGestureDetector: GestureDetector? = null
    var gestureListener: GestureDetector.SimpleOnGestureListener? = null

    init {
        setupGestureListener()
        setOnTouchListener { v, event ->
            if (isLongPressing && event.action == MotionEvent.ACTION_UP) {
                isPressed = false
                isLongPressing = false
                v.performClick()
            } else {
                isPressed = false
                isLongPressing = false
                compatGestureDetector?.onTouchEvent(event) ?: false
            }
        }
    }

    private fun setupGestureListener() {
        gestureListener = object : GestureDetector.SimpleOnGestureListener() {
            override fun onLongPress(e: MotionEvent) {
                isPressed = progress == 0f
                isLongPressing = progress == 0f
            }

            override fun onSingleTapUp(e: MotionEvent): Boolean {
                isPressed = true
                performClick()
                return true
            }
        }
        gestureListener?.let {
            compatGestureDetector = GestureDetector(context, it)
        }
    }
}