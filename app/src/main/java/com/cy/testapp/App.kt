package com.cy.testapp

import android.app.Application
import android.content.Context
import android.util.Log
import coil.Coil
import coil.ImageLoader
import coil.util.DebugLogger
import com.facebook.drawee.backends.pipeline.Fresco
import me.weishu.reflection.Reflection

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        Fresco.initialize(this)
        Coil.setImageLoader(
            ImageLoader.Builder(this)
                .logger(DebugLogger(Log.VERBOSE))
                .build()
        )
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        Reflection.unseal(base)
    }
}