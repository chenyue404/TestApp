package com.cy.testapp.ext

import android.util.Log

/**
 * Created by cy on 2022/1/7.
 */
fun Any.log(msg: String, tag: String = "") {
    val finalTag = if (tag.isNotEmpty()) tag
    else outerClassSimpleNameInternalOnlyDoNotUseKThxBye()
    Log.d(finalTag, msg)
}

private fun Any.outerClassSimpleNameInternalOnlyDoNotUseKThxBye(): String {
    val javaClass = this::class.java
    val fullClassName = javaClass.name
    val outerClassName = fullClassName.substringBefore('$')
    val simplerOuterClassName = outerClassName.substringAfterLast('.')
    return if (simplerOuterClassName.isEmpty()) {
        fullClassName
    } else {
        simplerOuterClassName.removeSuffix("Kt")
    }
}