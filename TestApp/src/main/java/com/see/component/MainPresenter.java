package com.see.component;

import android.support.annotation.NonNull;

import com.see.mvp.base.Presenter;

/**
 * @author by WuXiang on 2017/10/24.
 */

public class MainPresenter extends Presenter<MainActivity> {

    @Override
    protected void onCreateView(@NonNull MainActivity view) {
        super.onCreateView(view);

        view.setTextView("HaHaHa```");

    }
}
