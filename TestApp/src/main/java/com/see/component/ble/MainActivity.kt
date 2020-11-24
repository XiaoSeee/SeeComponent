package com.see.component.ble

import androidx.activity.viewModels
import com.see.component.BR
import com.see.component.R
import com.see.mvvm.databinding.BindingConfig
import com.see.mvvm.databinding.SeeBindingActivity

class MainActivity : SeeBindingActivity() {
    private val mControllerViewModel: ControllerViewModel by viewModels()

    override fun getBindingConfig(): BindingConfig {
        return BindingConfig(R.layout.activity_main)
                .addVariable(BR.controller, mControllerViewModel)
    }

    override fun onStart() {
        super.onStart()
        mControllerViewModel.scanWidth.value = 155
    }
}