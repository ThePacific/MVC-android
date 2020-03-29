package com.square.common.ui.pop;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.PopupWindowCompat;

import com.square.common.ui.R;
import com.square.common.ui.view.ViewUtilsKt;

/**
 * 1. scrollTo和scrollBy只是移动自己的内容.也就是如果ViewGroup设置scrollTo或者scrollBy的话,
 * 只有它的子View会有位移效果.如果是TextView设置scrollTo或者scrollBy的话只会让它内部的文字发生位移.
 * 2. scrollBy还是调用的scrollTo,但scrollBy的起始坐标是相对于上次结束时的mScrollX和mScrollY.
 * scrollTo的起始坐标是相对于父布局的左上角,之后起始坐标是不会变的
 */
public final class PopupWindowsManager implements PopupWindow.OnDismissListener {
    public final int keyboardHeight;
    @NonNull
    public final View content;
    @NonNull
    public final LayoutInflater layoutInflater;

    /**
     * DecorView内部包含两部分：标题栏和内容栏
     * Activity通过setContentView所设置的布局文件就是被加到内容栏中的，而内容栏的id是content
     * 通过ViewGroup viewGroup = findViewById(android.R.id.content)得到content
     * 通过content.getChildAt(0)得到我们Activity.setContentView设置的View
     * DecorView和content从目前的源码来看，它们都是FrameLayout
     * 综上，我们可以通过滚动content.getChildAt(0)来解决EditText被遮挡问题
     */
    private final ViewGroup activityRootView;
    private PopupWindow window;
    private int moveHeight = 0;

    public PopupWindowsManager(@NonNull Activity activity, @LayoutRes int keyboardLayout) {
        this(activity, keyboardLayout, null);
    }

    public PopupWindowsManager(
            @NonNull Activity activity,
            @LayoutRes int keyboardLayout,
            @Nullable ViewGroup rootView
    ) {
        if (rootView == null) {
            this.activityRootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        } else {
            this.activityRootView = rootView;
        }
        this.layoutInflater = LayoutInflater.from(activity.getApplication());
        this.content = layoutInflater.inflate(keyboardLayout, null);
        this.keyboardHeight = ViewUtilsKt.toMeasure(content).y;
    }

    /**
     * 显示键盘
     *
     * @param anchor 当前要输入的锚点view
     */
    public void showKeyboard(@NonNull View anchor) {
        if (window == null) {
            window = new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, keyboardHeight);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setAnimationStyle(R.style.OKKeyboardWindow);
            window.setOutsideTouchable(false);
            window.setFocusable(false);
            window.setTouchable(true);
            window.setContentView(content);
            window.setOnDismissListener(this);
            window.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            PopupWindowCompat.setOverlapAnchor(window, true);
        }
        if (!isShowing()) {
            window.showAtLocation(activityRootView, Gravity.LEFT | Gravity.BOTTOM, 0, 0);
        }

        int[] outLocation = new int[2];
        anchor.getLocationOnScreen(outLocation);
        // 锚点底部y轴坐标
        int anchorBaseLineY = outLocation[1] + anchor.getHeight();
        activityRootView.getLocationOnScreen(outLocation);
        // 可见内容底部y轴坐标
        int screenBaseLineY = outLocation[1] + activityRootView.getHeight();
        moveHeight = screenBaseLineY - anchorBaseLineY - keyboardHeight;
        if (moveHeight < 0) {
            // 可自行额外加偏移量，例如Math.abs(moveHeight) + n
            moveHeight = Math.abs(moveHeight);
            scrollTo(0, moveHeight);
        } else {
            moveHeight = 0;
        }
    }

    /**
     * 关闭键盘
     */
    public void hideKeyboard() {
        if (window != null) {
            window.dismiss();
        }
    }

    @Override
    public void onDismiss() {
        activityRootView.scrollTo(0, 0);
    }

    /**
     * 滚动activityRootView布局里面的内容
     *
     * @param x x轴偏移量
     * @param y y轴偏移量
     */
    private void scrollTo(int x, int y) {
        activityRootView.scrollBy(x, y);
    }

    public boolean isShowing() {
        if (window == null) {
            return false;
        }
        return window.isShowing();
    }

    public int getMoveHeight() {
        return moveHeight;
    }
}
