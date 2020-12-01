package com.see.mvvm.databinding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.see.mvvm.R
import com.see.mvvm.widget.TitleBar

/**
 * @author by XiaoSe on 2020/11/23.
 */
abstract class SeeBindingFragment : Fragment() {
    private var mTitleBar: TitleBar? = null

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

        mTitleBar = binding.root.findViewById(R.id.activity_title_bar)

        return binding.root
    }

    fun setTitle(titleId: Int) {
        setTitle(getString(titleId))
    }

    fun setTitle(title: CharSequence) {
        if (mTitleBar != null) {
            mTitleBar?.setTitle(title)
        } else {
            requireActivity().title = title
        }
    }
}