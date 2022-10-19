package com.see.component

import com.alibaba.android.arouter.facade.annotation.Route
import com.see.mvvm.navigation.NavConfig
import com.see.mvvm.navigation.SeeContainerActivity

/**
 * @author by wuxiang@tinglibao.com.cn on 2020/11/18.
 */
@Route(path = "/app/activity/a")
class ActivityA : SeeContainerActivity() {
    override fun getNavConfig(): NavConfig {
        return NavConfig().setGraph(R.navigation.nav_a)
    }
}