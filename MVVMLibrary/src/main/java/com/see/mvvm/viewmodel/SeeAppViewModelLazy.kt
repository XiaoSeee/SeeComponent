package com.see.mvvm.viewmodel

import androidx.activity.ComponentActivity
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.*

/**
 * @author by XiaoSe on 2020/11/23.
 */

/**
 * 扩展一个存放在 Application 的全局 ViewModels。应用的 Application 必须继承于 [SeeApplication]
 * 或者参照它给你自己的 Application 实现 [ViewModelStoreOwner] 接口
 */
@MainThread
inline fun <reified VM : ViewModel> ComponentActivity.appViewModels(
        noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<VM> {
    val factoryPromise = factoryProducer ?: {
        defaultViewModelProviderFactory
    }

    return ViewModelLazy(VM::class, { getAppViewModelStore() }, factoryPromise)
}

/**
 * 扩展一个存放在 Application 的全局 ViewModels。应用的 Application 必须继承于 [SeeApplication]
 * 或者参照它给你自己的 Application 实现 [ViewModelStoreOwner] 接口
 */
@MainThread
inline fun <reified VM : ViewModel> Fragment.appViewModels(
        noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<VM> {
    val factoryPromise = factoryProducer ?: {
        requireActivity().defaultViewModelProviderFactory
    }

    return ViewModelLazy(VM::class, { requireActivity().getAppViewModelStore() }, factoryPromise)
}

fun ComponentActivity.getAppViewModelStore(): ViewModelStore {
    checkNotNull(application) {
        ("Your activity is not yet attached to the Application instance.")
    }

    if (application !is ViewModelStoreOwner) {
        throw IllegalStateException("Your application is not a ViewModelStoreOwner.")
    }

    return (application as ViewModelStoreOwner).viewModelStore
}