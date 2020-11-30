package com.see.component.ble

import com.see.component.BR
import com.see.component.R
import com.see.mvvm.databinding.BindingConfig
import com.see.mvvm.databinding.SeeBindingActivity
import com.see.mvvm.viewmodel.appViewModels

class MainActivity : SeeBindingActivity() {
    private val mControllerViewModel: ControllerViewModel by appViewModels()

    override fun getBindingConfig(): BindingConfig {
        return BindingConfig(R.layout.activity_main)
                .addVariable(BR.controller, mControllerViewModel)
    }

    override fun onStart() {
        super.onStart()
        mControllerViewModel.scanWidth.value = 155
    }
}