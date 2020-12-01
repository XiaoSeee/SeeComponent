package com.see.mvvm.databinding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.see.mvvm.R
import com.see.mvvm.widget.TitleBar

/**
 * @author by XiaoSe on 2020/11/23.
 */
abstract class SeeBindingActivity : AppCompatActivity() {
    private var mTitleBar: TitleBar? = null

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

        mTitleBar = findViewById(R.id.activity_title_bar)
        if (mTitleBar != null) {
            initTitleBar()
        }
    }

    private fun initTitleBar() {
        setSupportActionBar(mTitleBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(!isTopLevel())
        mTitleBar?.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    /**
     * 设置是否时顶级 Activity，用于是否显示返回图标
     * 默认 false 吧，一般一个 app 就首页有这种需求
     */
    open fun isTopLevel(): Boolean {
        return false
    }
}