package com.pacific.app.architecture.core.ext.rxjava3;

import android.text.Editable;
import android.widget.TextView;

import androidx.annotation.Nullable;

import dagger.internal.Preconditions;

public class TextViewAfterTextChangeEvent {

    public final TextView view;
    public final Editable editable;

    private TextViewAfterTextChangeEvent(TextView view, Editable editable) {
        this.view = view;
        this.editable = editable;
    }

    public static TextViewAfterTextChangeEvent create(
            @Nullable TextView view,
            @Nullable Editable editable
    ) {
        Preconditions.checkNotNull(view);
        Preconditions.checkNotNull(editable);
        return new TextViewAfterTextChangeEvent(view, editable);
    }
}
