package com.see.component;

import android.os.Bundle;
import android.widget.TextView;

import com.see.mvp.base.BaseActivity;
import com.see.mvp.base.Presenter;

/**
 * @author WuXiang
 */
public class MainActivity<T extends Presenter> extends BaseActivity<T> {
    TextView textView;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        textView = $(R.id.main_text);
    }

    public void setTextView(String text) {
        textView.setText(text);
    }
}