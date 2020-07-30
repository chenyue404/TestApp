package com.cy.testapp.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cy.testapp.R
import kotlinx.android.synthetic.main.activity_scroll.*

class ScrollActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scroll)
        v_top.setOnTouchListener { v, event ->
            fl_top.dispatchTouchEvent(event)
        }
    }
}