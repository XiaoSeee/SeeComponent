package com.see.mvvm.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

/**
 * 继承 [ViewModelStoreOwner] 用来存放 [androidx.lifecycle.ViewModel]
 * @author by XiaoSe on 2020/11/23.
 */
open class SeeApplication : Application(), ViewModelStoreOwner {
    protected val mViewModelStore: ViewModelStore by lazy {
        ViewModelStore()
    }

    override fun getViewModelStore(): ViewModelStore {
        return mViewModelStore
    }
}