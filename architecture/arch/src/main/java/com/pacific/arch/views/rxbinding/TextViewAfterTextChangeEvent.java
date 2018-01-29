package com.pacific.arch.views.rxbinding;

import android.support.annotation.Nullable;
import android.text.Editable;
import android.widget.TextView;

import com.pacific.arch.gauva.Preconditions2;

public class TextViewAfterTextChangeEvent {

    public final TextView view;
    public final Editable editable;

    private TextViewAfterTextChangeEvent(TextView view, Editable editable) {
        this.view = view;
        this.editable = editable;
    }

    public static TextViewAfterTextChangeEvent create(@Nullable TextView view,
                                                      @Nullable Editable editable) {
        Preconditions2.checkNotNull(view);
        Preconditions2.checkNotNull(editable);
        return new TextViewAfterTextChangeEvent(view, editable);
    }
}
