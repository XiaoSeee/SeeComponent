package com.see.mvvm.navigation

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.customview.widget.Openable
import androidx.navigation.FloatingWindow
import androidx.navigation.NavController
import androidx.navigation.NavController.OnDestinationChangedListener
import androidx.navigation.NavDestination
import androidx.navigation.ui.AppBarConfiguration
import androidx.transition.TransitionManager
import com.see.mvvm.widget.TitleBar
import java.lang.ref.WeakReference
import java.util.regex.Pattern

/**
 * 抄的navigation-ui库里的下面两个类，修改了当title在中间的时候移除动画
 * 有空注意同步和库的更新
 *
 * [androidx.navigation.ui.ToolbarOnDestinationChangedListener]
 * [androidx.navigation.ui.AbstractAppBarOnDestinationChangedListener]
 *
 * @author by wuxiang@tinglibao.com.cn on 2020/12/1.
 */
class TitleBarOnDestinationChangedListener(titleBar: TitleBar, configuration: AppBarConfiguration)
    : OnDestinationChangedListener {
    private val mContext = titleBar.context
    private val mTitleBarWeakReference: WeakReference<TitleBar> = WeakReference(titleBar)
    private val mTopLevelDestinations = configuration.topLevelDestinations
    private val mOpenableLayoutWeakReference: WeakReference<Openable>? =
            if (configuration.openableLayout != null) {
                WeakReference(configuration.openableLayout)
            } else {
                null
            }

    private var mArrowDrawable: DrawerArrowDrawable? = null
    private var mAnimator: ValueAnimator? = null

    override fun onDestinationChanged(controller: NavController,
                                      destination: NavDestination, arguments: Bundle?) {
        if (mTitleBarWeakReference.get() == null) {
            controller.removeOnDestinationChangedListener(this)
        }
        if (destination is FloatingWindow) {
            return
        }
        val openableLayout = mOpenableLayoutWeakReference?.get()
        if (mOpenableLayoutWeakReference != null && openableLayout == null) {
            controller.removeOnDestinationChangedListener(this)
            return
        }
        val label = destination.label
        if (label != null) {
            // Fill in the data pattern with the args to build a valid URI
            val title = StringBuffer()
            val fillInPattern = Pattern.compile("\\{(.+?)\\}")
            val matcher = fillInPattern.matcher(label)
            while (matcher.find()) {
                val argName = matcher.group(1)
                if (arguments != null && arguments.containsKey(argName)) {
                    matcher.appendReplacement(title, "")
                    title.append(arguments[argName].toString())
                } else {
                    throw IllegalArgumentException("Could not find " + argName + " in "
                            + arguments + " to fill label " + label)
                }
            }
            matcher.appendTail(title)
            setTitle(title)
        }
        val isTopLevelDestination = matchDestinations(destination, mTopLevelDestinations)
        if (openableLayout == null && isTopLevelDestination) {
            setNavigationIcon(null, null)
        } else {
            setActionBarUpIndicator(openableLayout != null && isTopLevelDestination)
        }
    }

    private fun setTitle(title: CharSequence) {
        mTitleBarWeakReference.get()?.mActivity?.title = title
    }

    @SuppressLint("ObjectAnimatorBinding")
    private fun setActionBarUpIndicator(showAsDrawerIndicator: Boolean) {
        var animate = true
        if (mArrowDrawable == null) {
            mArrowDrawable = DrawerArrowDrawable(mContext)
            // We're setting the initial state, so skip the animation
            animate = false
        }
        setNavigationIcon(mArrowDrawable, if (showAsDrawerIndicator) "open_drawer" else "navigate_up")
        val endValue = if (showAsDrawerIndicator) 0f else 1f
        if (animate) {
            val startValue = mArrowDrawable!!.progress
            if (mAnimator != null) {
                mAnimator!!.cancel()
            }
            mAnimator = ObjectAnimator.ofFloat(mArrowDrawable, "progress", startValue, endValue)
            (mAnimator as ObjectAnimator).start()
        } else {
            mArrowDrawable!!.progress = endValue
        }
    }

    private fun setNavigationIcon(icon: Drawable?, description: CharSequence?) {
        val toolbar: TitleBar? = mTitleBarWeakReference.get()
        if (toolbar != null) {
            //当图标从无到有，且Title不在中间时执行动画
            val useTransition = icon == null &&
                    toolbar.navigationIcon != null &&
                    toolbar.mTitleGravity != Gravity.CENTER
            toolbar.navigationIcon = icon
            toolbar.navigationContentDescription = description
            if (useTransition) {
                TransitionManager.beginDelayedTransition(toolbar)
            }
        }
    }

    private fun matchDestinations(destination: NavDestination, topLevelDestinations: Set<Int>): Boolean {
        var currentDestination: NavDestination? = destination
        do {
            if (topLevelDestinations.contains(currentDestination!!.id)) {
                return true
            }
            currentDestination = currentDestination.parent
        } while (currentDestination != null)
        return false
    }
}