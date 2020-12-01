package com.see.component

import com.alibaba.android.arouter.facade.annotation.Route
import com.see.mvvm.navigation.SeeContainerActivity

/**
 * @author by wuxiang@tinglibao.com.cn on 2020/11/18.
 */
@Route(path = "/app/activity/a")
class ActivityA : SeeContainerActivity(R.navigation.nav_a)