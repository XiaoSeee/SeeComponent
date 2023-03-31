package com.see.mvvm.databinding

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.isEmpty
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.see.mvvm.R
import com.see.mvvm.widget.TitleBar

/**
 * @author by XiaoSe on 2020/11/23.
 */
abstract class SeeBindingActivity : AppCompatActivity() {
    @SuppressLint("uncheck")
    protected var mBinding: ViewDataBinding? = null

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
     *          layout = DataBindingUtil.setContentView(this, R.layout.aid_shop_activity_login)
     *          return BindingConfig(layout)
     *              .addVariable(BR.vm, vm)
     *              .addVariable(BR.vc, ViewClick())
     *
     *          or
     *
     *          layout = DataBindingUtil.setContentView<AidShopActivityLoginBinding>(
     *              this,
     *              R.layout.aid_shop_activity_login
     *              ).apply {
     *                  vm = viewModel
     *                  vc = ViewClick()
     *              }
     *              return BindingConfig(layout)
     *  ```
     *  三、返回 layout id 和 variable：
     *  ```
     *          return BindingConfig(R.layout.doctor_activity_login)
     *              .addVariable(BR.vm, vm)
     *              .addVariable(BR.vc, ViewClick())
     *  ```
     */
    abstract fun getBindingConfig(): BindingConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bindingConfig = getBindingConfig()
        when {
            bindingConfig.binding != null -> {
                mBinding = bindingConfig.binding?.also { iniBinding(it, bindingConfig) }
            }
            bindingConfig.bindingParams.isEmpty() -> {
                setContentView(bindingConfig.layout)
            }
            else -> {
                mBinding =
                    DataBindingUtil.setContentView<ViewDataBinding>(this, bindingConfig.layout)
                        .also { iniBinding(it, bindingConfig) }
            }
        }

        initTitleBar()
    }

    private fun iniBinding(binding: ViewDataBinding, config: BindingConfig) {
        binding.lifecycleOwner = this
        for (i: Int in 0 until config.bindingParams.size()) {
            binding.setVariable(
                config.bindingParams.keyAt(i),
                config.bindingParams.valueAt(i)
            )
        }
    }

    private fun initTitleBar() {
        mTitleBar = findViewById(R.id.activity_title_bar)
        mTitleBar?.let {
            setSupportActionBar(it)
            supportActionBar?.setDisplayHomeAsUpEnabled(!isTopLevel())
            it.setNavigationOnClickListener { onBackPressed() }
        }
    }

    /**
     * 设置是否时顶级 Activity，用于是否显示返回图标
     * 默认 false 吧，一般一个 app 就首页有这种需求
     */
    open fun isTopLevel(): Boolean {
        return false
    }

    fun setClickById(@IdRes id: Int, listener: View.OnClickListener) {
        findViewById<View>(id).setOnClickListener(listener)
    }
}