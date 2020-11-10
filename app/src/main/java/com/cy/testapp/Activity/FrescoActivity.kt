package com.cy.testapp.Activity

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cy.testapp.R
import com.facebook.common.util.UriUtil
import com.facebook.drawee.drawable.ScalingUtils
import kotlinx.android.synthetic.main.activity_fresco.*

class FrescoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fresco)

        sdv_2.setImageURI(UriUtil.getUriForResourceId(R.drawable.main_avartar_male))

        sdv_0.aspectRatio = 2.0f
        sdv_0.setImageURI(Uri.parse("https://i.picsum.photos/id/625/200/200.jpg?hmac=oIwf4IzbglfXYZo-9VXZTHju2-ox3D-Vooeuioav_nw"))
//        fl_0.requestLayout()

        sdv_1.hierarchy.setPlaceholderImage(
            R.drawable.big,
            ScalingUtils.ScaleType.FIT_END
        )
    }
}