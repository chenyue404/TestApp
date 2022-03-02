package com.cy.testapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by cy on 2022/1/13.
 */
object FloatWindowVM : ViewModel() {
    val showLiveData = MutableLiveData(false)
}