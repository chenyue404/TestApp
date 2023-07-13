package com.cy.testapp

import android.app.Application
import android.content.Context
import com.facebook.drawee.backends.pipeline.Fresco
import me.weishu.reflection.Reflection

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        Fresco.initialize(this)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        Reflection.unseal(base)
    }
}