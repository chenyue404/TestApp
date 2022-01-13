package com.cy.testapp.activity

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.cy.testapp.R
import com.cy.testapp.ext.bind

class ScrollActivity : AppCompatActivity() {
    private val v_top by bind<View>(R.id.v_top)
    private val fl_top by bind<LinearLayout>(R.id.fl_top)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scroll)
        v_top.setOnTouchListener { v, event ->
            fl_top.dispatchTouchEvent(event)
        }
    }
}