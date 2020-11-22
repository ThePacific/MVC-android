package com.pacific.guava.android.ime;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;

import java.lang.reflect.Method;

public class ImeUtils {

    private ImeUtils() {
    }

    public static void showIme(@NonNull View view, @NonNull Dialog dialog, int mode) {
        dialog.getWindow().setSoftInputMode(mode);
        view.requestFocus();
    }

    public static void showIme(@NonNull View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE
        );
        imm.showSoftInput(view, 0);
        view.requestFocus();
    }

    public static void hideIme(@NonNull View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE
        );
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void enableSystemSoftKeyboard(@NonNull EditText editText, boolean enable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            editText.setShowSoftInputOnFocus(false);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            try {
                Class<EditText> cls = EditText.class;
                final Method m = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                m.setAccessible(true);
                m.invoke(editText, enable);
            } catch (SecurityException e) {
                editText.setInputType(InputType.TYPE_NULL);
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                editText.setInputType(InputType.TYPE_NULL);
                e.printStackTrace();
            } catch (Exception e) {
                editText.setInputType(InputType.TYPE_NULL);
                e.printStackTrace();
            }
        } else {
            editText.setInputType(InputType.TYPE_NULL);
        }
    }
}
