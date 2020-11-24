package com.see.component.nav

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.alibaba.android.arouter.launcher.ARouter

/**
 * @author by wuxiang@tinglibao.com.cn on 2020/11/18.
 */
class NavFactory : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        Log.d("NavFactory", "className : $className")
        return if (className.startsWith("/")) {
            ARouter.getInstance().build(className).navigation() as Fragment
        } else {
            super.instantiate(classLoader, className)
        }
    }
}