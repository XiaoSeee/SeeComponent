package com.see.mvvm.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

/**
 * @author by wuxiang@tinglibao.com.cn on 2020/12/11.
 */

inline fun <X, Y> LiveData<X>.mapToMutable(crossinline function: (X) -> Y): MutableLiveData<Y> {
    return MediatorLiveData<Y>().apply {
        addSource(this@mapToMutable) {
            value = function(it)
        }
    }
}