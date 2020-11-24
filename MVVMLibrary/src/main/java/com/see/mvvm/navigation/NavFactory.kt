package com.see.mvvm.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory

/**
 * @author by wuxiang@tinglibao.com.cn on 2020/11/18.
 */
class NavFactory : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return super.instantiate(classLoader, className)
    }
}