package com.see.mvvm.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

/**
 * @author by XiaoSe on 2020/11/23.
 */
open class SeeApplication : Application(), ViewModelStoreOwner {
    private val mViewModelStore: ViewModelStore by lazy {
        ViewModelStore()
    }

    override fun getViewModelStore(): ViewModelStore {
        return mViewModelStore
    }
}