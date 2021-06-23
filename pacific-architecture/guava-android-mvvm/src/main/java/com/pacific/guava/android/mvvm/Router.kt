package com.pacific.guava.android.mvvm

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

// 选择文档
val getContent: ActivityResultContracts.GetContent by lazy {
    ActivityResultContracts.GetContent()
}

// 打开多个文档
val getMultipleContents: ActivityResultContracts.GetMultipleContents by lazy {
    ActivityResultContracts.GetMultipleContents()
}

// 创建文件
val createDocument: ActivityResultContracts.CreateDocument by lazy {
    ActivityResultContracts.CreateDocument()
}

// 打开文件
val openDocument: ActivityResultContracts.OpenDocument by lazy {
    ActivityResultContracts.OpenDocument()
}

// 打开文件
val openDocumentTree: ActivityResultContracts.OpenDocumentTree by lazy {
    ActivityResultContracts.OpenDocumentTree()
}

// 选择多文件
val openMultipleDocuments: ActivityResultContracts.OpenMultipleDocuments by lazy {
    ActivityResultContracts.OpenMultipleDocuments()
}

// 权限申请
val requestMultiplePermissions: ActivityResultContracts.RequestMultiplePermissions by lazy {
    ActivityResultContracts.RequestMultiplePermissions()
}

// 权限申请
val requestPermission: ActivityResultContracts.RequestPermission by lazy {
    ActivityResultContracts.RequestPermission()
}

// 选择联系人
val pickContact: ActivityResultContracts.PickContact by lazy {
    ActivityResultContracts.PickContact()
}

// 启动app
val startActivityForResult: ActivityResultContracts.StartActivityForResult by lazy {
    ActivityResultContracts.StartActivityForResult()
}

// 启动app
val startIntentSenderForResult: ActivityResultContracts.StartIntentSenderForResult by lazy {
    ActivityResultContracts.StartIntentSenderForResult()
}

// 照相
val takePicture: ActivityResultContracts.TakePicture by lazy {
    ActivityResultContracts.TakePicture()
}

// 照相预览
val takePicturePreview: ActivityResultContracts.TakePicturePreview by lazy {
    ActivityResultContracts.TakePicturePreview()
}

// 拍视频
val captureVideo: ActivityResultContracts.CaptureVideo by lazy {
    ActivityResultContracts.CaptureVideo()
}

/**
 * 显示DialogFragment
 */
fun FragmentActivity.showDialogFragment(dialogFragment: DialogFragment) {
    showDialogFragment(this.supportFragmentManager, dialogFragment)
}

/**
 * 显示DialogFragment
 */
fun Fragment.showDialogFragment(dialogFragment: DialogFragment) {
    showDialogFragment(this.childFragmentManager, dialogFragment)
}

/**
 * 显示DialogFragment
 */
fun FragmentActivity.showDialogFragmentAllowingStateLoss(dialogFragment: DialogFragment) {
    showDialogFragmentAllowingStateLoss(
        this.supportFragmentManager,
        dialogFragment
    )
}

/**
 * 显示DialogFragment
 */
fun Fragment.showDialogFragmentAllowingStateLoss(dialogFragment: DialogFragment) {
    showDialogFragmentAllowingStateLoss(
        this.childFragmentManager,
        dialogFragment
    )
}

/**
 * 显示DialogFragment
 */
fun showDialogFragment(fm: FragmentManager, dialogFragment: DialogFragment) {
    val tag = dialogFragment.javaClass.simpleName
    val ft = fm.beginTransaction()
    val prev = fm.findFragmentByTag(tag)
    if (prev != null) {
        ft.remove(prev)
    }
    dialogFragment.show(ft, tag)
}

/**
 * 显示DialogFragment
 */
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

/**
 * 启动Activity，带参数
 */
@JvmOverloads
fun Activity.newStartActivity(to: Class<*>, extras: Bundle? = null) {
    val intent = Intent()
    intent.setClass(this, to)
    if (extras != null) {
        intent.putExtras(extras)
    }
    this.startActivity(intent)
}

/**
 * 启动Activity，带参数
 */
@JvmOverloads
fun Fragment.newStartActivity(to: Class<*>, extras: Bundle? = null) {
    val intent = Intent()
    intent.setClass(this.requireActivity(), to)
    if (extras != null) {
        intent.putExtras(extras)
    }
    this.startActivity(intent)
}

/**
 * 获取Intent数据
 */
fun Activity.getExtras(): Bundle = this.intent.extras!!

/**
 * 成功关闭Activity
 */
@JvmOverloads
fun Activity.finishWithResultOk(intent: Intent? = null) {
    if (intent == null) {
        this.setResult(Activity.RESULT_OK)
    } else {
        this.setResult(Activity.RESULT_OK, intent)
    }
    this.finish()
}

/**
 * 关闭对话框
 */
fun dismiss(vararg targets: Any?) {
    if (targets.isEmpty()) return
    for (i in targets.indices) {
        val target = targets[i] ?: continue
        if (target is Dialog) {// Dialog
            target.dismiss()
            continue
        }
        if (target is Toast) {// toast
            target.cancel()
            continue
        }
        if (target is Snackbar) {// SnackBar
            target.dismiss()
            continue
        }
        if (target is PopupWindow) {// PopupWindow
            target.dismiss()
            continue
        }
        if (target is PopupMenu) {// PopupMenu
            target.dismiss()
            continue
        }
        if (target is android.widget.PopupMenu) {// PopupMenu
            target.dismiss()
            continue
        }
        if (target is DialogFragment) {// DialogFragment
            target.dismissAllowingStateLoss()
            continue
        }
        throw AssertionError()
    }
}