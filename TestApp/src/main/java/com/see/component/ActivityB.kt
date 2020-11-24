package com.see.component

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.see.mvvm.navigation.NavFactory

/**
 * @author by wuxiang@tinglibao.com.cn on 2020/11/18.
 */
@Route(path = "/app/activity/b")
class ActivityB : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = NavFactory()
        setContentView(R.layout.activity_b)
        //Navigation.findNavController(this, R.id.main_nav)
    }
}