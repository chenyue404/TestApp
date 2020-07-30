package com.cy.testapp.Activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cy.testapp.R
import kotlinx.android.synthetic.main.activity_anim_pop.*

class AnimPopActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anim_pop)

        bt_0.setOnClickListener {
            startActivity(Intent(this, DialogActivity::class.java))
        }
    }
}
