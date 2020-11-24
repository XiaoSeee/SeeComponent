package com.see.component

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter

/**
 * @author by wuxiang@tinglibao.com.cn on 2020/11/18.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        ARouter.openLog()
        ARouter.openDebug()
        ARouter.init(this)
    }
}