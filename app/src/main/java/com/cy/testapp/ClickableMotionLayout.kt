package com.cy.testapp

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.constraintlayout.motion.widget.MotionLayout
import kotlin.math.abs

/**
 * Created by chenyue on 2022/7/22 0022.
 */
class ClickableMotionLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : MotionLayout(context, attrs) {
    private val moveMax = 200
    private var mStartTime: Long = 0
    private var startX = 0f
    private var startY = 0f

    private val clickViewList: HashMap<View, (() -> Unit)?> = hashMapOf()

//    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
//
//        if (event?.action == MotionEvent.ACTION_DOWN) {
//            log("ACTION_DOWN")
//            mStartTime = event.eventTime
//        }
//
//        if ((event?.eventTime?.minus(mStartTime)!! >= ViewConfiguration.getTapTimeout())
//            && event.action == MotionEvent.ACTION_MOVE
//        ) {
//            log("ACTION_MOVE")
//            return super.onInterceptTouchEvent(event)
//        }
//
//        return false
//    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                mStartTime = event.eventTime
                startX = event.x
                startY = event.y
            }
            MotionEvent.ACTION_UP -> {
                val endX = event.x
                val endY = event.y
                if (event.eventTime - mStartTime <= ViewConfiguration.getTapTimeout()
                    && abs(startX - endX) < moveMax
                    && abs(startY - endY) < moveMax
                ) {
                    for (clickView in clickViewList) {
                        if (isTouchPointInView(clickView.key, event.rawX, event.rawY)) {
                            clickView.value?.invoke()
                        }
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }

    private fun isTouchPointInView(
        targetView: View,
        xAxis: Float,
        yAxis: Float,
    ): Boolean {
        val location = IntArray(2)
        targetView.getLocationOnScreen(location)
        val left = location.first()
        val top = location.last()
        val right = left + targetView.measuredWidth
        val bottom = top + targetView.measuredHeight

        return xAxis >= left && xAxis <= right
                && yAxis >= top && yAxis <= bottom
    }

    fun addClickView(view: View, clickFun: (() -> Unit)?) {
        clickViewList[view] = clickFun
    }
}