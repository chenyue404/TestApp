package com.cy.testapp.activity

import android.app.Activity
import android.os.Bundle
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import com.cy.testapp.R
import com.cy.testapp.ext.bind

/**
 * Created by cy on 2022/3/30.
 */
class InputActivity : Activity() {

    val et0 by bind<EditText>(R.id.et0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)

        et0.doAfterTextChanged { editable ->
            editable ?: return@doAfterTextChanged
//            if (editable.isEmpty() || editable.toString() == "800") return@doAfterTextChanged
//            editable.clear()
//            editable.append("800")

//            if (editable.toString() == "12345") {
//                editable.delete(2, 3)
//            }

            if (editable.startsWith('0') && editable.length > 1 && editable[1] != '.') {
                editable.delete(0, 1)
            }
        }
    }
}