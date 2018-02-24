package com.pacific.arch.presentation

import android.app.Activity
import android.app.Application
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.design.widget.Snackbar
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.widget.PopupMenu
import android.widget.PopupWindow
import android.widget.Toast
import com.jakewharton.processphoenix.ProcessPhoenix
import org.joor.Reflect

@JvmOverloads
fun exit(application: Application, restart: Boolean = false) {
    sendBroadcast(application, ACTION_FINISH)
    if (restart) {
        ProcessPhoenix.triggerRebirth(application)
    } else {
        Runtime.getRuntime().exit(0)
    }
}

fun dismiss(vararg targets: Any?) {
    if (targets.isEmpty()) {
        return
    }
    for (i in targets.indices) {
        val target = targets[i] ?: continue
        if (target is Dialog) {
            if (target.isShowing) {
                target.dismiss()
            }
            continue
        }
        if (target is Toast) {
            target.cancel()
            return
        }
        if (target is Snackbar) {
            if (target.isShownOrQueued) {
                target.dismiss()
            }
            continue
        }
        if (target is PopupWindow) {
            if (target.isShowing) {
                target.dismiss()
            }
            return
        }
        if (target is PopupMenu) {
            target.dismiss()
            continue
        }
        if (target is android.widget.PopupMenu) {
            target.dismiss()
            continue
        }
        if (target is DialogFragment) {
            target.dismissAllowingStateLoss()
            continue
        }
        if (target is android.app.DialogFragment) {
            target.dismissAllowingStateLoss()
            continue
        }
        throw UnsupportedOperationException()
    }
}

fun showDialogFragment(fm: FragmentManager, fragment: DialogFragment) {
    val tag = fragment.javaClass.simpleName
    val ft = fm.beginTransaction()
    val prev = fm.findFragmentByTag(tag)
    if (prev != null) {
        ft.remove(prev)
    }
    fragment.show(ft, tag)
}

fun showDialogFragmentAllowingStateLoss(fm: FragmentManager, fragment: DialogFragment) {
    val tag = fragment.javaClass.simpleName
    val ft = fm.beginTransaction()
    val prev = fm.findFragmentByTag(tag)
    if (prev != null) {
        ft.remove(prev)
    }
    Reflect.on(fragment).set("mDismissed", false)
    Reflect.on(fragment).set("mShownByMe", true)
    ft.add(fragment, tag)
    ft.commitAllowingStateLoss()
}

fun addFragment(activity: FragmentActivity,
                fragment: Fragment,
                isAddBack: Boolean,
                @IdRes container: Int) {
    val fm = activity.supportFragmentManager
    val ft = fm.beginTransaction()
    if (fm.findFragmentByTag(fragment.javaClass.simpleName) != null) {
        ft.show(fm.findFragmentByTag(fragment.javaClass.simpleName)).commit()
        return
    }
    ft.add(container, fragment, fragment.javaClass.simpleName)
    if (isAddBack) {
        ft.addToBackStack(fragment.javaClass.simpleName)
    }
    ft.commit()
}

fun replaceFragment(activity: FragmentActivity,
                    fragment: Fragment,
                    isAddBack: Boolean,
                    @IdRes container: Int) {
    val fm = activity.supportFragmentManager
    val ft = fm.beginTransaction()
    if (fm.findFragmentByTag(fragment.javaClass.simpleName) != null) {
        ft.show(fm.findFragmentByTag(fragment.javaClass.simpleName)).commit()
        return
    }
    ft.replace(container, fragment, fragment.javaClass.simpleName)
    if (isAddBack) {
        ft.addToBackStack(fragment.javaClass.simpleName)
    }
    ft.commit()
}

fun showFragment(activity: FragmentActivity, fragment: Fragment) {
    if (!fragment.isHidden) {
        return
    }
    val fm = activity.supportFragmentManager
    val ft = fm.beginTransaction()
    if (fragment.isAdded) {
        ft.show(fragment)
        ft.commit()
    }
}

fun hideFragment(activity: FragmentActivity, fragment: Fragment) {
    if (fragment.isHidden) {
        return
    }
    val fm = activity.supportFragmentManager
    val ft = fm.beginTransaction()
    if (fragment.isAdded) {
        ft.hide(fragment)
        ft.commit()
    }
}

fun removeFragment(activity: FragmentActivity, fragment: Fragment) {
    val fm = activity.supportFragmentManager
    val ft = fm.beginTransaction()
    if (fragment.isAdded) {
        ft.remove(fragment)
        ft.commit()
    }
}

@JvmOverloads
fun jumpTo(from: Activity, to: Class<*>, extras: Bundle? = null) {
    start(from, to, extras)
    from.finish()
}

@JvmOverloads
fun start(from: Activity, to: Class<*>, extras: Bundle? = null) {
    val intent = Intent()
    intent.setClass(from, to)
    if (extras != null) {
        intent.putExtras(extras)
    }
    from.startActivity(intent)
}

@JvmOverloads
fun startForResult(from: Activity, to: Class<*>, requestCode: Int, extras: Bundle? = null) {
    val intent = Intent()
    intent.setClass(from, to)
    if (extras != null) {
        intent.putExtras(extras)
    }
    from.startActivityForResult(intent, requestCode)
}

@JvmOverloads
fun startForResult2(from: Fragment, to: Class<*>, requestCode: Int, extras: Bundle? = null) {
    val intent = Intent()
    intent.setClass(from.activity!!, to)
    if (extras != null) {
        intent.putExtras(extras)
    }
    from.startActivityForResult(intent, requestCode)
}

fun sendBroadcast(context: Context, action: String) {
    val intent = Intent()
    intent.action = action
    LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
}

fun Activity.okFinish() {
    this.setResult(Activity.RESULT_OK)
    this.finish()
}

const val ACTION_FINISH = "com.pacific.arch.action.finish"