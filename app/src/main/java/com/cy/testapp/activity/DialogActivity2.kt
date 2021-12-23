package com.cy.testapp.activity

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import com.cy.testapp.R
import com.cy.testapp.bind


class DialogActivity2 : AppCompatActivity() {
    private val cl_root by bind<DragConstraintLayout>(R.id.cl_root)
    private val v_content by bind<NestedScrollView>(R.id.v_content)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog2)

        window.attributes.width = WindowManager.LayoutParams.MATCH_PARENT
        window.attributes.height = getScreenHeight(this) - getNavigationBarHeight(this)
        cl_root.bind(v_content)
        cl_root.setDragListener(object :
            DragConstraintLayout.DragListener {
            override fun onDragFinished() {
                finish()
            }

            override fun onDragChange(scale: Float) {
//                window.setBackgroundDrawable(
//                    ColorDrawable(
//                        cl_root.changeAlpha(
//                            0xff000000.toInt(),
//                            (0.8 - scale).toFloat()
//                        )
//                    )
//                )
//                if (scale >= 1) {
//                    finish()
//                }
            }

        })
        v_content.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            Log.e("v_content", "scrollY: " + scrollY)
            cl_root.setCanDragDown(scrollY == 0)
        }
    }

    private fun getScreenHeight(activity: Activity): Int {
        val displaymetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displaymetrics)
        return displaymetrics.heightPixels
    }

    private fun getStatusBarHeight(context: Context): Int {
        var statusBarHeight = 0
        val res: Resources = context.getResources()
        val resourceId: Int = res.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId)
        }
        return statusBarHeight
    }

    fun getNavigationBarHeight(context: Context): Int {
        val resourceId =
            context.resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) context.resources.getDimensionPixelSize(resourceId) else 0
    }
}