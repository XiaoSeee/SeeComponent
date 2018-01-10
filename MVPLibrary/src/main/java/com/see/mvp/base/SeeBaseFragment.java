package com.see.mvp.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.see.mvp.R;

/**
 * @author by WuXiang on 2017/10/25.
 */
public abstract class SeeBaseFragment<PresenterType extends Presenter> extends Fragment
        implements View.OnClickListener {
    private ViewHelper<PresenterType> helper = new ViewHelper<>(this);
    protected Context mContext;
    protected View mRootView;
    protected TextView mTitle;
    protected Toolbar mToolbar;
    /**
     * 表示UI是否准备好。
     * 因为数据加载后需要更新UI，如果UI还没有inflate，就不需要做数据加载。
     * 因为setUserVisibleHint()会在onCreateView()之前调用一次，
     * 如果此时调用，UI还没有inflate，因此不能加载数据。
     */
    protected boolean mIsPrepared = false;
    /**
     * 表示是否已经做过数据加载，如果做过了就不需要做了。
     * 因为setUserVisibleHint(true)在界面可见时都会调用，
     * 如果滑到该界面做过数据加载后，滑走，再滑回来，还是会调用setUserVisibleHint(true)，
     * 此时由于 mIsLoaded = true，因此不会再做一遍数据加载。
     */
    protected boolean mIsLoaded = false;

    protected OnCallListener mCallListener;

    public PresenterType getPresenter() {
        return helper.getPresenter();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (getActivity() instanceof OnCallListener) {
            mCallListener = (OnCallListener) getActivity();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper.onCreate(savedInstanceState, getClass().getGenericSuperclass());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getContentView(), container, false);
        initView(savedInstanceState);
        initToolbar();
        mIsLoaded = false;
        mIsPrepared = true;
        loadData();
        return mRootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            loadData();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        helper.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        helper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        helper.onSave(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        helper.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        helper.onPause();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        helper.onResult(requestCode, resultCode, data);
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
    protected void lazyLoad() {
        mIsLoaded = true;
        helper.onPostCreate();
    }

    private void loadData() {
        if (getUserVisibleHint() && mIsPrepared && !mIsLoaded) {
            lazyLoad();
        }
    }

    private void initToolbar() {
        mToolbar = $(R.id.activity_toolbar);
        mTitle = $(R.id.toolbar_title);
    }

    public void setTitle(CharSequence title) {
        if (mTitle != null) {
            mTitle.setText(title);
        } else {
            getActivity().setTitle(title);
        }
    }

    public void setTitle(int titleId) {
        this.setTitle(getText(titleId));
    }

    protected final <E extends View> E $(@NonNull View view, @IdRes int id) {
        return (E) view.findViewById(id);
    }

    protected final <E extends View> E $(@IdRes int id) {
        return (E) mRootView.findViewById(id);
    }

    @Override
    public void onClick(View view) {

    }

    public interface OnCallListener {
        void callOnActivity(int what, Object obj);
    }
}