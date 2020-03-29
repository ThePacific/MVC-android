package com.square.core.mvvm

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.google.android.material.snackbar.Snackbar
import org.joor.Reflect

fun FragmentActivity.showDialogFragment(dialogFragment: DialogFragment) {
    showDialogFragment(this.supportFragmentManager, dialogFragment)
}

fun Fragment.showDialogFragment(dialogFragment: DialogFragment) {
    showDialogFragment(this.childFragmentManager, dialogFragment)
}

fun FragmentActivity.showDialogFragmentAllowingStateLoss(dialogFragment: DialogFragment) {
    showDialogFragmentAllowingStateLoss(
        this.supportFragmentManager,
        dialogFragment
    )
}

fun Fragment.showDialogFragmentAllowingStateLoss(dialogFragment: DialogFragment) {
    showDialogFragmentAllowingStateLoss(
        this.childFragmentManager,
        dialogFragment
    )
}

fun showDialogFragment(fm: FragmentManager, dialogFragment: DialogFragment) {
    val tag = dialogFragment.javaClass.simpleName
    val ft = fm.beginTransaction()
    val prev = fm.findFragmentByTag(tag)
    if (prev != null) {
        ft.remove(prev)
    }
    dialogFragment.show(ft, tag)
}

fun showDialogFragmentAllowingStateLoss(fm: FragmentManager, dialogFragment: DialogFragment) {
    val tag = dialogFragment.javaClass.simpleName
    val ft = fm.beginTransaction()
    val prev = fm.findFragmentByTag(tag)
    if (prev != null) {
        ft.remove(prev)
    }
    Reflect.on(dialogFragment).set("mDismissed", false)
    Reflect.on(dialogFragment).set("mShownByMe", true)
    ft.add(dialogFragment, tag)
    Reflect.on(dialogFragment).set("mViewDestroyed", false)
    Reflect.on(dialogFragment).set("mBackStackId", ft.commitAllowingStateLoss())
}

@JvmOverloads
fun Activity.newStartActivity(to: Class<*>, extras: Bundle? = null) {
    val intent = Intent()
    intent.setClass(this, to)
    if (extras != null) {
        intent.putExtras(extras)
    }
    this.startActivity(intent)
}

@JvmOverloads
fun Activity.newStartActivityForResult(to: Class<*>, requestCode: Int, extras: Bundle? = null) {
    val intent = Intent()
    intent.setClass(this, to)
    if (extras != null) {
        intent.putExtras(extras)
    }
    this.startActivityForResult(intent, requestCode)
}

@JvmOverloads
fun Fragment.newStartActivity(to: Class<*>, extras: Bundle? = null) {
    val intent = Intent()
    intent.setClass(this.activity!!, to)
    if (extras != null) {
        intent.putExtras(extras)
    }
    this.startActivity(intent)
}

@JvmOverloads
fun Fragment.newStartActivityForResult(to: Class<*>, requestCode: Int, extras: Bundle? = null) {
    val intent = Intent()
    intent.setClass(this.activity!!, to)
    if (extras != null) {
        intent.putExtras(extras)
    }
    this.startActivityForResult(intent, requestCode)
}

fun Activity.getExtras(): Bundle = this.intent.extras!!

@JvmOverloads
fun Activity.okFinish(intent: Intent? = null) {
    if (intent == null) {
        this.setResult(Activity.RESULT_OK)
    } else {
        this.setResult(Activity.RESULT_OK, intent)
    }
    this.finish()
}

fun dismiss(vararg targets: Any?) {
    if (targets.isEmpty()) return
    for (i in targets.indices) {
        val target = targets[i] ?: continue
        if (target is Dialog) {
            target.dismiss()
            continue
        }
        if (target is Toast) {
            target.cancel()
            continue
        }
        if (target is Snackbar) {
            target.dismiss()
            continue
        }
        if (target is PopupWindow) {
            target.dismiss()
            continue
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
        throw AssertionError()
    }
}