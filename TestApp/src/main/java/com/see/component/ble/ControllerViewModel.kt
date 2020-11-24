package com.see.component.ble

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ControllerViewModel : ViewModel() {
    val scanWidth: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(6)
    }

    fun addOne() {
        val old = scanWidth.value
        scanWidth.value = old?.plus(1)
    }
}