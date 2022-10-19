package com.see.component

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.alibaba.android.arouter.facade.annotation.Route
import com.see.component.ble.ControllerViewModel
import com.see.mvvm.databinding.BindingConfig
import com.see.mvvm.databinding.SeeBindingFragment

/**
 * @author by wuxiang@tinglibao.com.cn on 2020/11/18.
 */
@Route(path = "/app/fragment/a")
class FragmentA : SeeBindingFragment() {
    val mControllerViewModel: ControllerViewModel by viewModels()
    override fun getBindingConfig(inflater: LayoutInflater, container: ViewGroup?): BindingConfig {
        return BindingConfig(R.layout.fragment_a)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(R.string.feedback)
        view.findViewById<View>(R.id.main_text).setOnClickListener {
            findNavController().navigate(R.id.nav_action_a)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_a, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController()) || super.onOptionsItemSelected(
            item
        )
    }
}