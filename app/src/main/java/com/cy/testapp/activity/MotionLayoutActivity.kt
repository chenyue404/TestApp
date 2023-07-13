package com.cy.testapp.activity

import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.chenyue404.androidlib.extends.log
import com.cy.testapp.R
import com.cy.testapp.widget.ClickableMotionLayout

/**
 * Created by chenyue on 2022/7/21 0021.
 */
class MotionLayoutActivity : AppCompatActivity() {

    private val mlRoot: ClickableMotionLayout by lazy { findViewById(R.id.mlRoot) }
    private val vDragHome: View by lazy { findViewById(R.id.vDragHome) }
    private val vDragHome2: View by lazy { findViewById(R.id.vDragHome2) }
    private val vDragTop: View by lazy { findViewById(R.id.vDragTop) }
    private val vApps: View by lazy { findViewById(R.id.vApps) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motion_layout)


        mlRoot.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {
                log("onTransitionStarted, startId=${getSetName(startId)}, endId=${getSetName(endId)}")
            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
                if (progress == 0f || progress == 1f) {
                    log(
                        "onTransitionChange, progress=$progress, " +
                                "startId=${getSetName(startId)}, " +
                                "endId=${getSetName(endId)}"
                    )
                }
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                log("onTransitionCompleted, currentId=${getSetName(currentId)}")
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {
                log("onTransitionTrigger, positive=$positive, progress=$progress")
            }
        })

        mlRoot.addClickView(vDragHome) {
            log("vDragHome click")
            when (mlRoot.currentState) {
                R.id.set_apps_hide -> {
                    mlRoot.transitionToState(R.id.set_apps_show)
                }
                R.id.set_apps_show -> {
                    mlRoot.transitionToState(R.id.set_apps_hide)
                }
                R.id.set_detail_show -> {
                    mlRoot.transitionToState(R.id.set_detail_hide)
                }
            }
        }
        mlRoot.addClickView(vDragTop) {
            mlRoot.transitionToState(R.id.set_apps_hide)
        }
        vDragHome2.setOnClickListener {
//            mlRoot.transitionToState(R.id.set_detail_show)
            ConstraintSet().apply {
                clone(mlRoot)
                clear(R.id.vAppDetail, ConstraintSet.START)
                connect(
                    R.id.vAppDetail,
                    ConstraintSet.START,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.START,
                    500
                )
                TransitionManager.beginDelayedTransition(mlRoot)
                applyTo(mlRoot)
            }
        }
    }

    private fun getSetName(id: Int) = when (id) {
        R.id.set_apps_hide -> "set_apps_hide"
        R.id.set_apps_show -> "set_apps_show"
        R.id.set_detail_show -> "set_detail_show"
        R.id.set_detail_hide -> "set_detail_hide"
        else -> "未知"
    }
}