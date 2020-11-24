package com.see.mvvm.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.alibaba.android.arouter.launcher.ARouter

/**
 * @author by XiaoSe on 2020/11/18.
 */
class NavFactory : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return if (className.startsWith("/")) {
            ARouter.getInstance().build(className).navigation() as Fragment
        } else {
            super.instantiate(classLoader, className)
        }
    }
}