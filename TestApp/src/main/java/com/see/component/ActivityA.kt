package com.see.component

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.see.mvvm.navigation.NavFactory

/**
 * @author by wuxiang@tinglibao.com.cn on 2020/11/18.
 */
@Route(path = "/app/activity/a")
class ActivityA : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = NavFactory()
        setContentView(R.layout.activity_a)
        //Navigation.findNavController(this, R.id.main_nav)
    }
}