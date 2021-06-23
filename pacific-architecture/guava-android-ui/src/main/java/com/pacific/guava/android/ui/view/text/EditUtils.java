package com.pacific.guava.android.ui.view.text;

import android.text.InputFilter;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditUtils {

    /**
     * 禁止输入特殊字符
     *
     * @param editText
     */
    public static void setEditTextInhibitInputSpeChat(EditText editText) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String speChat = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(source.toString());
                if (matcher.find()) return "";
                else return null;

            }
        };
        editText.setFilters(new InputFilter[]{filter});

    }

    /**
     * 禁止输入空格字符
     *
     * @param editText
     */
    public static void setEditTextInhibitInputSpace(EditText editText) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(" ")) return "";
                else return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter});

    }

    /**
     * 禁止使用复制粘贴
     *
     * @param editText
     */
    public static void disableCopyAndPaste(EditText editText) {
        try {
            if (editText == null) {
                return;
            }

            editText.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return true;
                }
            });
            editText.setLongClickable(false);
            editText.setTextIsSelectable(false);
            editText.setCustomSelectionActionModeCallback(new android.view.ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(android.view.ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onPrepareActionMode(android.view.ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(android.view.ActionMode mode, MenuItem item) {
                    return false;
                }

                @Override
                public void onDestroyActionMode(android.view.ActionMode mode) {
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
