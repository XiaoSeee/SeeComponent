package com.see.component

import android.view.Menu
import com.alibaba.android.arouter.facade.annotation.Route
import com.see.mvvm.databinding.BindingConfig
import com.see.mvvm.databinding.SeeBindingActivity

/**
 * @author by wuxiang@tinglibao.com.cn on 2020/11/18.
 */
@Route(path = "/app/activity/b")
class ActivityB : SeeBindingActivity() {
    override fun getBindingConfig(): BindingConfig {
        return BindingConfig(R.layout.activity_b)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_b, menu)
        return super.onCreateOptionsMenu(menu)
    }
}