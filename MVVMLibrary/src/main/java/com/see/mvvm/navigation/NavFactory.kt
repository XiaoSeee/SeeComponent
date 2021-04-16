package com.see.mvvm.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.alibaba.android.arouter.launcher.ARouter

/**
 * @author by XiaoSe on 2020/11/18.
 */
class NavFactory(
        private val hostFragment: String = "androidx.navigation.fragment.NavHostFragment",
        private val startArgs: Bundle? = null
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when {
            className == "WhichHost" -> {
                when {
                    hostFragment.startsWith("/") -> {
                        ARouter.getInstance()
                                .build(hostFragment)
                                .with(startArgs)
                                .navigation() as Fragment
                    }
                    else -> {
                        super.instantiate(classLoader, hostFragment)
                    }
                }
            }
            className.startsWith("/") -> {
                ARouter.getInstance()
                        .build(className)
                        .navigation() as Fragment
            }
            else -> {
                super.instantiate(classLoader, className)
            }
        }
    }
}