package com.cy.testapp.activity

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.chenyue404.androidlib.extends.bind
import com.chenyue404.androidlib.extends.click
import com.cy.testapp.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by cy on 2021/11/5.
 */
class FlowActivity : AppCompatActivity() {
    companion object {
        private val TAG = this.javaClass.name
    }

    private val tv0 by bind<TextView>(R.id.tv0)
    private val bt0 by bind<Button>(R.id.bt0)
    private val bt1 by bind<Button>(R.id.bt1)

    private val sharedFlow: MutableSharedFlow<String> = MutableSharedFlow()
    private var testFlow: MutableStateFlow<Int>? = MutableStateFlow(111)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flow)

//        lifecycleScope.launch {
//            for (i in 1..10) {
//                sharedFlow.emit(i.toString())
//                delay(200)
//            }
//        }
//        lifecycleScope.launch {
//            sharedFlow.collect {
//                Log.d(TAG, "sharedFlow1: $it")
//            }
//        }
//        lifecycleScope.launch {
//            sharedFlow.conflate().collect {
//                Log.d(TAG, "sharedFlow2: $it")
//            }
//        }

        lifecycleScope.launch {
            for (i in 1..500) {
                testFlow?.emit(i)
                delay(1000)
            }
        }
        lifecycleScope.launch {
            testFlow?.collectLatest {
                tv0.text = "$it"
            }
        }
        bt0.click { testFlow = null }
        bt1.click {
            testFlow = MutableStateFlow(222)
        }
    }
}