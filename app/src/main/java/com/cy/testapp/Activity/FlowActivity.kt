package com.cy.testapp.Activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.cy.testapp.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.launch

/**
 * Created by cy on 2021/11/5.
 */
class FlowActivity : AppCompatActivity() {
    companion object {
        private val TAG = this.javaClass.name
    }

    private val sharedFlow: MutableSharedFlow<String> = MutableSharedFlow()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flow)

        lifecycleScope.launch {
            for (i in 1..10) {
                sharedFlow.emit(i.toString())
                delay(200)
            }
        }
        lifecycleScope.launch {
            sharedFlow.collect {
                Log.d(TAG, "sharedFlow1: $it")
            }
        }
        lifecycleScope.launch {
            sharedFlow.conflate().collect {
                Log.d(TAG, "sharedFlow2: $it")
            }
        }
    }
}