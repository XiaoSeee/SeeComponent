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
        val bindingConfig = getBindingConfig()
        val binding: ViewDataBinding = DataBindingUtil.setContentView(this, bindingConfig.layout)
        binding.lifecycleOwner = this
        for (i: Int in 0 until bindingConfig.bindingParams.size()) {
            binding.setVariable(bindingConfig.bindingParams.keyAt(i),
                    bindingConfig.bindingParams.valueAt(i))
        }
    }
}