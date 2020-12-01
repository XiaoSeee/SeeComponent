package com.see.mvvm.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import com.see.mvvm.R

/**
 * 实现 title 在中间的 Toolbar 使用 [android:gravity="center"] 来控制
 *
 * @author by wuxiang@tinglibao.com.cn on 2020/11/25.
 */
class TitleBar @JvmOverloads constructor(context: Context,
                                         attrs: AttributeSet? = null,
                                         defStyleAttr: Int = R.attr.toolbarStyle)
    : Toolbar(context, attrs, defStyleAttr) {

    /**
     * 居中标题
     */
    private val mCenterTextView: AppCompatTextView by lazy {
        val view = AppCompatTextView(context)
        view.setSingleLine()
        view.ellipsize = TextUtils.TruncateAt.END
        if (mCenterTitleTextAppearance != 0) {
            view.setTextAppearance(context, mCenterTitleTextAppearance)
        }
        if (mCenterTitleTextColor != null) {
            view.setTextColor(mCenterTitleTextColor)
        }
        view
    }

    /**
     * 居中标题的TitleTextAppearance
     */
    private var mCenterTitleTextAppearance = 0

    /**
     * 居中标题的颜色
     */
    private var mCenterTitleTextColor: ColorStateList? = null

    /**
     * 居中标题的文本
     */
    private var mCenterTitle: CharSequence? = null

    var mTitleGravity = Gravity.START

    init {
        resolveAttrs(attrs, defStyleAttr)
    }

    /**
     * 读取ToolBar的默认标题属性
     *
     * @param attrs        AttributeSet
     * @param defStyleAttr defStyleAttr
     */
    @SuppressLint("CustomViewStyleable")
    private fun resolveAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.Toolbar, defStyleAttr, 0)
        mCenterTitleTextAppearance = a.getResourceId(R.styleable.Toolbar_titleTextAppearance, 0)
        mCenterTitleTextColor = a.getColorStateList(R.styleable.Toolbar_titleTextColor)
        mTitleGravity = a.getInteger(R.styleable.Toolbar_android_gravity, mTitleGravity)
        a.recycle()
    }

    override fun setTitle(title: CharSequence?) {
        if (mTitleGravity == Gravity.CENTER) {
            setCenterTitle(title)
        } else {
            super.setTitle(title)
        }
    }

    /**
     * 设置居中标题
     *
     * @param resId Resource ID of a string to set as the title
     */
    fun setCenterTitle(@StringRes resId: Int) {
        setCenterTitle(context.getString(resId))
    }

    /**
     * 设置居中标题
     * 模仿ToolBar中的写法,样式与ToolBar中标题一致
     *
     * @param title 标题文本
     */
    fun setCenterTitle(title: CharSequence?) {
        if (!TextUtils.isEmpty(title)) {
            if (mCenterTextView.parent !== this) {
                addCenterView(mCenterTextView)
            }
        } else if (mCenterTextView.parent === this) {
            // mCenterTextView已添加时，若设置title为空,就移除mCenterTextView
            removeView(mCenterTextView)
        }

        mCenterTextView.text = title
        mCenterTitle = title
    }

    /**
     * 获取居中标题的文本
     *
     * @return 居中标题的文本
     */
    fun getCenterTitle(): CharSequence? {
        return mCenterTitle
    }

    /**
     * 添加一个居中的View
     * 有时候ToolBar中间不是文本，是一个其他View,或想显示自定义居中标题的View,可以在这里传入一个View
     * 不与 [.setCenterTitle] 同时使用
     *
     * @param view 放在中间的View
     */
    fun addCenterView(view: View) {
        addCustomView(view, Gravity.CENTER)
    }

    /**
     * 添加一个左边的View
     * 左边是一个图片按钮时可以直接使用[.setNavigationIcon]
     * 若想放一个自定义View，可以使用此方法
     *
     * @param views 想要依次排列的View
     */
    fun addLeftView(vararg views: View) {
        for (view in views) {
            addCustomView(view, Gravity.START)
        }
    }

    /**
     * 添加一个右边的View
     * ToolBar右边菜单可以使用Menu，若想展示自己的样式，
     * 可以使用[android.view.MenuItem.setActionView]
     * 或[android.view.MenuItem.setActionView]这两个方法将自定义View展示在Menu上
     * 也可以使用本方法往右边添加自定义View
     *
     * @param views 想要依次排列的View
     */
    fun addRightView(vararg views: View) {
        for (view in views) {
            addCustomView(view, Gravity.END)
        }
    }

    /**
     * 添加自定义View
     *
     * @param v       view
     * @param gravity gravity
     */
    private fun addCustomView(v: View, gravity: Int) {
        val vlp = v.layoutParams
        val lp: LayoutParams
        lp = if (vlp == null) {
            generateDefaultLayoutParams(gravity)
        } else if (!checkLayoutParams(vlp)) {
            generateLayoutParams(vlp, gravity)
        } else {
            vlp as LayoutParams
        }
        addView(v, lp)
    }

    /**
     * 创建[Toolbar.LayoutParams]
     * 用于指定添加到ToolBar的View的位置
     *
     * @param gravity CENTER,LEFT,RIGHT
     * @return LayoutParams
     */
    private fun generateDefaultLayoutParams(gravity: Int): LayoutParams {
        return LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, gravity)
    }

    /**
     * 创建[Toolbar.LayoutParams]
     * 用于指定添加到ToolBar的View的位置
     *
     * @param lp      view的LayoutParams
     * @param gravity CENTER,LEFT,RIGHT
     * @return LayoutParams
     */
    private fun generateLayoutParams(lp: ViewGroup.LayoutParams, gravity: Int): LayoutParams {
        val layoutParams = when (lp) {
            is LayoutParams -> {
                LayoutParams(lp)
            }
            is ActionBar.LayoutParams -> {
                LayoutParams(lp)
            }
            is MarginLayoutParams -> {
                LayoutParams(lp)
            }
            else -> {
                LayoutParams(lp)
            }
        }
        layoutParams.gravity = gravity
        return layoutParams
    }
}