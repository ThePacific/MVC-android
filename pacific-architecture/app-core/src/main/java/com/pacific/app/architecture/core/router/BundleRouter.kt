package com.pacific.app.architecture.core.router

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.pacific.guava.android.mvvm.AndroidX

const val MinePackage = "com.pacific.app.architecture.mine"

const val MainPackage = "com.pacific.app.architecture.main"

const val AuthPackage = "com.pacific.app.architecture.auth"

private fun createFragment(clazzName: String): Fragment {
    return FragmentFactory().instantiate(AndroidX.myApp.classLoader, clazzName)
}

private fun createIntent(context: Context, clazzName: String): Intent {
    return Intent().apply {
        setClassName(context, clazzName)
    }
}