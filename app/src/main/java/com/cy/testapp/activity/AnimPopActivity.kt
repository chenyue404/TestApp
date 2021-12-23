package com.cy.testapp.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.cy.testapp.R

class AnimPopActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anim_pop)

        findViewById<Button>(R.id.bt_0).setOnClickListener {
            startActivity(Intent(this, DialogActivity::class.java))
        }
    }
}
