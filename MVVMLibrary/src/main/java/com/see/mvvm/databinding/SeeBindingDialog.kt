package com.see.mvvm.databinding

import android.annotation.SuppressLint
import android.app.Dialog
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

    private var mTitleBar: TitleBar? = null

    abstract fun getBindingConfig(): BindingConfig

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
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
        mTitleBar?.title = title
    }

    fun <T : View> findViewById(@IdRes id: Int): T {
        return mRootView.findViewById(id)
    }
}