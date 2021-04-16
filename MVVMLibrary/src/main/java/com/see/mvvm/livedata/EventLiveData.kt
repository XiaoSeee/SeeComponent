package com.see.mvvm.livedata

import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * @author by wuxiang@tinglibao.com.cn on 2020/12/9.
 */
class EventLiveData<T> : LiveData<T> {
    @Suppress("unused")
    constructor() : super()

    @Suppress("unused")
    constructor(value: T) : super(value)

    private val observers = mutableMapOf<Int, Boolean>()

    @Suppress("unused")
    fun observeEvent(activity: AppCompatActivity, observer: Observer<in T>) {
        val owner: LifecycleOwner = activity
        val storeId = System.identityHashCode(activity.viewModelStore)
        observeEvent(storeId, owner, observer)
    }

    @Suppress("unused")
    fun observeEvent(fragment: Fragment, observer: Observer<in T>) {
        val owner: LifecycleOwner = fragment.viewLifecycleOwner
        val storeId = System.identityHashCode(fragment.viewModelStore)
        observeEvent(storeId, owner, observer)
    }

    private fun observeEvent(storeId: Int, owner: LifecycleOwner, observer: Observer<in T>) {
        if (observers[storeId] == null) {
            observers[storeId] = true
        }

        super.observe(owner) {
            if (observers[storeId] == false) {
                observers[storeId] = true
                it?.let { observer.onChanged(it) }
            }
        }
    }

    /**
     * 请不要在 EventLiveData 中使用 observe 方法。取而代之的是在 Activity 和 Fragment 中分别使用 [observeEvent]  来观察。
     */
    @Deprecated("Can not use observe", level = DeprecationLevel.HIDDEN)
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        throw IllegalArgumentException("Can not use observe")
    }

    /**
     *出于生命周期安全的考虑，请不要在 UnPeekLiveData 中使用 observeForever 方法。
     */
    @Deprecated("Can not use observeForever", level = DeprecationLevel.HIDDEN)
    override fun observeForever(observer: Observer<in T>) {
        throw IllegalArgumentException("Can not use observeForever")
    }

    /**
     * 可以设置空值，但是空值不触发消息
     */
    @MainThread
    public override fun setValue(value: T?) {
        value?.let {
            for (item in observers) {
                observers[item.key] = false
            }
            super.setValue(value)
        }
    }
}