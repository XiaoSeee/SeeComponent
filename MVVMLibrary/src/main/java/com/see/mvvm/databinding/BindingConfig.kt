package com.see.mvvm.databinding

import android.util.SparseArray
import androidx.lifecycle.ViewModel

/**
 * @author by wuxiang@tinglibao.com.cn on 2020/11/23.
 */
data class BindingConfig(val layout: Int, val variable: Int, val viewModel: ViewModel) {
    val bindingParams = SparseArray<Any>()

    fun addBindingParams(variable: Int, any: Any): BindingConfig {
        bindingParams.put(variable, any)
        return this
    }
}