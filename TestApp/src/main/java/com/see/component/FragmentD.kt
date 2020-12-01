package com.see.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route

/**
 * @author by wuxiang@tinglibao.com.cn on 2020/11/19.
 */
@Route(path = "/app/fragment/d")
class FragmentD : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_d, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.main_text).setOnClickListener {
            requireActivity().title = "FragmentD"
        }
    }
}