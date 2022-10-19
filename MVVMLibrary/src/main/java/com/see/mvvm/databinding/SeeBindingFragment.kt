package com.see.mvvm.databinding

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.core.util.isEmpty
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.see.mvvm.R
import com.see.mvvm.listener.OnCallListener
import com.see.mvvm.widget.TitleBar

/**
 * @author by XiaoSe on 2020/11/23.
 */
abstract class SeeBindingFragment : Fragment() {
    @SuppressLint("uncheck")
    protected lateinit var mRootView: View

    protected val mBinding get() = _binding!!
    private var _binding: ViewDataBinding? = null

    @SuppressLint("uncheck")
    protected var mCallListener: OnCallListener? = null

    private var mTitleBar: TitleBar? = null

    /**
     *  三种写法：
     *
     *  一、直接返回 layout id 不使用 DataBinding：
     *  ```
     *          return BindingConfig(R.layout.medical_activity_main)
     *  ```
     *  二、直接返回 ViewDataBinding：
     *  ```
     *          layout = AidShopFragmentInfoEditBinding.inflate(inflater, container, false)
     *          return BindingConfig(layout)
     *              .addVariable(BR.vm, vm)
     *              .addVariable(BR.vc, ViewClick())
     *
     *          or
     *
     *          layout = AidShopFragmentInfoEditBinding.inflate(inflater, container, false).apply {
     *              vm = viewModel
     *              vc = ViewClick()
     *              nc = NavClick(findNavController())
     *          }
     *          return BindingConfig(layout)
     *  ```
     *  三、返回 layout id 和 variable：
     *  ```
     *          return BindingConfig(R.layout.advisory_fragment_setting_main)
     *              .addVariable(BR.nc, NavClick(findNavController()))
     *              .addVariable(BR.vc, ViewClick())
     *              .addVariable(BR.vm, viewModel)
     *  ```
     */
    abstract fun getBindingConfig(inflater: LayoutInflater, container: ViewGroup?): BindingConfig

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (requireActivity() is OnCallListener) {
            mCallListener = requireActivity() as OnCallListener
        }
    }

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

    fun setTitle(title: CharSequence?) {
        if (mTitleBar != null) {
            mTitleBar?.setTitle(title)
        } else {
            requireActivity().title = title
        }
    }

    fun <T : View> findViewById(@IdRes id: Int): T {
        return mRootView.findViewById(id)
    }

    fun callOnActivity(what: Int, args: Bundle = Bundle()) {
        mCallListener?.callOnActivity(what, args)
    }

    fun navigate(@IdRes resId: Int, args: Bundle = Bundle()) {
        try {
            findNavController().navigate(resId, args)
        } catch (exception: IllegalArgumentException) {
            findNavController().currentDestination?.id?.let {
                callOnActivity(it, args)
            }
        } catch (exception: RuntimeException) {
            exception.printStackTrace()
        }
    }
}