package com.cy.testapp.activity

import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import com.chenyue404.androidlib.extends.bind
import com.chenyue404.androidlib.extends.click
import com.chenyue404.androidlib.extends.log
import com.chenyue404.androidlib.widget.BaseActivity
import com.cy.testapp.R

class RotationAnimActivity : BaseActivity() {
    private val iv0 by bind<ImageView>(R.id.iv0)
    private val vPoint by bind<View>(R.id.vPoint)
    private val sbX by bind<SeekBar>(R.id.sbX)
    private val sbY by bind<SeekBar>(R.id.sbY)
    private val btX by bind<Button>(R.id.btX)
    private val btY by bind<Button>(R.id.btY)
    private val btZ by bind<Button>(R.id.btZ)
    private val btToggle by bind<Button>(R.id.btToggle)
    private val btReset by bind<Button>(R.id.btReset)
    private val tvX by bind<TextView>(R.id.tvX)
    private val tvY by bind<TextView>(R.id.tvY)
    override fun getContentViewResId() = R.layout.activity_rotation_anim
    private val animate
        get() = iv0.animate()
            .setDuration(1500)
            .setInterpolator(LinearInterpolator())

    override fun initView() {
        listenSeekbar(sbX, true)
        listenSeekbar(sbY, false)
        iv0.post {
            sbX.progress = (iv0.pivotX * 100 / iv0.width).toInt()
            sbY.progress = (iv0.pivotY * 100 / iv0.height).toInt()
        }
        btX.click {
            animate
                .rotationXBy(360f)
                .start()
        }
        btY.click {
            animate
                .rotationYBy(360f)
                .start()
        }
        btZ.click {
            animate
                .rotationBy(360f)
                .start()
        }
        btReset.click {
            iv0.apply {
                animate.cancel()
                rotation = 0f
                rotationX = 0f
                rotationY = 0f
                invalidate()
            }
        }
    }

    private fun listenSeekbar(sb: SeekBar, isX: Boolean) {
        sb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                val f = (progress * iv0.width / 100)
                    .coerceAtLeast(0)
                    .coerceAtMost(iv0.width)
                    .toFloat()
                log("${if (isX) "X" else "Y"}.f = $f")
                if (isX) {
                    iv0.pivotX = f
                    vPoint.translationX = f
                    tvX.text = progress.toString()
                } else {
                    iv0.pivotY = f
                    vPoint.translationY = f
                    tvY.text = progress.toString()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }
}