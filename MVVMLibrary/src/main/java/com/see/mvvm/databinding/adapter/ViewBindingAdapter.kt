package com.see.mvvm.databinding

import android.util.Log
import android.view.View
import androidx.databinding.BindingAdapter

/**
 * @author by wuxiang@tinglibao.com.cn on 2020/12/7.
 */

@BindingAdapter("setVisible", requireAll = false)
fun setVisible(view: View, visible: Boolean) {
    Log.d("BindingAdapter", "setVisible view.id=${view.id} visible=$visible")
    view.visibility = if (visible) View.VISIBLE else View.GONE
}