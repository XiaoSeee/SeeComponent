package com.see.mvvm.databinding

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * @author by wuxiang@tinglibao.com.cn on 2020/11/23.
 */
abstract class SeeBindingActivity : AppCompatActivity() {
    abstract fun initViewModel()
    abstract fun getBindingConfig(): BindingConfig

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        initViewModel()
        val binding: ViewDataBinding =
                DataBindingUtil.setContentView(this, getBindingConfig().layout)
        binding.lifecycleOwner = this
        binding.setVariable(getBindingConfig().variable, getBindingConfig().viewModel)
        for (i: Int in 0 until getBindingConfig().bindingParams.size()) {
            binding.setVariable(getBindingConfig().bindingParams.keyAt(i),
                    getBindingConfig().bindingParams.valueAt(i))
        }
    }
}