package com.see.mvvm.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.navigation.fragment.NavHostFragment
import com.alibaba.android.arouter.launcher.ARouter

/**
 * @author by XiaoSe on 2020/11/18.
 */
class NavFactory(private val graphResId: Int, private val startDestinationArgs: Bundle? = null) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when {
            className.startsWith("/") -> {
                ARouter.getInstance().build(className).navigation() as Fragment
            }
            className == "NavHostFragment" -> {
                NavHostFragment.create(graphResId, startDestinationArgs)
            }
            else -> {
                super.instantiate(classLoader, className)
            }
        }
    }
}