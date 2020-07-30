package com.cy.testapp.Activity

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import com.cy.testapp.R
import com.zackratos.ultimatebarx.library.UltimateBarX
import kotlinx.android.synthetic.main.activity_dialog.*

class DialogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)
//
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        window.setGravity(Gravity.BOTTOM)

        UltimateBarX.create(UltimateBarX.STATUS_BAR)
            .transparent()
            .apply(this)

        cl_root.bind(v_content)
        cl_root.setDragListener(object :
            DragConstraintLayout.DragListener {
            override fun onDragFinished() {
//                doAnimation()
            }

            override fun onDragChange(scale: Float) {
                v_bg.setBackgroundColor(
                    cl_root.changeAlpha(
                        0xff000000.toInt(),
                        (0.8 - scale).toFloat()
                    )
                )
                if (scale >= 1) {
                    finish()
                }
            }
        })

        v_content.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            Log.e("v_content", "scrollY: " + scrollY)
            cl_root.setCanDragDown(scrollY == 0)
        }

        cl_root.post {
            v_content.startAnimation(
                AnimationUtils.loadAnimation(
                    this,
                    R.anim.profile_bottom_in
                )
            )
            v_bg.animate()
                .alpha(0.8f)
                .start()
        }
    }

    override fun onPause() {
        overridePendingTransition(0, 0)
        super.onPause()
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        doAnimation()
    }

    private fun doAnimation() {
        val v_content_anim =
            ObjectAnimator.ofFloat(v_content, "y", v_content.y, v_content.y + cl_root.height)
        val v_bg_anim =
            ObjectAnimator.ofFloat(v_bg, "alpha", v_bg.alpha, 0f)
        AnimatorSet().apply {
            playTogether(v_content_anim, v_bg_anim)
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    this@DialogActivity.finish()
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                }
            })
            start()
        }

    }
}