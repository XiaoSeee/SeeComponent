package com.see.mvvm.presenter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;


/**
 * @author by WuXiang on 2017/10/25.
 */
public class PresenterManager {
    private static volatile PresenterManager instance;

    /**
     * 使用id当索引保持所有presenter引用。
     * 虽然view与presenter是一一对应关系，但避免保持View引。
     */
    private HashMap<String, Presenter> idToPresenter = new HashMap<>();
    private int nextId;

    /**
     * 双重检查锁定（DCL）方式
     */
    public static PresenterManager getInstance() {
        if (instance == null) {
            synchronized (PresenterManager.class) {
                if (instance == null) {
                    instance = new PresenterManager();
                }
            }
        }
        return instance;
    }

    public <T extends Presenter> T create(Type type) {
        T presenter = fromType(type);
        if (presenter == null) {
            return null;
        }

        presenter.id = providePresenterId();
        idToPresenter.put(presenter.id, presenter);
        return presenter;
    }

    public <T extends Presenter> T get(String id) {
        return (T) idToPresenter.get(id);
    }

    public void destroy(String id) {
        idToPresenter.remove(id);
    }

    /**
     * 最大程度保持线程安全。
     * 虽然主线程是非常安全的。
     */
    private String providePresenterId() {
        return nextId++ + "/" + System.nanoTime() + "/" + (int) (Math.random() * Integer.MAX_VALUE);
    }

    private <T extends Presenter> T fromType(Type type) {
        //获取 Type 失败
        if (type == null) {
            return null;
        }

        //没有指定 Presenter
        if (!(type instanceof ParameterizedType)) {
            return null;
        }

        T presenter;
        try {
            Class<T> presenterClass = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
            presenter = presenterClass.newInstance();
        } catch (ClassCastException | InstantiationException | IllegalAccessException e) {
            return null;
        }
        return presenter;
    }
}
