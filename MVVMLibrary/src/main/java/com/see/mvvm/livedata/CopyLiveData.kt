package com.see.mvvm.livedata

import android.util.Log
import androidx.lifecycle.LiveData

/**
 * @author by wuxiang@tinglibao.com.cn on 2020/11/29.
 */
class CopyLiveData<E> : LiveData<MutableList<E>> {
    constructor(value: MutableList<E>) : super(value)
    constructor() : super()

    public override fun postValue(value: MutableList<E>) {
        super.postValue(value)
    }

    public override fun setValue(value: MutableList<E>?) {
        val newValue: MutableList<E> = value?.map {
            it?.deepCopy()
        } as MutableList<E>
        if (newValue === value) {
            Log.e("CopyLiveData", "==")
        } else {
            Log.e("CopyLiveData", "!=")
        }
        super.setValue(newValue)
    }
}


