package com.see.mvvm.databinding

import android.util.SparseArray

/**
 * @author by XiaoSe on 2020/11/23.
 */
data class BindingConfig(val layout: Int) {
    val bindingParams = SparseArray<Any>()

    fun addVariable(variable: Int, any: Any): BindingConfig {
        bindingParams.put(variable, any)
        return this
    }
}