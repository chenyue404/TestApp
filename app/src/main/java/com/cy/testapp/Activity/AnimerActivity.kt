package com.cy.testapp.Activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.cy.testapp.Animer.Animer
import com.cy.testapp.R

class AnimerActivity : AppCompatActivity() {

    private lateinit var vGreen: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animer)

        val vGreen: View = findViewById(R.id.vGreen)

        // 创建一个 Animer 解算器对象，采用了原生的 DynamicAnimation 动画器
        val solver = Animer.springiOSUIView(0.5f, 3f)
        val animer = Animer<View, View>(vGreen, solver, Animer.TRANSLATION_Y)
        animer.setUpdateListener { value, velocity, progress ->
            Log.e("animer", "value: $value, velocity: $velocity, progress: $progress")
            vGreen.translationY = value
        }
        animer.setCurrentValue(0f)
        vGreen.setOnClickListener { animer.setEndValue(1000f) }
    }
}