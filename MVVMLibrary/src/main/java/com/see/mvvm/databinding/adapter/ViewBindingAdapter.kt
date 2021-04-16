package com.see.mvvm.databinding.adapter

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * @author by wuxiang@tinglibao.com.cn on 2020/12/7.
 */

@BindingAdapter("setClick", requireAll = false)
fun setClick(view: View, click: () -> Unit) {
    view.setOnClickListener { click.invoke() }
}

@BindingAdapter("setVisible", requireAll = false)
fun setVisible(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("setAdapter", "setData")
fun <VH : RecyclerView.ViewHolder, T> setAdapter(view: RecyclerView,
                                                 adapter: RecyclerView.Adapter<VH>,
                                                 data: MutableList<T>) {

}
