package com.thepacific.common.view;

import android.text.TextWatcher;

public abstract class AfterTextChanged implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }
}
