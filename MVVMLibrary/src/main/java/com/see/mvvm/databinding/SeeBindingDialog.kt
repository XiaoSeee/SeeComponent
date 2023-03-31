package com.see.mvvm.databinding

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.core.util.isEmpty
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.see.mvvm.R
import com.see.mvvm.widget.TitleBar

/**
 * @author by XiaoSe on 2020/11/23.
 */
abstract class SeeBindingDialog : DialogFragment() {
    @SuppressLint("uncheck")
    protected lateinit var mRootView: View

    protected val mBinding get() = _binding!!
    private var _binding: ViewDataBinding? = null

    private var mTitleBar: TitleBar? = null

    /**
     * see [SeeBindingFragment.getBindingConfig]
     */
    abstract fun getBindingConfig(inflater: LayoutInflater, container: ViewGroup?): BindingConfig

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindingConfig = getBindingConfig(inflater, container)
        when {
            bindingConfig.binding != null -> {
                _binding = bindingConfig.binding?.also { iniBinding(it, bindingConfig) }
            }
            bindingConfig.bindingParams.isEmpty() -> {
                mRootView = inflater.inflate(bindingConfig.layout, container, false)
            }
            else -> {
                _binding =
                    DataBindingUtil.inflate<ViewDataBinding>(
                        inflater,
                        bindingConfig.layout,
                        container,
                        false
                    ).also { iniBinding(it, bindingConfig) }
            }
        }

        mTitleBar = mRootView.findViewById(R.id.activity_title_bar)
        return mRootView
    }

    private fun iniBinding(binding: ViewDataBinding, config: BindingConfig) {
        binding.lifecycleOwner = viewLifecycleOwner
        for (i: Int in 0 until config.bindingParams.size()) {
            binding.setVariable(
                config.bindingParams.keyAt(i),
                config.bindingParams.valueAt(i)
            )
        }
        mRootView = binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setTitle(titleId: Int) {
        setTitle(getString(titleId))
    }

    fun setTitle(title: CharSequence) {
        mTitleBar?.title = title
    }

    fun <T : View> findViewById(@IdRes id: Int): T {
        return mRootView.findViewById(id)
    }

    fun setClickById(@IdRes id: Int, listener: View.OnClickListener) {
        findViewById<View>(id).setOnClickListener(listener)
    }
}