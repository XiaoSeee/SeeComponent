package com.see.component.ble

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ControllerViewModel : ViewModel() {
    val scanWidth: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(6)
    }

    fun addOne() {
        scanWidth.value = scanWidth.value!! + 1
    }
}