package com.see.mvvm.navigation

import android.os.Bundle
import com.see.mvvm.R

/**
 * @author by wuxiang@tinglibao.com.cn on 2020/12/10.
 */
const val START_DEST_ID = "navigation.start.dest.id"

class NavConfig {
    var hostFragment = "androidx.navigation.fragment.NavHostFragment"
    var graphResId = 0
    var startDestId = 0
    var startArgs: Bundle? = null
    var contentLayoutId = R.layout.activity_see_container

    fun setHost(hostFragment: String): NavConfig {
        this.hostFragment = hostFragment
        return this
    }

    fun setContent(contentLayoutId: Int): NavConfig {
        this.contentLayoutId = contentLayoutId
        return this
    }

    fun setGraph(graphResId: Int): NavConfig {
        this.graphResId = graphResId
        return this
    }

    fun setStartDestId(startDestId: Int): NavConfig {
        this.startDestId = startDestId
        return this
    }

    fun setArgs(startArgs: Bundle?): NavConfig {
        this.startArgs = startArgs
        return this
    }
}