package com.cy.testapp

import android.graphics.PixelFormat
import android.os.Build
import android.util.DisplayMetrics
import android.view.GestureDetector
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.view.GestureDetectorCompat
import androidx.lifecycle.LifecycleService
import com.chenyue404.androidlib.extends.log

/**
 * Created by cy on 2022/1/13.
 */
class FloatWindowService : LifecycleService() {

    private val windowManager by lazy {
        (getSystemService(WINDOW_SERVICE) as WindowManager)
    }
    private val windowView by lazy {
        LayoutInflater.from(this).inflate(R.layout.view_float_window, null).apply {
            var x = 0f
            var y = 0f
            val gestureDetectorCompat =
                GestureDetectorCompat(context, object : GestureDetector.OnGestureListener {
                    override fun onDown(e: MotionEvent) = false

                    override fun onShowPress(e: MotionEvent) {
                    }

                    override fun onSingleTapUp(e: MotionEvent): Boolean {
                        Toast.makeText(context, "onSingleTapUp", Toast.LENGTH_SHORT).show()
                        return true
                    }

                    override fun onScroll(
                        e1: MotionEvent?,
                        e2: MotionEvent,
                        distanceX: Float,
                        distanceY: Float
                    ) = false

                    override fun onLongPress(e: MotionEvent) {
                    }

                    override fun onFling(
                        e1: MotionEvent?,
                        e2: MotionEvent,
                        velocityX: Float,
                        velocityY: Float
                    ) = false
                })
            setOnTouchListener { v, event ->
                if (gestureDetectorCompat.onTouchEvent(event)) return@setOnTouchListener true
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        x = event.rawX
                        y = event.rawY
                        log("ACTION_DOWN-x=$x-y=$y")
                    }
                    MotionEvent.ACTION_MOVE -> {
                        val nowX = event.rawX
                        val nowY = event.rawY
                        val movedX = nowX - x
                        val movedY = nowY - y
                        x = nowX
                        y = nowY
                        log("ACTION_MOVE-x=$x-y=$y")
                        windowManager.updateViewLayout(
                            this,
                            (layoutParams as WindowManager.LayoutParams).apply {
                                this.x += movedX.toInt()
                                this.y += movedY.toInt()
                            })
                    }
                    MotionEvent.ACTION_UP -> {
                        val nowX = event.rawX
                        val nowY = event.rawY
                        x = nowX
                        y = nowY
                        log("ACTION_UP-x=$x-y=$y")
                    }
                }
                false
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        FloatWindowVM.showLiveData.observe(this) { show ->
            if (show) {
                val outMetrics = DisplayMetrics()
                windowManager.defaultDisplay.getMetrics(outMetrics)
                val layoutParam = WindowManager.LayoutParams().apply {
                    type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                    } else {
                        WindowManager.LayoutParams.TYPE_PHONE
                    }
                    format = PixelFormat.RGBA_8888
                    flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    //位置大小设置
                    width = ViewGroup.LayoutParams.WRAP_CONTENT
                    height = ViewGroup.LayoutParams.WRAP_CONTENT
                    gravity = Gravity.START or Gravity.TOP
                    //设置剧中屏幕显示
                    x = outMetrics.widthPixels / 2 - width / 2
                    y = outMetrics.heightPixels / 2 - height / 2
//                    x = 0
//                    y = 0
                }
                // 将悬浮窗控件添加到WindowManager
                windowManager.addView(windowView, layoutParam)
            } else {
                windowView?.windowToken?.run {
                    windowManager.removeView(windowView)
                }
            }
        }
    }
}