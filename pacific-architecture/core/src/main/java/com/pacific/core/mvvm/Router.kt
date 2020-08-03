package com.pacific.core.mvvm

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.PopupWindow
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.google.android.material.snackbar.Snackbar
import org.joor.Reflect

@get:JvmName("startActivityForResult")
val startActivityForResult by lazy { ActivityResultContracts.StartActivityForResult() }

@get:JvmName("startIntentSenderForResult")
val startIntentSenderForResult by lazy { ActivityResultContracts.StartIntentSenderForResult() }

@get:JvmName("requestMultiplePermissions")
val requestMultiplePermissions by lazy { ActivityResultContracts.RequestMultiplePermissions() }

@get:JvmName("requestPermission")
val requestPermission by lazy { ActivityResultContracts.RequestPermission() }

@get:JvmName("takePicturePreview")
val takePicturePreview by lazy { ActivityResultContracts.TakePicturePreview() }

@get:JvmName("takePicture")
val takePicture by lazy { ActivityResultContracts.TakePicture() }

@get:JvmName("takeVideo")
val takeVideo by lazy { ActivityResultContracts.TakeVideo() }

@get:JvmName("pickContact")
val pickContact by lazy { ActivityResultContracts.PickContact() }

@get:JvmName("getContent")
val getContent by lazy { ActivityResultContracts.GetContent() }

@get:JvmName("getMultipleContents")
val getMultipleContents by lazy { ActivityResultContracts.GetMultipleContents() }

@get:JvmName("openDocument")
val openDocument by lazy { ActivityResultContracts.OpenDocument() }

@get:JvmName("openMultipleDocuments")
val openMultipleDocuments by lazy { ActivityResultContracts.OpenMultipleDocuments() }

@get:JvmName("openDocumentTree")
val openDocumentTree by lazy { ActivityResultContracts.OpenDocumentTree() }

@get:JvmName("createDocument")
val createDocument by lazy { ActivityResultContracts.CreateDocument() }

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

fun Activity.newStartActivity(to: Class<*>, extras: Bundle? = null) {
    val intent = Intent()
    intent.setClass(this, to)
    if (extras != null) {
        intent.putExtras(extras)
    }
    this.startActivity(intent)
}

fun Fragment.newStartActivity(to: Class<*>, extras: Bundle? = null) {
    val intent = Intent()
    intent.setClass(this.requireActivity(), to)
    if (extras != null) {
        intent.putExtras(extras)
    }
    this.startActivity(intent)
}

fun Activity.ofExtras(): Bundle = this.intent.extras!!

fun Activity.finishWithResultOk(intent: Intent? = null) {
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
        throw RuntimeException()
    }
}