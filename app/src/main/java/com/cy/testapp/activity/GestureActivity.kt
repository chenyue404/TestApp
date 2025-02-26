package com.cy.testapp.activity

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.chenyue404.androidlib.extends.log
import com.cy.testapp.R
import kotlin.math.abs

/**
 * Created by chenyue on 2022/7/23 0023.
 */
class GestureActivity : AppCompatActivity() {

    private val speedThreshold = 300

    private val clRoot: ConstraintLayout by lazy { findViewById(R.id.clRoot) }
    private val vDragHome: View by lazy { findViewById(R.id.vDragHome) }
    private val vDragBottom: View by lazy { findViewById(R.id.vDragBottom) }
    private val vDragHome2: View by lazy { findViewById(R.id.vDragHome2) }
    private val vDragTop: View by lazy { findViewById(R.id.vDragTop) }
    private val vApps: FrameLayout by lazy { findViewById(R.id.vApps) }

    private var velocityY = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gesture)

        val motionArray = arrayOf(
            "ACTION_DOWN",
            "ACTION_UP",
            "ACTION_MOVE",
            "ACTION_CANCEL",
            "ACTION_OUTSIDE",
            "ACTION_POINTER_DOWN",
            "ACTION_POINTER_UP",
            "ACTION_HOVER_MOVE",
            "ACTION_SCROLL",
            "ACTION_HOVER_ENTER",
            "ACTION_HOVER_EXIT",
            "ACTION_BUTTON_PRESS",
            "ACTION_BUTTON_RELEASE",
        )

        val dragToTopOnTouchListener = object : View.OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
