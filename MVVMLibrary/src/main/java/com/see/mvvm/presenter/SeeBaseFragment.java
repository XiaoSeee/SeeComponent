package com.see.mvvm.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.see.mvvm.R;
import com.see.mvvm.listener.OnCallListener;

import org.jetbrains.annotations.NotNull;

/**
 * @author by WuXiang on 2017/10/25.
 */
public abstract class SeeBaseFragment<PresenterType extends Presenter> extends Fragment
        implements View.OnClickListener {
    public static final int SET_TITLE = 18000;//设置标题

    private final ViewHelper<PresenterType> helper = new ViewHelper<>(this);
    protected Context mContext;
    protected View mRootView;
    protected TextView mTitle;
    protected CharSequence mTitleChar;
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
    public void onAttach(@NonNull Context context) {
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
        mIsLoaded = false;
        mIsPrepared = true;
        initToolbar();
        initView(savedInstanceState);
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        loadData();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            loadData();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            Bundle args = new Bundle();
            args.putCharSequence("SET_TITLE", mTitleChar);
            callOnActivity(SET_TITLE, args);
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
        mTitleChar = title;
        if (mTitle != null) {
            mTitle.setText(mTitleChar);
        } else {
            Bundle args = new Bundle();
            args.putCharSequence("SET_TITLE", mTitleChar);
            callOnActivity(SET_TITLE, args);
        }
    }

    public void setTitle(int titleId) {
        this.setTitle(getText(titleId));
    }

    public void addToolbarMenu(@MenuRes int resId, Toolbar.OnMenuItemClickListener listener) {
        if (mToolbar != null) {
            mToolbar.inflateMenu(resId);
            mToolbar.setOnMenuItemClickListener(listener);
        }
    }

    public void callOnActivity(int what) {
        callOnActivity(what, new Bundle());
    }

    public void callOnActivity(int what, @NotNull Bundle args) {
        if (mCallListener != null) {
            mCallListener.callOnActivity(what, args);
        }
    }

    protected final <E extends View> E $(@NonNull View view, @IdRes int id) {
        return view.findViewById(id);
    }

    protected final <E extends View> E $(@IdRes int id) {
        return mRootView.findViewById(id);
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
}