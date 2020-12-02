package com.see.mvvm.navigation

import androidx.navigation.NavController

/**
 * @author by wuxiang@tinglibao.com.cn on 2020/12/2.
 */
class NavClick(private val navController: NavController) {
    fun navigate(action: Int) {
        navController.navigate(action)
    }
}