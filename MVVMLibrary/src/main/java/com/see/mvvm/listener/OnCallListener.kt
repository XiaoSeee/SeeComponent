package com.see.mvvm.listener

import android.os.Bundle

/**
 * @author by wuxiang@tinglibao.com.cn on 2021/1/27.
 */
interface OnCallListener {
    fun callOnActivity(what: Int, args: Bundle)
}