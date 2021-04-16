package com.see.mvvm.widget

import android.view.View

/**
 * @author by wuxiang@tinglibao.com.cn on 2020/12/9.
 */
abstract class AvoidQuickClick : View.OnClickListener {
    private val viewTimes = mutableMapOf<Int, Long>()
    fun doClick(v: View) {
        val curClickTime = System.currentTimeMillis()
        val lastClickTime = viewTimes[v.id] ?: 0
        if (curClickTime - lastClickTime >= 400) {
            viewTimes[v.id] = curClickTime
            onClick(v)
        }
    }
}