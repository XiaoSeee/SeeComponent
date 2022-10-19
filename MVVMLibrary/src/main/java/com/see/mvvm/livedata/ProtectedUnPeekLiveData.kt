package com.see.mvvm.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

/**
 * https://github.com/KunMinX/UnPeek-LiveData/
 *
 * 通过在 "代理类/包装类" 中自行维护一个版本号，在 UnPeekLiveData 中维护一个当前版本号，
 * 分别来在 setValue 和 Observe 的时机来改变和对齐版本号，
 * 如此使得无需另外管理一个 Observer map，从而进一步规避了内存管理的问题，
 * 同时也是继 V6 版源码以来，最简的源码设计，方便阅读理解和后续修改。
 *
 * 唯一可信源设计
 * 来确保 "事件" 的发送权牢牢握在可信的逻辑中枢单元手里，从而确保所有订阅者收到的信息都是可靠且一致的，
 *
 * 以及支持消息从内存清空
 * 我们支持通过 clear 方法手动将消息从内存中清空，
 * 以免无用消息随着 SharedViewModel 的长时间驻留而导致内存溢出的发生。
 */
open class ProtectedUnPeekLiveData<T> : LiveData<T> {
    constructor() : super()
    constructor(value: T) : super(value)

    private val mCurrentVersion = AtomicInteger(START_VERSION)
    protected var isAllowNullValue = false

    /**
     * 当 liveData 用作 event 用途时，可使用该方法来观察 "生命周期敏感" 的非粘性消息
     *
     * state 是可变且私用的，event 是只读且公用的，
     * state 的倒灌是应景的，event 倒灌是不符预期的，
     *
     * @param owner    activity 传入 this，fragment 建议传入 getViewLifecycleOwner
     * @param observer observer
     */
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, createObserverWrapper(observer, mCurrentVersion.get()))
    }

    /**
     * 当 liveData 用作 event 用途时，可使用该方法来观察 "生命周期不敏感" 的非粘性消息
     *
     * @param observer observer
     */
    override fun observeForever(observer: Observer<in T>) {
        super.observeForever(createObserverWrapper(observer, mCurrentVersion.get()))
    }

    /**
     * 当 liveData 用作 state 用途时，可使用该方法来观察 "生命周期敏感" 的粘性消息
     *
     * @param owner    activity 传入 this，fragment 建议传入 getViewLifecycleOwner
     * @param observer observer
     */
    fun observeSticky(owner: LifecycleOwner, observer: Observer<T>) {
        super.observe(owner, createObserverWrapper(observer, START_VERSION))
    }

    /**
     * 当 liveData 用作 state 用途时，可使用该方法来观察 "生命周期不敏感" 的粘性消息
     *
     * @param observer observer
     */
    fun observeStickyForever(observer: Observer<in T>) {
        super.observeForever(createObserverWrapper(observer, START_VERSION))
    }

    /**
     * 只需重写 setValue
     * postValue 最终还是会经过这里
     *
     * @param value value
     */
    override fun setValue(value: T?) {
        mCurrentVersion.getAndIncrement()
        super.setValue(value)
    }

    /**
     * 1.添加一个包装类，自己维护一个版本号判断，用于无需 map 的帮助也能逐一判断消费情况
     * 2.重写 equals 方法和 hashCode，在用于手动 removeObserver 时，忽略版本号的变化引起的变化
     */
    internal inner class ObserverWrapper(
        private val observer: Observer<in T>,
        private val version: Int
    ) : Observer<T> {
        override fun onChanged(t: T?) {
            if (mCurrentVersion.get() > version && (t != null || isAllowNullValue)) {
                observer.onChanged(t)
            }
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) {
                return true
            }

            if (other == null || javaClass != other.javaClass) {
                return false
            }

            return observer == (other as ProtectedUnPeekLiveData<*>.ObserverWrapper).observer
        }

        override fun hashCode(): Int {
            return Objects.hash(observer)
        }
    }

    /**
     * 通过 ObserveForever 的 Observe，需要记得 remove，不然存在 LiveData 内存泄漏的隐患，
     * 保险的做法是，在页面的 onDestroy 环节安排 removeObserver 代码，
     * 具体可参见 app module 中 ObserveForeverFragment 的案例
     *
     * @param observer observeForever 注册的 observer，或 observe 注册的 observerWrapper
     */
    override fun removeObserver(observer: Observer<in T>) {
        if (observer.javaClass.isAssignableFrom(ObserverWrapper::class.java)) {
            super.removeObserver(observer)
        } else {
            super.removeObserver(createObserverWrapper(observer, START_VERSION))
        }
    }

    private fun createObserverWrapper(observer: Observer<in T>, version: Int): ObserverWrapper {
        return ObserverWrapper(observer, version)
    }

    /**
     * 手动将消息从内存中清空，
     * 以免无用消息随着 SharedViewModel 的长时间驻留而导致内存溢出的发生。
     */
    fun clear() {
        super.setValue(null)
    }

    companion object {
        private const val START_VERSION = -1
    }
}