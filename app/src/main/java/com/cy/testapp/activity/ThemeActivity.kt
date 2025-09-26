package com.cy.testapp.activity

import android.widget.Button
import com.chenyue404.androidlib.extends.bind
import com.chenyue404.androidlib.extends.click
import com.chenyue404.androidlib.widget.BaseActivity
import com.cy.testapp.R

/**
 * Created by chenyue on 2024/9/22 星期日.
 */
class ThemeActivity : BaseActivity() {

    private val bt0 by bind<Button>(R.id.bt0)

    override fun getContentViewResId() = R.layout.activity_theme
    override fun initView() {
        bt0.click {
        }
    }
}

/**
 * colorPrimary
 * 默认ActionBar背景色
 *
 * colorPrimaryDark
 * 默认状态栏颜色
 *
 * colorAccent
 * 控件的亮色，比如edittext的光标、聚焦时的边框；进度条的颜色；checkbox的选中
 */