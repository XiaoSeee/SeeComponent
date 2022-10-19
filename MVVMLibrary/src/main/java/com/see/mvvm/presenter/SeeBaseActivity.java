package com.see.mvvm.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.see.mvvm.R;


/**
 * @author by WuXiang on 2017/10/25.
 */
public abstract class SeeBaseActivity<PresenterType extends Presenter> extends AppCompatActivity
        implements View.OnClickListener {
    private ViewHelper<PresenterType> helper = new ViewHelper<>(this);
    protected Context mContext;
    /**
     * 如果使用了ToolBar则自动部署，没有则无影响。
     */
    protected Toolbar mToolbar;
    protected TextView mTitle;

    public PresenterType getPresenter() {
        return helper.getPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(getContentView());
        initToolbar();
        initView(savedInstanceState);
        helper.onCreate(savedInstanceState, getClass().getGenericSuperclass());
    }

    /**
     * 设置界面的布局文件
     *
     * @return layout id
     */
    protected abstract int getContentView();

    /**
     * 开始初始化各种布局
     *
     * @param savedInstanceState 异常恢复保存的信息
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 开始加载数据
     */
    protected void loadData() {
        helper.onPostCreate();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.onDestroyView();
        if (isFinishing()) {
            helper.onDestroy();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        helper.onSave(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        helper.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        helper.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        helper.onResult(requestCode, resultCode, data);
    }

    //第二次点击按钮的点击间隔不能少于400毫秒
    private static final int MIN_CLICK_DELAY_TIME = 400;
    private long lastClickTime;

    @Override
    public void onClick(View view) {
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            doClick(view);
            lastClickTime = curClickTime;
        }
    }

    /**
     * 按钮防抖动
     */
    public void doClick(View view) {

    }

    public void initToolbar() {
        mToolbar = $(R.id.activity_toolbar);
        if (mToolbar != null) {
            mTitle = $(R.id.toolbar_title);
            setSupportActionBar(mToolbar);
            ActionBar mActionBar = getSupportActionBar();
            if (null != mActionBar) {
                mActionBar.setDisplayHomeAsUpEnabled(true);
                if (mTitle != null) {
                    mActionBar.setDisplayShowTitleEnabled(false);
                }
            }

            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    public void hideHomeAsUp() {
        ActionBar mActionBar = getSupportActionBar();
        if (null != mActionBar) {
            mActionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        if (mTitle != null) {
            mTitle.setText(title);
        } else {
            super.setTitle(title);
        }
    }

    @Override
    public void setTitle(int titleId) {
        this.setTitle(getText(titleId));
    }

    @Nullable
    protected final <E extends View> E $(@NonNull View view, @IdRes int id) {
        return (E) view.findViewById(id);
    }

    @Nullable
    protected final <E extends View> E $(@IdRes int id) {
        return (E) findViewById(id);
    }
}