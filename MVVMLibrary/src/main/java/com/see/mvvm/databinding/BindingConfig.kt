package com.see.mvvm.databinding

import android.util.SparseArray
import androidx.databinding.ViewDataBinding

/**
 * @author by XiaoSe on 2020/11/23.
 */
class BindingConfig(val layout: Int) {
    constructor(binding: ViewDataBinding) : this(-1) {
        this.binding = binding
    }

    var binding: ViewDataBinding? = null

    val bindingParams = SparseArray<Any>()

    fun addVariable(variable: Int, any: Any): BindingConfig {
        bindingParams.put(variable, any)
        return this
    }
}