//                log(motionArray.getOrNull(event.actionMasked).toString())
                mGestureDetector.onTouchEvent(event)
                if (event.actionMasked == MotionEvent.ACTION_UP
                    && vApps.translationY != -vApps.height.toFloat()
                ) {
                    if (velocityY < -speedThreshold) {
                        log("触发飞出")
                        var duration =
                            (vApps.height - (-vApps.translationY)) / abs(velocityY / 300)
                        log("duration=$duration")
                        duration = duration
                            .coerceAtLeast(100f)
                            .coerceAtMost(500f)

                        vApps.animate()
                            .translationY((-vApps.height).toFloat())
                            .setDuration(duration.toLong())
                            .start()
                    } else {
                        log("触发自动归位")
                        log("vApps.translationY=${vApps.translationY}, vApps.height=${vApps.height}")
                        vApps.animate()
                            .translationY(
                                if (abs(vApps.translationY) > vApps.height / 2)
                                    -vApps.height.toFloat()
                                else
                                    0f
                            )
                            .setInterpolator(AccelerateDecelerateInterpolator())
                            .start()
                    }
                }
                return v.onTouchEvent(event)
            }
        }
        vDragHome.setOnTouchListener(dragToTopOnTouchListener)
        vDragBottom.setOnTouchListener(dragToTopOnTouchListener)

        vDragHome.setOnClickListener {
            log("vDragHome click， vApps.isVisible=${vApps.isVisible}")
//            vApps.animate()
//                .translationY(
//                    if (vApps.translationY == 0f) -vApps.height.toFloat()
//                    else 0f
//                )
//                .start()
            ObjectAnimator.ofFloat(
                vApps, "translationY",
                if (vApps.translationY == 0f) -vApps.height.toFloat()
                else 0f
            ).start()
        }
        vDragBottom.setOnClickListener { }
        vDragHome2.setOnClickListener {
            log(
                "vApps.translationY=${vApps.translationY}, " +
                        "vApps.height=${vApps.height}, " +
                        "vApps.y=${vApps.y}, "
            )
        }
        vDragTop.setOnClickListener {
            log("vDragTop click")
        }
        vDragTop.setOnTouchListener { v, event ->
            topGestureDetector.onTouchEvent(event)
            if (event.actionMasked == MotionEvent.ACTION_UP) {
                if (velocityY > speedThreshold) {
                    log("触发飞出")
                    var duration = (vApps.height - (-vApps.translationY)) / abs(velocityY / 300)
                    log("duration=$duration")
                    duration = duration
                        .coerceAtLeast(100f)
                        .coerceAtMost(500f)
                    vApps.animate()
                        .translationY(0f)
                        .setDuration(duration.toLong())
                        .start()
                } else {
                    log("触发自动归位")
                    log("vApps.translationY=${vApps.translationY}, velocityY=$velocityY")
                    vApps.animate()
                        .translationY(
                            if (abs(vApps.translationY) > vApps.height / 2)
                                -vApps.height.toFloat()
                            else
                                0f
                        )
                        .setInterpolator(AccelerateDecelerateInterpolator())
                        .start()
                }
            }
            if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                return@setOnTouchListener true
            }
            super.onTouchEvent(event)
        }
    }

    private val mGestureDetector: GestureDetector by lazy {
        GestureDetector(this,
            object : GestureDetector.SimpleOnGestureListener() {
                override fun onScroll(
                    e1: MotionEvent?,
                    e2: MotionEvent,
                    distanceX: Float,
                    distanceY: Float
                ): Boolean {
//                    log("onScroll, e1=(${e1.rawX},${e1.rawY}), e2=(${e2.rawX},${e2.rawY})")
//                    log("vApps.translationY=${vApps.translationY}, vApps.height=${vApps.height}")
//                    return super.onScroll(e1, e2, distanceX, distanceY)

                    if (vApps.translationY == -vApps.height.toFloat()
                        || e2.rawY >= (e1?.rawY ?: 0f)
                    ) {
                        return super.onScroll(e1, e2, distanceX, distanceY)
                    }

                    val scrollY = e2.y - (e1?.y ?: 0f)
                    val chaRawY = e2.rawY - (e1?.rawY ?: 0f)
                    log("scrollY=$scrollY, chaRawY=$chaRawY")
                    vApps.translationY = scrollY
                        .coerceAtMost(0f)
                        .coerceAtLeast((-vApps.height).toFloat())
                    return false
                }

                override fun onFling(
                    e1: MotionEvent?,
                    e2: MotionEvent,
                    velocityX: Float,
                    velocityY: Float
                ): Boolean {
                    log(
                        "onFling, e1=(${e1?.rawX},${e1?.rawY}), e2=(${e2.rawX},${e2.rawY}), " +
                                "velocityX=$velocityX, velocityY=$velocityY"
                    )
                    this@GestureActivity.velocityY = velocityY
                    return super.onFling(e1, e2, velocityX, velocityY)
                }

                override fun onDown(e: MotionEvent): Boolean {
                    log("onDown, e=(${e.rawX},${e.rawY})")
                    velocityY = 0f
                    return super.onDown(e)
                }
            })
    }

    private val topGestureDetector: GestureDetector by lazy {
        GestureDetector(this@GestureActivity,
            object : GestureDetector.SimpleOnGestureListener() {
                override fun onScroll(
                    e1: MotionEvent?,
                    e2: MotionEvent,
                    distanceX: Float,
                    distanceY: Float
                ): Boolean {
                    val chaY = e2.rawY - (e1?.rawY ?: 0f)
                    log("chaY=$chaY")
                    vApps.translationY = (-vApps.height.toFloat() + chaY)
                        .coerceAtMost(0f)
                        .coerceAtLeast((-vApps.height).toFloat())
                    return super.onScroll(e1, e2, distanceX, distanceY)
                }

                override fun onFling(
                    e1: MotionEvent?,
                    e2: MotionEvent,
                    velocityX: Float,
                    velocityY: Float
                ): Boolean {
                    this@GestureActivity.velocityY = velocityY
                    return super.onFling(e1, e2, velocityX, velocityY)
                }
            })
    }
}