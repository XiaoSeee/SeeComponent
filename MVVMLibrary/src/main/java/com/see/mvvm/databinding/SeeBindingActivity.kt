package com.see.mvvm.databinding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * @author by XiaoSe on 2020/11/23.
 */
abstract class SeeBindingActivity : AppCompatActivity() {
    abstract fun getBindingConfig(): BindingConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ViewDataBinding =
                DataBindingUtil.setContentView(this, getBindingConfig().layout)
        binding.lifecycleOwner = this
        for (i: Int in 0 until getBindingConfig().bindingParams.size()) {
            binding.setVariable(getBindingConfig().bindingParams.keyAt(i),
                    getBindingConfig().bindingParams.valueAt(i))
        }
    }
}