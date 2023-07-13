package com.cy.testapp.activity

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chenyue404.androidlib.extends.bind
import com.cy.testapp.R
import com.facebook.common.util.UriUtil
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.view.SimpleDraweeView

class FrescoActivity : AppCompatActivity() {
    private val sdv_0 by bind<SimpleDraweeView>(R.id.sdv_0)
    private val sdv_1 by bind<SimpleDraweeView>(R.id.sdv_1)
    private val sdv_2 by bind<SimpleDraweeView>(R.id.sdv_2)

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