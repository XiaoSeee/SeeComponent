package com.see.mvvm.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.see.mvvm.R
import com.see.mvvm.widget.TitleBar

/**
 * @author by XiaoSe on 2020/11/23.
 */
abstract class SeeContainerActivity : AppCompatActivity() {
    private var mTitleBar: TitleBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = NavFactory(getGraphId(), getStartArgs())
        setContentView(R.layout.activity_see_container)
        initToolBar()
    }

    private fun initToolBar() {
        mTitleBar = findViewById(R.id.activity_toolbar)
        setSupportActionBar(mTitleBar)

    }

    abstract fun getGraphId(): Int
    open fun getStartArgs(): Bundle? {
        return null
    }

    fun AppCompatActivity.initTitle() {

        val title = findViewById<TitleBar>(R.id.activity_toolbar)
    }
}