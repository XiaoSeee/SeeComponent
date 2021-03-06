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
import com.see.mvvm.navigation.OnCallListener
import com.see.mvvm.widget.TitleBar

/**
 * @author by XiaoSe on 2020/11/23.
 */
abstract class SeeBindingFragment : Fragment() {
    @SuppressLint("uncheck")
    protected lateinit var mRootView: View

    @SuppressLint("uncheck")
    protected var mCallListener: OnCallListener? = null

    private var mTitleBar: TitleBar? = null

    abstract fun getBindingConfig(): BindingConfig

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (requireActivity() is OnCallListener) {
            mCallListener = requireActivity() as OnCallListener
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val bindingConfig = getBindingConfig()
        if (bindingConfig.bindingParams.isEmpty()) {
            mRootView = inflater.inflate(bindingConfig.layout, container, false)
        } else {
            val binding: ViewDataBinding =
                    DataBindingUtil.inflate(inflater, bindingConfig.layout, container, false)
            binding.lifecycleOwner = this
            for (i: Int in 0 until bindingConfig.bindingParams.size()) {
                binding.setVariable(bindingConfig.bindingParams.keyAt(i),
                        bindingConfig.bindingParams.valueAt(i))
            }
            mRootView = binding.root
        }

        mTitleBar = mRootView.findViewById(R.id.activity_title_bar)
        return mRootView
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

    fun <T : View> findViewById(@IdRes id: Int): T {
        return mRootView.findViewById(id)
    }

    fun callOnActivity(what: Int, args: Bundle?) {
        mCallListener?.callOnActivity(what, args)
    }

    fun navigate(@IdRes resId: Int, args: Bundle? = null) {
        try {
            findNavController().navigate(resId, args)
        } catch (exception: RuntimeException) {
            findNavController().currentDestination?.id?.let {
                callOnActivity(it, args)
            }
        }
    }
}