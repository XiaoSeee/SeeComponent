package com.see.component

import android.os.Bundle
import android.view.Menu
import com.alibaba.android.arouter.facade.annotation.Route
import com.see.mvvm.navigation.SeeContainerActivity

/**
 * @author by wuxiang@tinglibao.com.cn on 2020/11/18.
 */
@Route(path = "/app/activity/a")
class ActivityA : SeeContainerActivity(R.navigation.nav_a) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_activity_b, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
}