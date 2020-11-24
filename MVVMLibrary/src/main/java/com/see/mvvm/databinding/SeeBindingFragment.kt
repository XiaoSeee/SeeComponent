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
        val binding: ViewDataBinding =
                DataBindingUtil.inflate(inflater, getBindingConfig().layout, container, false)
        binding.lifecycleOwner = this
        for (i: Int in 0 until getBindingConfig().bindingParams.size()) {
            binding.setVariable(getBindingConfig().bindingParams.keyAt(i),
                    getBindingConfig().bindingParams.valueAt(i))
        }
        return binding.root
    }
}