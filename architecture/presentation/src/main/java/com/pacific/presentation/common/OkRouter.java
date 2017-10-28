package com.pacific.presentation.common;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Toast;
import com.pacific.presentation.core.OkReceiver;
import com.jakewharton.processphoenix.ProcessPhoenix;

public class OkRouter {

  private OkRouter() {
    throw new UnsupportedOperationException();
  }

  public static void dismiss(Object... targets) {
    if (targets == null || targets.length == 0) {
      return;
    }
    for (int i = 0; i < targets.length; i++) {
      Object target = targets[i];
      if (target == null) {
        continue;
      }
      if (target instanceof Dialog) {
        Dialog dialog = (Dialog) target;
        if (dialog.isShowing()) {
          dialog.dismiss();
        }
        continue;
      }
      if (target instanceof Toast) {
        Toast toast = (Toast) target;
        toast.cancel();
        return;
      }
      if (target instanceof Snackbar) {
        Snackbar snackbar = (Snackbar) target;
        if (snackbar.isShownOrQueued()) {
          snackbar.dismiss();
        }
        continue;
      }
      if (target instanceof PopupWindow) {
        PopupWindow popupWindow = (PopupWindow) target;
        if (popupWindow.isShowing()) {
          popupWindow.dismiss();
        }
        return;
      }
      if (target instanceof PopupMenu) {
        PopupMenu popupMenu = (PopupMenu) target;
        popupMenu.dismiss();
        continue;
      }
      if (target instanceof android.widget.PopupMenu) {
        android.widget.PopupMenu popupMenu = (android.widget.PopupMenu) target;
        popupMenu.dismiss();
        continue;
      }
      if (target instanceof DialogFragment) {
        DialogFragment fragment = (DialogFragment) target;
        try {
          fragment.dismiss();
        } catch (Exception e) {
          fragment.dismissAllowingStateLoss();
          e.printStackTrace();
        }
        continue;
      }

      if (target instanceof android.app.DialogFragment) {
        android.app.DialogFragment fragment = (android.app.DialogFragment) target;
        try {
          fragment.dismiss();
        } catch (Exception e) {
          fragment.dismissAllowingStateLoss();
          e.printStackTrace();
        }
        continue;
      }

      throw new UnsupportedOperationException();
    }
  }

  public static void showDialogFragment(FragmentManager fm, DialogFragment fragment) {
    final String tag = fragment.getClass().getSimpleName();
    FragmentTransaction ft = fm.beginTransaction();
    Fragment prev = fm.findFragmentByTag(tag);
    if (prev != null) {
      ft.remove(prev);
    }
    fragment.show(ft, tag);
  }

  public static void addFragment(FragmentActivity activity, Fragment fragment, boolean isAddBack,
      @IdRes int container) {
    if (fragment == null) {
      return;
    }
    FragmentManager fm = activity.getSupportFragmentManager();
    FragmentTransaction ft = fm.beginTransaction();
    if (fm.findFragmentByTag(fragment.getClass().getSimpleName()) != null) {
      ft.show(fm.findFragmentByTag(fragment.getClass().getSimpleName())).commit();
      return;
    }
    ft.add(container, fragment, fragment.getClass().getSimpleName());
    if (isAddBack) {
      ft.addToBackStack(fragment.getClass().getSimpleName());
    }
    ft.commit();
  }

  public static void replaceFragment(FragmentActivity activity, Fragment fragment,
      boolean isAddBack, @IdRes int container) {
    if (fragment == null) {
      return;
    }
    FragmentManager fm = activity.getSupportFragmentManager();
    FragmentTransaction ft = fm.beginTransaction();
    if (fm.findFragmentByTag(fragment.getClass().getSimpleName()) != null) {
      ft.show(fm.findFragmentByTag(fragment.getClass().getSimpleName())).commit();
      return;
    }
    ft.replace(container, fragment, fragment.getClass().getSimpleName());
    if (isAddBack) {
      ft.addToBackStack(fragment.getClass().getSimpleName());
    }
    ft.commit();
  }

  public static void showFragment(FragmentActivity activity, Fragment fragment) {
    if (fragment == null || !fragment.isHidden()) {
      return;
    }
    FragmentManager fm = activity.getSupportFragmentManager();
    FragmentTransaction ft = fm.beginTransaction();
    if (fragment.isAdded()) {
      ft.show(fragment);
      ft.commit();
    }
  }

  public static void hideFragment(FragmentActivity activity, Fragment fragment) {
    if (fragment == null || fragment.isHidden()) {
      return;
    }
    FragmentManager fm = activity.getSupportFragmentManager();
    FragmentTransaction ft = fm.beginTransaction();
    if (fragment.isAdded()) {
      ft.hide(fragment);
      ft.commit();
    }
  }

  public static void removeFragment(FragmentActivity activity, Fragment fragment) {
    if (fragment == null) {
      return;
    }
    FragmentManager fm = activity.getSupportFragmentManager();
    FragmentTransaction ft = fm.beginTransaction();
    if (fragment.isAdded()) {
      ft.remove(fragment);
      ft.commit();
    }
  }

  public static void jumpTo(Activity from, Class<?> to, Bundle extras) {
    start(from, to, extras);
    from.finish();
  }

  public static void jumpTo(Activity from, Class<?> to) {
    jumpTo(from, to, null);
  }

  public static void start(Activity from, Class<?> to, Bundle extras) {
    Intent intent = new Intent();
    intent.setClass(from, to);
    if (extras != null) {
      intent.putExtras(extras);
    }
    from.startActivity(intent);
  }

  public static void start(Activity from, Class<?> to) {
    start(from, to, null);
  }

  public static void startForResult(Activity from, Class<?> to, Bundle extras, int requestCode) {
    Intent intent = new Intent();
    intent.setClass(from, to);
    if (extras != null) {
      intent.putExtras(extras);
    }
    from.startActivityForResult(intent, requestCode);
  }

  public static void startForResult(Activity from, Class<?> to, int requestCode) {
    startForResult(from, to, null, requestCode);
  }

  public static void startForResult2(Fragment from, Class<?> to, Bundle extras, int requestCode) {
    Intent intent = new Intent();
    intent.setClass(from.getActivity(), to);
    if (extras != null) {
      intent.putExtras(extras);
    }
    from.startActivityForResult(intent, requestCode);
  }

  public static void startForResult2(Fragment from, Class<?> to, int requestCode) {
    startForResult2(from, to, null, requestCode);
  }

  public static void exit(Context context, boolean restart) {
    OkReceiver.sendFinishBroadcast(context);
    if (restart) {
      ProcessPhoenix.triggerRebirth(context);
    }
  }

  public static boolean isForeground(Context context) {
    ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    ComponentName cn = (am.getRunningTasks(1).get(0)).topActivity;
    return context.getPackageName().equals(cn.getPackageName());
  }
}
