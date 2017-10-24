package com.see.mvp.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.see.mvp.R;
import com.see.mvp.reflect.TypeToken;


/**
 * @author WuXiang
 */
public abstract class BaseActivity<PresenterType extends Presenter> extends AppCompatActivity
        implements View.OnClickListener {
    //final TypeToken<PresenterType> type =  TypeToken.get(PresenterType);
    //TypeToken<T> type = new TypeToken<T>(getClass()) {};
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
        createPresenter();
        helper.onCreate(savedInstanceState);
    }

    private void createPresenter() {

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

    @Override
    public void onClick(View v) {

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