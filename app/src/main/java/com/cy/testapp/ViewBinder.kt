package com.cy.testapp

import android.app.Activity
import android.app.Dialog
import android.view.View

/**
 * Kotlin扩展方法
 */

fun <V : View> Activity.bind(id: Int): Lazy<V> = lazy {
    viewFinder(id) as V
}

fun <V : View> View.bind(id: Int): Lazy<V> = lazy {
    viewFinder(id) as V
}

fun <V : View> Dialog.bind(id: Int): Lazy<V> = lazy {
    viewFinder(id) as V
}

private val Activity.viewFinder: Activity.(Int) -> View
    get() = { findViewById(it) }

private val View.viewFinder: View.(Int) -> View
    get() = { findViewById(it) }

private val Dialog.viewFinder: Dialog.(Int) -> View
    get() = { findViewById(it) }