package com.thepacific.presentation.common;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.PopupWindow;
import android.widget.Toast;
import javax.annotation.Nullable;

public class RouterUtil {

  private RouterUtil() {
    throw new UnsupportedOperationException();
  }

  public static void dismiss(@Nullable Object o) {
    if (o == null) {
      return;
    }
    if (o instanceof Dialog) {
      Dialog dialog = (Dialog) o;
      if (dialog.isShowing()) {
        dialog.dismiss();
      }
      return;
    }

    if (o instanceof Toast) {
      Toast toast = (Toast) o;
      toast.cancel();
      return;
    }

    if (o instanceof Snackbar) {
      Snackbar snackbar = (Snackbar) o;
      if (snackbar.isShownOrQueued()) {
        snackbar.dismiss();
      }
      return;
    }

    if (o instanceof PopupWindow) {
      PopupWindow popupWindow = (PopupWindow) o;
      if (popupWindow.isShowing()) {
        popupWindow.dismiss();
      }
      return;
    }

    if (o instanceof DialogFragment) {
      DialogFragment fragment = (DialogFragment) o;
      try {
        fragment.dismiss();
      } catch (Exception e) {
        fragment.dismissAllowingStateLoss();
        e.printStackTrace();
      }
      return;
    }

    throw new UnsupportedOperationException();
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
    Intent intent = new Intent();
    intent.setClass(from, to);
    if (extras != null) {
      intent.putExtras(extras);
    }
    from.startActivity(intent);
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

  public static void start2(Fragment from, Class<?> to, Bundle extras) {
    Intent intent = new Intent();
    intent.setClass(from.getActivity(), to);
    if (extras != null) {
      intent.putExtras(extras);
    }
    from.startActivity(intent);
  }

  public static void start2(Fragment from, Class<?> to) {
    start2(from, to, null);
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
}
