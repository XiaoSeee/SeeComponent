package com.see.mvvm.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.see.mvvm.R
import com.see.mvvm.widget.TitleBar

/**
 * @param graphResId 定义的NavigationViewId
 * @param contentLayoutId ContentViewId，不传的话用默认的
 * @author by XiaoSe on 2020/11/23.
 */
abstract class SeeContainerActivity(
        private val graphResId: Int,
        private val contentLayoutId: Int = R.layout.activity_see_container,
) : AppCompatActivity() {

    private var mTitleBar: TitleBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = NavFactory(graphResId, getStartArgs())
        setContentView(contentLayoutId)

        mTitleBar = findViewById(R.id.activity_title_bar)
        if (mTitleBar != null) {
            initTitleBar()
        }
    }

    /**
     * 获取启动参数
     */
    open fun getStartArgs(): Bundle? {
        return null
    }

    private fun initTitleBar() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration =
                if (isTopLevel())
                    AppBarConfiguration.Builder()
                            .setFallbackOnNavigateUpListener {
                                onBackPressed()
                                true
                            }.build()
                else
                    AppBarConfiguration(navController.graph)

        setSupportActionBar(mTitleBar)
        navController.addOnDestinationChangedListener(
                TitleBarOnDestinationChangedListener(mTitleBar!!, appBarConfiguration))
        mTitleBar?.setNavigationOnClickListener {
            NavigationUI.navigateUp(navController, appBarConfiguration)
        }
    }

    /**
     * 设置是否时顶级 Activity，用于当前为起始 Fragment 时，是否显示返回图标
     * 默认 false 吧，一般一个 app 就首页有这种需求
     */
    open fun isTopLevel(): Boolean {
        return false
    }
}