package com.cy.testapp.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chenyue404.androidlib.extends.bind
import com.chenyue404.androidlib.extends.log
import com.cy.testapp.FloatWindowService
import com.cy.testapp.FloatWindowVM
import com.cy.testapp.R

/**
 * Created by cy on 2022/1/13.
 */
class FloatWindowActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_FLOAT_CODE = 1001
    }

    private val bt0 by bind<Button>(R.id.bt0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startService(Intent(this, FloatWindowService::class.java))
        setContentView(R.layout.activity_float_window)
        bt0.setOnClickListener {
            checkSuspendedWindowPermission(this) {
                log("123")
                FloatWindowVM.showLiveData.value = !FloatWindowVM.showLiveData.value!!
            }
        }
    }

    /**
     * 检查悬浮窗权限是否开启
     */
    private fun checkSuspendedWindowPermission(context: Activity, block: () -> Unit) {
        if (commonROMPermissionCheck(context)) {
            block()
        } else {
            Toast.makeText(context, "请开启悬浮窗权限", Toast.LENGTH_SHORT).show()
            context.startActivityForResult(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION).apply {
                data = Uri.parse("package:${context.packageName}")
            }, REQUEST_FLOAT_CODE)
        }
    }

    /**
     * 判断悬浮窗权限权限
     */
    private fun commonROMPermissionCheck(context: Context?): Boolean {
        var result = true
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                result =
                    Settings::class.java.getDeclaredMethod("canDrawOverlays", Context::class.java)
                        .invoke(null, context) as Boolean
            } catch (e: Exception) {
                Log.e("ServiceUtils", Log.getStackTraceString(e))
            }
        }
        return result
    }
}