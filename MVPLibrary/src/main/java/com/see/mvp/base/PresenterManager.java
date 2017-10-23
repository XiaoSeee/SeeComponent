package com.see.mvp.base;

import java.util.HashMap;


/**
 * @author WuXiang
 */
public class PresenterManager {
    private static volatile PresenterManager instance;

    /**
     * 使用id当索引保持所有presenter引用
     * <p>
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

    public <T extends Presenter> T create(Object view) {
        T presenter = fromViewClass(view.getClass());
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
     * <p>
     * 虽然主线程是非常安全的。
     */
    private String providePresenterId() {
        return nextId++ + "/" + System.nanoTime() + "/" + (int) (Math.random() * Integer.MAX_VALUE);
    }

    public static <PresenterType extends Presenter> PresenterType fromViewClass(Class<?> viewClass) {
        RequiresPresenter annotation = viewClass.getAnnotation(RequiresPresenter.class);
        //noinspection unchecked
        if (annotation == null) {
            return null;
            //throw new RuntimeException("You must declaration @RequiresPresenter for your Activity");
        }

        Class<PresenterType> presenterClass = (Class<PresenterType>) annotation.value();

        PresenterType presenter;
        try {
            presenter = presenterClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return presenter;
    }
}
