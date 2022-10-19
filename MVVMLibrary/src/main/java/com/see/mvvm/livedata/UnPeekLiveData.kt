package com.see.mvvm.livedata

/**
 * 唯一可信源设计
 * 来确保 "事件" 的发送权牢牢握在可信的逻辑中枢单元手里，从而确保所有订阅者收到的信息都是可靠且一致的，
 *
 * 提供 Builder 选项的支持
 * 目前提供 "是否允许发送 null" 的选项。
 *
 * Create by KunMinX at 2021/6/17
 */
class UnPeekLiveData<T> : ProtectedUnPeekLiveData<T> {
    constructor() : super()
    constructor(value: T) : super(value)

    public override fun setValue(value: T?) {
        super.setValue(value)
    }

    public override fun postValue(value: T?) {
        super.postValue(value)
    }

    class Builder<T> {
        /**
         * 是否允许传入 null value
         */
        private var isAllowNullValue = false
        fun setAllowNullValue(allowNullValue: Boolean): Builder<T> {
            isAllowNullValue = allowNullValue
            return this
        }

        fun create(): UnPeekLiveData<T> {
            val liveData = UnPeekLiveData<T>()
            liveData.isAllowNullValue = isAllowNullValue
            return liveData
        }
    }
}