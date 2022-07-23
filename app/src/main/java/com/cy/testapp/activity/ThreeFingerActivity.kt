package com.cy.testapp.activity

import android.graphics.Color
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.cy.testapp.R
import com.cy.testapp.ext.log
import kotlin.math.abs

/**
 * Created by chenyue on 2022/7/23 0023.
 */
class ThreeFingerActivity : AppCompatActivity() {
    private val THRESHOLD = 300

    private val tv01: TextView by lazy { findViewById(R.id.tv01) }
    private val vp01: ViewPager by lazy { findViewById(R.id.vp01) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_three_finger)

        vp01.adapter = object : PagerAdapter() {
            override fun getCount() = 3

            override fun isViewFromObject(view: View, `object`: Any) =
                view == `object`

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                return TextView(container.context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    textSize = 30F
                    text = "这是第${position}个"
                    setTextColor(Color.GREEN)
                    setBackgroundColor(Color.DKGRAY)
                }.apply {
                    container.addView(this)
                }
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                container.removeView(`object` as View)
            }
        }
    }

    private val pointArrayStart: Array<Pair<Float, Float>> =
        Array(3) {
            Pair(0f, 0f)
        }
    private val pointArrayEnd: Array<Pair<Float, Float>> =
        Array(3) {
            Pair(0f, 0f)
        }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val pointerCount = event.pointerCount

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                for (i in 0 until pointerCount.coerceAtMost(3)) {
                    pointArrayStart[i] = Pair(event.getX(i), event.getY(i))
                }
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                log("event.actionIndex=${event.actionIndex}")
                if (event.actionIndex < 4) {
                    pointArrayStart[event.actionIndex] =
                        Pair(event.getX(event.actionIndex), event.getY(event.actionIndex))
                }
            }
//            MotionEvent.ACTION_UP -> {
//                log("ACTION_UP, ${pointArrayStart.asList()}")
//                judgeThreePointerGesture()
//            }
            MotionEvent.ACTION_POINTER_UP -> {
                log("ACTION_POINTER_UP, ${pointArrayStart.asList()}")
                judgeThreePointerGesture()
            }
            MotionEvent.ACTION_MOVE -> {
                if (pointerCount >= 3) {
                    for (i in 0 until pointerCount.coerceAtMost(3)) {
                        pointArrayEnd[i] = Pair(event.x, event.y)
                    }
                    if (event.actionIndex < 2) return true
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    private fun judgeThreePointerGesture() {
        val emptyPair = Pair(0f, 0f)
        if (!pointArrayStart.contains(emptyPair)
            && !pointArrayEnd.contains(emptyPair)
        ) {
            // w,a,s,d
            var result = "n"
            for (i in 0 until 3) {
                val startX = pointArrayStart[i].first
                val startY = pointArrayStart[i].second
                val endX = pointArrayEnd[i].first
                val endY = pointArrayEnd[i].second

                val direction = getDirection(startX, startY, endX, endY)

                if (result != direction) {
                    if (result != "n") break
                    result = direction
                }
            }
            log("result=$result")
            val resultText = when (result) {
                "w" -> "上"
                "a" -> "左"
                "s" -> "下"
                "d" -> "右"
                else -> "无"
            }
            tv01.text = resultText
        }

        pointArrayStart.fill(emptyPair)
        pointArrayEnd.fill(emptyPair)
    }

    /**
     * w,a,s,d,n
     * 上下左右无
     */
    private fun getDirection(startX: Float, startY: Float, endX: Float, endY: Float): String {
        val chaX = endX - startX
        val chaY = endY - startY
        return when {
            abs(chaX) < THRESHOLD && abs(chaY) < THRESHOLD -> "n"
            abs(chaX) >= abs(chaY) -> {
                if (chaX > 0) "d" else "a"
            }
            else -> {
                if (chaY > 0) "s" else "w"
            }
        }
    }


    private var pointNum = 0
    private var finishing3Finger = false

    private val detector: GestureDetector by lazy {
        GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onScroll(
                e1: MotionEvent,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                if (pointNum == 3) {
                    if (finishing3Finger) return true
                }

                if (e2.x - e1.x > 100) {
                    log("onScroll右滑")
                    finishing3Finger = false
                }
                if (e2.x - e1.x < -100) {
                    log("onScroll左滑")
                    finishing3Finger = false
                }

                return super.onScroll(e1, e2, velocityX, velocityY)
            }
        })
    }
}