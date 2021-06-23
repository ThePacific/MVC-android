package com.pacific.guava.android.context

import android.app.Activity
import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment
import com.pacific.guava.android.ime.ImeUtils

/**
 * 创建Fragment
 */
fun Fragment.instantiate(className: String): Fragment {
    return childFragmentManager.fragmentFactory.instantiate(
        activity?.classLoader ?: javaClass.classLoader!!,
        className
    )
}

/**
 * 隐藏软键盘
 */
fun Fragment.hideSoftInput() = this.view?.let { ImeUtils.hideIme(it) }

/**
 * 设置状态栏颜色
 */
fun Fragment.applyNavigationBarColor(@ColorRes colorRes: Int) {
    requireActivity().applyStatusBarColor(colorRes)
}

/**
 * 设置导航栏栏颜色
 */
fun Fragment.applyStatusBarColor(@ColorRes colorRes: Int) {
    requireActivity().applyStatusBarColor(colorRes)
}

/**
 * 获取父Activity
 */
@Suppress("UNCHECKED_CAST")
fun <T : Activity> Fragment.ofActivity() = requireActivity() as T

/**
 * 获取父Fragment
 */
@Suppress("UNCHECKED_CAST")
fun <T : Fragment> Fragment.ofParent() = requireParentFragment() as T