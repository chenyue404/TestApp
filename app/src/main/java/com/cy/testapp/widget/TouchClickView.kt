package com.cy.testapp.widget

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.chenyue404.androidlib.ContextProvider.Companion.mContext
import com.chenyue404.androidlib.extends.log

/**
 * Created by chenyue on 2022/7/22 0022.
 */
class TouchClickView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val onGestureListener: GestureDetector.OnGestureListener by lazy {
        object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
//                return super.onSingleTapUp(e)
                val singleTapUp = super.onSingleTapUp(e)
                log("onSingleTapUp=$singleTapUp")
                onClick()
                return singleTapUp
            }

            override fun onDown(e: MotionEvent): Boolean {
                log("onDown")
//                return super.onDown(e)
                return true
            }

            override fun onShowPress(e: MotionEvent) {
                log("onShowPress")
                super.onShowPress(e)
            }

            override fun onScroll(
                e1: MotionEvent,
                e2: MotionEvent,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                val onScroll = super.onScroll(e1, e2, distanceX, distanceY)
                log("onScroll=$onScroll")
                return true
            }

            override fun onLongPress(e: MotionEvent) {
                log("onLongPress")
                super.onLongPress(e)
            }

            override fun onFling(
                e1: MotionEvent,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                val onFling = super.onFling(e1, e2, velocityX, velocityY)
                log("onFling=$onFling")
                return true
            }

            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                log("onSingleTapConfirmed")
                return super.onSingleTapConfirmed(e)
            }

            override fun onDoubleTap(e: MotionEvent): Boolean {
                log("onDoubleTap")
                return super.onDoubleTap(e)
            }

            override fun onDoubleTapEvent(e: MotionEvent): Boolean {
                log("onDoubleTapEvent")
                return super.onDoubleTapEvent(e)
            }

            override fun onContextClick(e: MotionEvent): Boolean {
                log("onContextClick")
                return super.onContextClick(e)
            }
        }.apply {
            isLongClickable = false
        }
    }

    private val mGestureDetector: GestureDetector by lazy {
        GestureDetector(mContext, onGestureListener.apply {
            isLongClickable = false
        })
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
//        return super.onTouchEvent(event)
        return mGestureDetector.onTouchEvent(event)
    }

    init {
//        isClickable = true
//        isLongClickable = false
    }

    var onClick: () -> Unit = {}
}