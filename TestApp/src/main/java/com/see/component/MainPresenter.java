package com.see.component;

import android.support.annotation.NonNull;

import com.see.mvp.base.Presenter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by WuXiang on 2017/10/24.
 */

public class MainPresenter extends Presenter<MainActivity> {
    private List list;

    @Override
    protected void onCreateView(@NonNull MainActivity view) {
        super.onCreateView(view);

        view.setTextView("HaHaHa```");

        list = new ArrayList<>();
        list.add("sss");
    }

    private void out() {
        System.out.print(list.size());
    }
}
