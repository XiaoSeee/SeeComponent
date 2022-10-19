package com.see.mvvm.presenter;

import android.content.Intent;
import android.os.Bundle;

import java.lang.reflect.Type;

/**
 * A helper class to control presenter's lifecycle.
 *
 * @author by WuXiang on 2017/10/25.
 */
class ViewHelper<PresenterType extends Presenter> {
    public static final String PRESENTER_ID = "presenter_id";

    boolean hasPresenter;

    public PresenterType getPresenter() {
        return presenter;
    }

    PresenterType presenter;
    Object view;

    public ViewHelper(Object view) {
        this.view = view;
    }

    void onCreate(Bundle savedInstanceState, Type type) {
        String id;
        if (savedInstanceState == null || (id = savedInstanceState.getString(PRESENTER_ID)) == null) {
            createPresenter(savedInstanceState, type);
        } else {
            presenter = PresenterManager.getInstance().get(id);
            if (presenter == null) {
                createPresenter(savedInstanceState, type);
            }
        }
    }

    private void createPresenter(Bundle savedInstanceState, Type type) {
        presenter = PresenterManager.getInstance().create(type);
        hasPresenter = presenter != null;
        if (hasPresenter) {
            presenter.create(view, savedInstanceState);
        }
    }

    boolean ensurePresenterInstance() {
        if (presenter == null) {
            if (hasPresenter) {
                //能执行到这里就是见鬼了。表示View所对应的Presenter莫名其妙消失了。单独的View存在是很容易空指针的，所以直接重建最好。
                if (view instanceof SeeBaseActivity) {
                    ((SeeBaseActivity) view).recreate();
                } else if (view instanceof SeeBaseFragment) {
                    ((SeeBaseFragment) view).getActivity().recreate();
                }
            }
            return false;
        } else {
            return true;
        }
    }

    void onPostCreate() {
        if (ensurePresenterInstance()) {
            presenter.onCreateView(view);
        }
    }

    void onDestroyView() {
        if (ensurePresenterInstance()) {
            presenter.onDestroyView();
        }
    }

    void onDestroy() {
        if (ensurePresenterInstance()) {
            presenter.onDestroy();
            PresenterManager.getInstance().destroy(presenter.id);
        }
    }

    void onSave(Bundle state) {
        if (ensurePresenterInstance()) {
            state.putString(PRESENTER_ID, presenter.id);
            presenter.onSave(state);
        }
    }

    void onResume() {
        if (ensurePresenterInstance()) {
            presenter.onResume();
        }
    }

    void onPause() {
        if (ensurePresenterInstance()) {
            presenter.onPause();
        }
    }

    void onResult(int requestCode, int resultCode, Intent data) {
        if (ensurePresenterInstance()) {
            presenter.onResult(requestCode, resultCode, data);
        }
    }
}