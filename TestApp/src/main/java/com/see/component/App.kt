package com.see.component

import com.alibaba.android.arouter.launcher.ARouter
import com.see.mvvm.viewmodel.SeeApplication

/**
 * @author by wuxiang@tinglibao.com.cn on 2020/11/18.
 */
class App : SeeApplication() {
    override fun onCreate() {
        super.onCreate()

        ARouter.openLog()
        ARouter.openDebug()
        ARouter.init(this)
    }
}