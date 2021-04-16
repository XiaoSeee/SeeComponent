package com.see.mvvm.navigation

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.see.mvvm.R
import com.see.mvvm.widget.TitleBar

/**
 * @author by XiaoSe on 2020/11/23.
 */
abstract class SeeContainerActivity : AppCompatActivity(), OnCallListener {

    private var mTitleBar: TitleBar? = null

    protected lateinit var navController: NavController

    abstract fun getNavConfig(): NavConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config = getNavConfig()
        // 如果启动 Activity 有指定起始页
        val actionStartDestId = intent.getIntExtra(START_DEST_ID, 0)
        if (actionStartDestId != 0) {
            config.startDestId = actionStartDestId
        }
        supportFragmentManager.fragmentFactory = NavFactory(config.hostFragment, config.startArgs)
        setContentView(config.contentLayoutId)
        initTitleBar(config)
        initNavigation(config)
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
    }

    private fun initNavigation(config: NavConfig) {
        if (config.hostFragment.endsWith("NavHostFragment")) {
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navController = navHostFragment.navController
            // 获取 navGraph
            val navGraph = navController.navInflater.inflate(config.graphResId)
            if (config.startDestId != 0) {
                navGraph.startDestination = config.startDestId
            }
            // 设置 TitleBar 事件
            mTitleBar?.let {
                val builder = if (isTopLevel()) {
                    AppBarConfiguration.Builder(navGraph.startDestination)
                } else {
                    AppBarConfiguration.Builder()
                }

                val appBarConfiguration = builder.setFallbackOnNavigateUpListener {
                    onBackPressed()
                    true
                }.build()

                navController.addOnDestinationChangedListener(TitleBarOnDestinationChangedListener(it, appBarConfiguration))
                it.setNavigationOnClickListener {
                    NavigationUI.navigateUp(navController, appBarConfiguration)
                }
            }
            // 设置全局监听
            navController.addOnDestinationChangedListener { controller, destination, arguments ->
                onDestinationChanged(controller, destination, arguments)
            }
            // 设置 navGraph
            navController.setGraph(navGraph, config.startArgs)
        }
    }

    private fun initTitleBar(config: NavConfig) {
        mTitleBar = findViewById(R.id.activity_title_bar)
        mTitleBar?.let {
            setSupportActionBar(it)
            if (!config.hostFragment.endsWith("NavHostFragment")) {
                supportActionBar?.setDisplayHomeAsUpEnabled(!isTopLevel())
                it.setNavigationOnClickListener { onBackPressed() }
            }
        }
    }

    /**
     * 设置是否时顶级 Activity，用于当前为起始 Fragment 时，是否显示返回图标
     * 默认 false 吧，一般一个 app 就首页有这种需求
     */
    open fun isTopLevel(): Boolean {
        return false
    }

    open fun onDestinationChanged(controller: NavController, dest: NavDestination, args: Bundle?) {

    }

    override fun callOnActivity(what: Int, args: Bundle?) {

    }
}