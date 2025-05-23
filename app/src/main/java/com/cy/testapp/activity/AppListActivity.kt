package com.cy.testapp.activity

import android.content.pm.PackageInfo
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.chenyue404.androidlib.extends.bind
import com.chenyue404.androidlib.extends.click
import com.chenyue404.androidlib.extends.launch
import com.chenyue404.androidlib.extends.log
import com.chenyue404.androidlib.widget.BaseActivity
import com.cy.testapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


/**
 * Created by cy on 2023/6/13.
 */
class AppListActivity : BaseActivity() {
    private val rvList: RecyclerView by bind(R.id.rv_list)
    private val bt0: Button by bind(R.id.bt0)

    private val appList = mutableListOf<PackageInfo>()


    override fun getContentViewResId() = R.layout.activity_app_list

    override fun initView() {
        lifecycle.launch {
            appList.addAll(readAppList())
            log(appList.first().toString())
        }
        bt0.click {
            val info = appList.first()
            val name = info.applicationInfo?.name
            val compileSdkVersion =
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//                    val instanceFields = HiddenApiBypass.getInstanceFields(PackageInfo::class.java)
//                    (instanceFields.find { (it as Field).name == "compileSdkVersion" } as Field?)?.get(
//                        info
//                    ) ?: 0
//                } else {
                info.javaClass.getField("compileSdkVersion").get(info) as Int
//                }
            log("$name-$compileSdkVersion")
        }
    }

    private suspend fun readAppList() = withContext(Dispatchers.IO) {
        mContext.packageManager.getInstalledPackages(0x08000000)
    }
}