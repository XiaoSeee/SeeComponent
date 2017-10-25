package com.see.component;

import android.os.Bundle;

/**
 * @author by WuXiang on 2017/10/25.
 */

public class OneActivity extends MainActivity<MainPresenter> {
    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        hideHomeAsUp();
        setTitle(R.string.app_name);
    }

    @Override
    public void setTextView(String text) {
        super.setTextView(text);
        textView.setText("OneActivity");
    }
}
