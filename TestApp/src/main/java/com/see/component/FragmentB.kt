package com.see.component

import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.findNavController
import com.alibaba.android.arouter.facade.annotation.Route
import com.see.mvvm.databinding.BindingConfig
import com.see.mvvm.databinding.SeeBindingFragment

/**
 * @author by wuxiang@tinglibao.com.cn on 2020/11/18.
 */
@Route(path = "/app/fragment/b")
class FragmentB : SeeBindingFragment() {
    override fun getBindingConfig(inflater: LayoutInflater, container: ViewGroup?): BindingConfig {
        return BindingConfig(R.layout.fragment_b)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle("onViewCreated")
        setHasOptionsMenu(true)
        view.findViewById<View>(R.id.main_text).setOnClickListener {
            findNavController().navigate(R.id.nav_action_a)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_activity_b, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}