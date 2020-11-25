package com.see.mvvm.databinding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * @author by XiaoSe on 2020/11/23.
 */
abstract class SeeBindingFragment : Fragment() {
    abstract fun getBindingConfig(): BindingConfig

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val bindingConfig = getBindingConfig()
        val binding: ViewDataBinding =
                DataBindingUtil.inflate(inflater, bindingConfig.layout, container, false)
        binding.lifecycleOwner = this
        for (i: Int in 0 until bindingConfig.bindingParams.size()) {
            binding.setVariable(bindingConfig.bindingParams.keyAt(i),
                    bindingConfig.bindingParams.valueAt(i))
        }
        return binding.root
    }
}