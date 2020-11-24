package com.see.mvvm.navigation

import android.os.Bundle
import com.see.mvvm.databinding.SeeBindingActivity

/**
 * @author by XiaoSe on 2020/11/23.
 */
abstract class SeeContainerActivity : SeeBindingActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = NavFactory()
    }
}